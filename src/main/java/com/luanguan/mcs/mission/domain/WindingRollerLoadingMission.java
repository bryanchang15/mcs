package com.luanguan.mcs.mission.domain;

import com.luanguan.mcs.buffer_location.domain.BufferLocation;
import com.luanguan.mcs.buffer_location.domain.BufferLocationId;
import com.luanguan.mcs.framework.domain.Version;
import com.luanguan.mcs.external.robot.application.RobotTaskId;
import com.luanguan.mcs.winding_machine.domain.WindingRoller;
import io.vavr.control.Option;
import lombok.Getter;
import lombok.NonNull;

@Getter
public class WindingRollerLoadingMission extends Mission {

    @NonNull private final WindingRoller targetWindingRoller;

    BufferLocationId sourceBufferLocationId = null;

    public WindingRollerLoadingMission(
            @NonNull MissionId missionId,
            @NonNull Version version,
            @NonNull MissionState missionState,
            MissionPendingReason missionPendingReason,
            RobotTaskId scheduledRobotTaskId,
            MissionId preMissionId,
            @NonNull WindingRoller targetWindingRoller
    ) {
        super(missionId, version, missionState, missionPendingReason, scheduledRobotTaskId, preMissionId);
        this.targetWindingRoller = targetWindingRoller;
    }

    public Option<BufferLocationId> getSourceBufferLocationId() {
        if (null == this.sourceBufferLocationId) {
            return Option.none();
        }

        return Option.some(this.sourceBufferLocationId);
    }

    @Override
    public WindingRollerLoadingMission pendBy(MissionPendingReason reason) {
        super.pendBy(reason);
        return this;
    }

    public WindingRollerLoadingMission assignOf(BufferLocationId sourceBufferLocationId) {
        super.becomeReady();
        this.sourceBufferLocationId = sourceBufferLocationId;

        return this;
    }

    @Override
    public WindingRollerLoadingMission executeBy(RobotTaskId scheduledRobotTaskId) {
        super.executeBy(scheduledRobotTaskId);

        return this;
    }

    @Override
    public WindingRollerLoadingMission complete() {
        super.complete();
        return this;
    }

    @Override
    public WindingRollerLoadingMission fail() {
        super.fail();
        return this;
    }

}
