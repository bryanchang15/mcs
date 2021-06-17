package com.luanguan.mcs.mission.application;

import com.luanguan.mcs.framework.domain.DomainEventRepository;
import com.luanguan.mcs.framework.domain.Version;
import com.luanguan.mcs.mission.domain.MissionEvent.MissionCreated;
import com.luanguan.mcs.mission.domain.*;
import com.luanguan.mcs.winding_machine.application.FindWindingRoller;
import io.vavr.control.Try;
import lombok.AllArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
public class CreatingWindingRollerMission {

    private final FindWindingRoller findWindingRoller;
    private final MissionRepository missionRepository;
    private final DomainEventRepository domainEventRepository;

    public Try<CreateMissionResult> createUnloading(CreateWindingRollerMissionCommand command) {
        return findWindingRoller.findBy(
                command.getWindingMachinePosition(),
                command.getBatteryModel(),
                command.getRollerName()
        ).map(windingRoller -> {
            WindingRollerLoadingMission mission = new WindingRollerLoadingMission(
                    new MissionId(UUID.randomUUID()),
                    Version.zero(),
                    MissionState.Created,
                    null,
                    null,
                    windingRoller);
            missionRepository.save(mission);
            domainEventRepository.publish(MissionCreated.now(mission.getMissionId()));

            return new CreateMissionResult(mission.getMissionId().getId());
        });
    }

    public Try<CreateMissionResult> createLoading(CreateWindingRollerMissionCommand command) {
        return findWindingRoller.findBy(
                command.getWindingMachinePosition(),
                command.getBatteryModel(),
                command.getRollerName()
        ).map(windingRoller -> {
            WindingRollerUnloadingMission mission = new WindingRollerUnloadingMission(
                    new MissionId(UUID.randomUUID()),
                    Version.zero(),
                    MissionState.Created,
                    null,
                    windingRoller);
            missionRepository.save(mission);
            domainEventRepository.publish(MissionCreated.now(mission.getMissionId()));

            return new CreateMissionResult(mission.getMissionId().getId());
        });
    }

    public Try<CreateMissionResult> createReloading(CreateWindingRollerMissionCommand command) {
        return findWindingRoller.findBy(
                command.getWindingMachinePosition(),
                command.getBatteryModel(),
                command.getRollerName()
        ).map(windingRoller -> {
            WindingRollerUnloadingMission preMission = new WindingRollerUnloadingMission(
                    new MissionId(UUID.randomUUID()),
                    Version.zero(),
                    MissionState.Created,
                    null,
                    windingRoller);
            missionRepository.save(preMission);
            domainEventRepository.publish(MissionCreated.now(preMission.getMissionId()));

            WindingRollerLoadingMission mission = new WindingRollerLoadingMission(
                    new MissionId(UUID.randomUUID()),
                    Version.zero(),
                    MissionState.Created,
                    null,
                    preMission.getPreMissionId(),
                    windingRoller);
            missionRepository.save(mission);
            domainEventRepository.publish(MissionCreated.now(mission.getMissionId()));

            return new CreateMissionResult(mission.getMissionId().getId());
        });
    }

}
