package com.luanguan.mcs.mission.domain;

import com.luanguan.mcs.buffer_location.domain.BufferLocationId;
import com.luanguan.mcs.framework.domain.Version;
import com.luanguan.mcs.shared_kernel.RobotTaskId;
import lombok.Getter;
import lombok.NonNull;

@Getter
public class BufferLocationTrayUnloadingMission extends Mission {

    @NonNull
    private final BufferLocationId sourceBufferLocationId;

    private RobotTaskId scheduledRobotTaskId;

    public BufferLocationTrayUnloadingMission(
            @NonNull MissionId missionId,
            @NonNull Version version,
            @NonNull MissionState missionState,
            MissionPendingReason missionPendingReason,
            @NonNull BufferLocationId sourceBufferLocationId
    ) {
        super(missionId, version, missionState, missionPendingReason);
        this.sourceBufferLocationId = sourceBufferLocationId;
    }

    public void assignOf() {
        super.ready();
    }

    public void executeBy(RobotTaskId scheduledRobotTaskId) {
        super.execute();
        this.scheduledRobotTaskId = scheduledRobotTaskId;
    }

}
