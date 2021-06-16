package com.luanguan.mcs.mission.domain;

import com.luanguan.mcs.buffer_location.domain.BufferLocationId;
import com.luanguan.mcs.framework.domain.Version;
import com.luanguan.mcs.shared_kernel.RobotTaskId;
import lombok.Getter;
import lombok.NonNull;

@Getter
public class BufferLocationTrayLoadingMission extends Mission {

    @NonNull
    private final BufferLocationId targetBufferLocationId;

    private RobotTaskId scheduledRobotTaskId;

    public BufferLocationTrayLoadingMission(
            @NonNull MissionId missionId,
            @NonNull Version version,
            @NonNull MissionState missionState,
            MissionPendingReason missionPendingReason,
            @NonNull BufferLocationId targetBufferLocationId
    ) {
        super(missionId, version, missionState, missionPendingReason);
        this.targetBufferLocationId = targetBufferLocationId;
    }

    public void assignOf() {
        super.ready();
    }

    public void executeBy(RobotTaskId scheduledRobotTaskId) {
        super.execute();
        this.scheduledRobotTaskId = scheduledRobotTaskId;
    }

}
