package com.luanguan.mcs.mission.domain;

import com.luanguan.mcs.empty_roll_location.domain.EmptyRollLocationId;
import com.luanguan.mcs.framework.domain.Version;
import com.luanguan.mcs.shared_kernel.RobotTaskId;
import com.luanguan.mcs.winding_machine.domain.WindingRoller;
import lombok.Getter;
import lombok.NonNull;

@Getter
public class WindingRollerUnloadingMission extends Mission {

    @NonNull
    private final WindingRoller sourceWindingRoller;

    private EmptyRollLocationId targetEmptyRollLocationId = null;

    private RobotTaskId scheduledRobotTaskId = null;

    public WindingRollerUnloadingMission(
            @NonNull MissionId missionId,
            @NonNull Version version,
            @NonNull MissionState missionState,
            MissionPendingReason missionPendingReason,
            @NonNull WindingRoller sourceWindingRoller
    ) {
        super(missionId, version, missionState, missionPendingReason);
        this.sourceWindingRoller = sourceWindingRoller;
    }

    public void assignOf(EmptyRollLocationId targetEmptyRollLocationId) {
        super.ready();
        this.targetEmptyRollLocationId = targetEmptyRollLocationId;
    }

    public void executeBy(RobotTaskId scheduledRobotTaskId) {
        super.execute();
        this.scheduledRobotTaskId = scheduledRobotTaskId;
    }

}
