package com.luanguan.mcs.mission.domain;

import com.luanguan.mcs.empty_roll_location.domain.EmptyRollLocationInformation;
import com.luanguan.mcs.framework.domain.Version;
import com.luanguan.mcs.mission.application.EmptyRollUnloadingRobotTaskRequest;
import com.luanguan.mcs.winding_machine.domain.WindingRoller;
import lombok.Getter;
import lombok.NonNull;

@Getter
public class EmptyRollUnloadingMission extends Mission {

    @NonNull private final WindingRoller sourceWindingRoller;

    private EmptyRollLocationInformation targetEmptyRollLocationInformation = null;

    private RobotTaskId scheduledRobotTask = null;

    public EmptyRollUnloadingMission(
            @NonNull MissionInformation missionInformation,
            @NonNull Version version,
            @NonNull MissionState missionState,
            @NonNull MissionPendingReason missionPendingReason,
            @NonNull WindingRoller sourceWindingRoller
    ) {
        super(missionInformation, version, missionState, missionPendingReason);
        this.sourceWindingRoller = sourceWindingRoller;
    }

    public void assignOf(EmptyRollLocationInformation emptyRollLocationInformation) {
        super.ready();
        this.targetEmptyRollLocationInformation = emptyRollLocationInformation;
    }

    public void executeBy(RobotTaskId scheduledRobotTask) {
        super.execute();
        this.scheduledRobotTask = scheduledRobotTask;
    }

    public EmptyRollUnloadingRobotTaskRequest getEmptyRollUnloadingRobotTaskRequest() {
        return new EmptyRollUnloadingRobotTaskRequest(
                sourceWindingRoller.getWindingMachinePosition(),
                sourceWindingRoller.getElectrodeType().getRollerName(),
                sourceWindingRoller.getBatteryModel(),
                targetEmptyRollLocationInformation.getEmptyRollRackPosition(),
                targetEmptyRollLocationInformation.getEmptyRollLocationPosition()
        );
    }

}
