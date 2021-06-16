package com.luanguan.mcs.mission.application;

import com.luanguan.mcs.framework.domain.*;
import com.luanguan.mcs.mission.domain.*;
import com.luanguan.mcs.mission.domain.MissionEvent.MissionCreated;
import com.luanguan.mcs.shared_kernel.*;
import com.luanguan.mcs.winding_machine.application.FindWindingRoller;
import com.luanguan.mcs.winding_machine.domain.ElectrodeType;
import io.vavr.control.Try;
import lombok.AllArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
public class CreateWindingRollerLoadingMission {

    private final FindWindingRoller findWindingRoller;
    private final MissionRepository missionRepository;
    private final DomainEventRepository domainEventRepository;

    public Try<Mission> createBy(
            Position windingMachinePosition,
            BatteryModel batteryModel,
            WindingRollerName rollerName
    ) {
        return findWindingRoller.findBy(windingMachinePosition, batteryModel, rollerName).map(windingRoller -> {
            WindingRollerLoadingMission mission = new WindingRollerLoadingMission(
                    new MissionId(UUID.randomUUID()),
                    Version.zero(),
                    MissionState.Created,
                    null,
                    null,
                    windingRoller);
            missionRepository.save(mission);
            domainEventRepository.publish(MissionCreated.now(mission.getMissionId()));

            return mission;
        });
    }

}
