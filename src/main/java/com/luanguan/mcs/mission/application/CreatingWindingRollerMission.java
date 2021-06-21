package com.luanguan.mcs.mission.application;

import com.luanguan.mcs.framework.domain.DomainEventRepository;
import com.luanguan.mcs.mission.domain.*;
import com.luanguan.mcs.mission.domain.MissionEvent.MissionCreated;
import com.luanguan.mcs.winding_machine.application.FindWindingRoller;
import io.vavr.control.Try;
import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

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
            Mission mission = missionRepository.save(WindingRollerMissionFactory.unloading(
                    windingRoller
            ));
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
            Mission mission = missionRepository.save(WindingRollerMissionFactory.loading(
                    null,
                    windingRoller
            ));
            domainEventRepository.publish(MissionCreated.now(mission.getMissionId()));

            return new CreateMissionResult(mission.getMissionId().getId());
        });
    }

    @Transactional
    public Try<CreateMissionResult> createReloading(CreateWindingRollerMissionCommand command) {
        return findWindingRoller.findBy(
                command.getWindingMachinePosition(),
                command.getBatteryModel(),
                command.getRollerName()
        ).map(windingRoller -> {
            Mission preMission = missionRepository.save(WindingRollerMissionFactory.unloading(
                    windingRoller
            ));
            Mission mission = missionRepository.save(WindingRollerMissionFactory.loading(
                    preMission.getMissionId(),
                    windingRoller
            ));
            domainEventRepository.publish(MissionCreated.now(preMission.getMissionId()));
            domainEventRepository.publish(MissionCreated.now(mission.getMissionId()));

            return new CreateMissionResult(mission.getMissionId().getId());
        });
    }

}
