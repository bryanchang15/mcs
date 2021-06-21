package com.luanguan.mcs.mission.application;

import com.luanguan.mcs.buffer_location.application.FindFullRollLoadedBufferLocation;
import com.luanguan.mcs.empty_roll_location.application.FindUnloadedEmptyRollLocation;
import com.luanguan.mcs.external.robot.application.SchedulingRobot;
import com.luanguan.mcs.framework.domain.DomainEventRepository;
import com.luanguan.mcs.mission.domain.*;
import com.luanguan.mcs.mission.domain.MissionEvent.*;
import io.vavr.NotImplementedError;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static com.luanguan.mcs.mission.domain.MissionState.*;
import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;
import static io.vavr.Predicates.instanceOf;
import static io.vavr.Predicates.isIn;

@AllArgsConstructor
@Slf4j
public class MissionEventHandler {

    private final FindFullRollLoadedBufferLocation findFullRollLoadedBufferLocation;
    private final FindUnloadedEmptyRollLocation findUnloadedEmptyRollLocation;
    private final MissionRepository missionRepository;
    private final DomainEventRepository domainEventRepository;
    private final SchedulingRobot schedulingRobot;

    void handle(MissionCreated missionCreated) {
        missionRepository.findBy(missionCreated.missionId())
                .map(mission -> Match(mission).of(
                        Case($(instanceOf(WindingRollerUnloadingMission.class)), this::scheduleMission),
                        Case($(instanceOf(WindingRollerLoadingMission.class)), this::scheduleMission),
                        Case($(), m -> {
                            throw new NotImplementedError(m.toString());
                        })
                ))
                .map(this::saveMission);
    }


    void handle(MissionCompleted missionCompleted) {
        missionRepository.findAllPendedMissionBy(MissionPendingReason.PreMissionNotCompleted)
                .filter(mission -> Match(mission).of(
                        Case($(instanceOf(WindingRollerLoadingMission.class)), windingRollerLoadingMission ->
                                windingRollerLoadingMission.getPreMissionId().get().equals(
                                        missionCompleted.missionId()
                                )
                        ),
                        Case($(), () -> false)
                ))
                .map(mission -> Match(mission).of(
                        Case($(instanceOf(WindingRollerLoadingMission.class)), this::scheduleMission),
                        Case($(), m -> {
                            throw new NotImplementedError(m.toString());
                        })
                ))
                .map(this::saveMission);
    }

    void handle(MissionFailed missionFailed) {
        missionRepository.findAllPendedMissionBy(MissionPendingReason.PreMissionNotCompleted).toStream()
                .filter(mission -> Match(mission).of(
                        Case($(instanceOf(WindingRollerLoadingMission.class)), windingRollerLoadingMission ->
                                windingRollerLoadingMission.getPreMissionId().get().equals(
                                        missionFailed.missionId()
                                )
                        ),
                        Case($(), () -> false)
                ))
                .map(mission -> Match(mission).of(
                        Case($(instanceOf(WindingRollerLoadingMission.class)), Mission::fail),
                        Case($(), m -> {
                            throw new NotImplementedError(m.toString());
                        })
                ))
                .map(this::saveMission);
    }

    private WindingRollerUnloadingMission scheduleMission(WindingRollerUnloadingMission mission) {
        return findUnloadedEmptyRollLocation.findBy(
                mission.getSourceWindingRoller().getBatteryModel(),
                mission.getSourceWindingRoller().getElectrodeType()
        ).map(unloadedEmptyRollLocation ->
                schedulingRobot.schedule(ScheduleRobotCommandFactory.create(
                        mission.assignOf(unloadedEmptyRollLocation.getEmptyRollLocationId()),
                        unloadedEmptyRollLocation
                )).map(result ->
                        mission.executeBy(result.getRobotTaskId())
                ).recover(t -> {
                    log.warn("Schedule robot failed", t);
                    return mission.pendBy(MissionPendingReason.NoAvailableRobot);
                }).get()
        ).getOrElse(mission.pendBy(MissionPendingReason.NoAvailableEmptyRollLocation));
    }

    private WindingRollerLoadingMission scheduleMission(WindingRollerLoadingMission mission) {
        return Match(
                mission.getPreMissionId().map(preMissionId -> // PreMission existed
                        missionRepository.findBy(preMissionId)
                                .map(preMission ->
                                        Match(preMission.getMissionState()).of(
                                                Case($(Completed), () -> mission), //PreMission completed
                                                Case($(), () -> mission.pendBy(
                                                        MissionPendingReason.PreMissionNotCompleted
                                                ))
                                        ))
                                .getOrElseThrow(RuntimeException::new)
                ).getOrElse(mission).getMissionState()
        ).of(Case($(isIn(Created, Pending)), () ->
                        findFullRollLoadedBufferLocation.findBy(
                                mission.getTargetWindingRoller().getBatteryModel(),
                                mission.getTargetWindingRoller().getElectrodeType()
                        ).map(fullRollLoadedBufferLocation ->
                                schedulingRobot.schedule(ScheduleRobotCommandFactory.create(
                                        mission.assignOf(fullRollLoadedBufferLocation.getBufferLocationId()),
                                        fullRollLoadedBufferLocation
                                )).map(
                                        result -> mission.executeBy(result.getRobotTaskId())
                                ).recover(t -> {
                                    log.warn("Schedule robot failed", t);
                                    return mission.pendBy(MissionPendingReason.NoAvailableRobot);
                                }).get()
                        ).getOrElse(mission.pendBy(MissionPendingReason.NoAvailableBufferLocation))
                )
        );
    }

    private Mission saveMission(Mission mission) {
        Match(missionRepository.save(mission).getMissionState()).of(
                Case($(Executing), () -> Match(mission).of(
                        Case($(instanceOf(WindingRollerUnloadingMission.class)), windingRollerUnloadingMission ->
                                domainEventRepository.publish(WindingRollerUnloadingMissionScheduled.now(
                                        windingRollerUnloadingMission.getMissionId(),
                                        windingRollerUnloadingMission.getTargetEmptyRollLocationId().get(),
                                        windingRollerUnloadingMission.getSourceWindingRoller().getBatteryModel(),
                                        windingRollerUnloadingMission.getSourceWindingRoller().getElectrodeType()
                                ))
                        ),
                        Case($(instanceOf(WindingRollerLoadingMission.class)), windingRollerLoadingMission ->
                                domainEventRepository.publish(WindingRollerLoadingMissionScheduled.now(
                                        windingRollerLoadingMission.getMissionId(),
                                        windingRollerLoadingMission.getSourceBufferLocationId().get()
                                ))
                        ),
                        Case($(), m -> {
                            throw new NotImplementedError(m.toString());
                        })
                )),
                Case($(Failed), () ->
                        domainEventRepository.publish(MissionFailed.now(mission.getMissionId()))
                ),
                Case($(), () -> {
                    throw new RuntimeException();
                })
        );

        return mission;
    }

}
