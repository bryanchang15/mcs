package com.luanguan.mcs.mission.domain;

import com.luanguan.mcs.buffer_location.domain.BufferLocationId;
import com.luanguan.mcs.framework.domain.Version;
import com.luanguan.mcs.shared_kernel.RobotTaskId;
import com.luanguan.mcs.winding_machine.domain.WindingRoller;
import lombok.Getter;
import lombok.NonNull;

@Getter
public class WindingRollerLoadingMission extends Mission {

    @NonNull private final WindingRoller targetWindingRoller;

    BufferLocationId sourceBufferLocationId = null;

    RobotTaskId scheduledRobotTaskId = null;

    public WindingRollerLoadingMission(
            @NonNull MissionId missionId,
            @NonNull Version version,
            @NonNull MissionState missionState,
            MissionPendingReason missionPendingReason,
            MissionId preMissionId,
            @NonNull WindingRoller targetWindingRoller
    ) {
        super(missionId, version, missionState, missionPendingReason, preMissionId);
        this.targetWindingRoller = targetWindingRoller;
    }

    public void assignOf(BufferLocationId sourceBufferLocationId) {
        super.ready();
        this.sourceBufferLocationId = sourceBufferLocationId;
    }

    public void executeBy(RobotTaskId scheduledRobotTaskId) {
        super.execute();
        this.scheduledRobotTaskId = scheduledRobotTaskId;
    }

}
