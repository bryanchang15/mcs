package com.luanguan.mcs.mission.domain;

import com.luanguan.mcs.buffer_location.domain.BufferLocationId;
import com.luanguan.mcs.empty_roll_location.domain.EmptyRollLocationId;
import com.luanguan.mcs.framework.domain.Version;
import com.luanguan.mcs.external.robot.application.RobotTaskId;
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
            MissionId preMissionId,
            @NonNull BufferLocationId sourceBufferLocationId
    ) {
        super(missionId, version, missionState, missionPendingReason, preMissionId);
        this.targetBufferLocationId = sourceBufferLocationId;
    }

    public BufferLocationEmptyRollLoadingMission assignOf(EmptyRollLocationId sourceEmptyRollLocationId) {
        super.ready();
        this.sourceEmptyRollLocationId = sourceEmptyRollLocationId;

        return this;
    }

    public BufferLocationEmptyRollLoadingMission executeBy(RobotTaskId scheduledRobotTaskId) {
        super.execute();
        this.scheduledRobotTaskId = scheduledRobotTaskId;

        return this;
    }

}
