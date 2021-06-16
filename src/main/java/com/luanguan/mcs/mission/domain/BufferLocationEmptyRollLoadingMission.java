package com.luanguan.mcs.mission.domain;

import com.luanguan.mcs.buffer_location.domain.BufferLocationId;
import com.luanguan.mcs.empty_roll_location.domain.EmptyRollLocationId;
import com.luanguan.mcs.framework.domain.Version;
import com.luanguan.mcs.shared_kernel.RobotTaskId;
import lombok.Getter;
import lombok.NonNull;

@Getter
public class BufferLocationEmptyRollLoadingMission extends Mission {

    @NonNull private final BufferLocationId targetBufferLocationId;

    private EmptyRollLocationId sourceEmptyRollLocationId = null;

    RobotTaskId scheduledRobotTaskId = null;

    public BufferLocationEmptyRollLoadingMission(
            @NonNull MissionId missionId,
            @NonNull Version version,
            @NonNull MissionState missionState,
            MissionPendingReason missionPendingReason,
            @NonNull BufferLocationId sourceBufferLocationId
    ) {
        super(missionId, version, missionState, missionPendingReason);
        this.targetBufferLocationId = sourceBufferLocationId;
    }

    public void assignOf(EmptyRollLocationId sourceEmptyRollLocationId) {
        super.ready();
        this.sourceEmptyRollLocationId = sourceEmptyRollLocationId;
    }

    public void executeBy(RobotTaskId scheduledRobotTaskId) {
        super.execute();
        this.scheduledRobotTaskId = scheduledRobotTaskId;
    }

}
