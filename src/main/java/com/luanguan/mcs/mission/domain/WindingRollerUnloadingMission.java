package com.luanguan.mcs.mission.domain;

import com.luanguan.mcs.buffer_location.domain.BufferLocationId;
import com.luanguan.mcs.empty_roll_location.domain.EmptyRollLocationId;
import com.luanguan.mcs.framework.domain.Version;
import com.luanguan.mcs.external.robot.application.RobotTaskId;
import com.luanguan.mcs.winding_machine.domain.WindingRoller;
import io.vavr.control.Option;
import lombok.Getter;
import lombok.NonNull;

import static com.luanguan.mcs.mission.domain.MissionState.Completed;
import static com.luanguan.mcs.mission.domain.MissionState.Failed;

@Getter
public class WindingRollerUnloadingMission extends Mission {

    @NonNull
    private final WindingRoller sourceWindingRoller;

    private EmptyRollLocationId targetEmptyRollLocationId;

    public WindingRollerUnloadingMission(
            @NonNull MissionId missionId,
            @NonNull Version version,
            @NonNull MissionState missionState,
            MissionPendingReason missionPendingReason,
            RobotTaskId scheduledRobotTaskId,
            @NonNull WindingRoller sourceWindingRoller
    ) {
        super(missionId, version, missionState, missionPendingReason,scheduledRobotTaskId, null);
        this.sourceWindingRoller = sourceWindingRoller;
    }

    public Option<EmptyRollLocationId> getTargetEmptyRollLocationId() {
        if (null == this.targetEmptyRollLocationId) {
            return Option.none();
        }

        return Option.some(this.targetEmptyRollLocationId);
    }

    @Override
    public WindingRollerUnloadingMission pendBy(MissionPendingReason reason) {
        super.pendBy(reason);

        return this;
    }

    public WindingRollerUnloadingMission assignOf(EmptyRollLocationId targetEmptyRollLocationId) {
        super.becomeReady();
        this.targetEmptyRollLocationId = targetEmptyRollLocationId;

        return this;
    }

    @Override
    public WindingRollerUnloadingMission executeBy(RobotTaskId scheduledRobotTaskId) {
        super.executeBy(scheduledRobotTaskId);

        return this;
    }

    @Override
    public WindingRollerUnloadingMission complete() {
        super.complete();
        return this;
    }

    @Override
    public WindingRollerUnloadingMission fail() {
        super.fail();
        return this;
    }

}
