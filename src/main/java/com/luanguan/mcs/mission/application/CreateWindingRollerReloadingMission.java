package com.luanguan.mcs.mission.application;

import com.luanguan.mcs.framework.domain.DomainEventRepository;
import com.luanguan.mcs.framework.domain.Version;
import com.luanguan.mcs.mission.domain.*;
import com.luanguan.mcs.mission.domain.MissionEvent.MissionCreated;
import com.luanguan.mcs.shared_kernel.*;
import com.luanguan.mcs.winding_machine.application.FindWindingRoller;
import io.vavr.control.Try;
import lombok.AllArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
public class CreateWindingRollerReloadingMission {

    private final FindWindingRoller findWindingRoller;
    private final MissionRepository missionRepository;
    private final DomainEventRepository domainEventRepository;

    public Try<Mission> createBy(
            Position windingMachinePosition,
            BatteryModel batteryModel,
            WindingRollerName rollerName
    ) {
        return findWindingRoller.findBy(windingMachinePosition, batteryModel, rollerName).map(windingRoller -> {
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

            return mission;
        });

    }

}
