package com.luanguan.mcs.mission.domain;

import com.luanguan.mcs.buffer_location.domain.BufferLocationId;
import com.luanguan.mcs.empty_roll_location.domain.EmptyRollLocationId;
import com.luanguan.mcs.framework.domain.Version;
import com.luanguan.mcs.external.robot.application.RobotTaskId;
import com.luanguan.mcs.winding_machine.domain.WindingRoller;
import io.vavr.control.Option;
import lombok.*;

import static com.luanguan.mcs.mission.domain.MissionState.*;
import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;
import static io.vavr.Predicates.instanceOf;

@Getter
@AllArgsConstructor
@EqualsAndHashCode(of = "missionId")
public class WindingRollerUnloadingMission implements Mission {

    private final MissionId missionId;

    @NonNull private final Version version;

    @NonNull private MissionState missionState;

    private MissionPendingReason missionPendingReason;

    private RobotTaskId scheduledRobotTaskId;

    private final MissionId preMissionId = null;

    @NonNull
    private final WindingRoller sourceWindingRoller;

    private EmptyRollLocationId targetEmptyRollLocationId;

    public Option<MissionId> getPreMissionId() {
        return Option.none();
    }

    public Option<EmptyRollLocationId> getTargetEmptyRollLocationId() {
        if (null == targetEmptyRollLocationId) return Option.none();

        return Option.of(targetEmptyRollLocationId);
    }

    @Override
    public WindingRollerUnloadingMission pendBy(MissionPendingReason reason) {
        this.missionPendingReason = reason;
        this.missionState = Pending;
        return this;
    }

    @Override
    public WindingRollerUnloadingMission assignOf(Object targetEmptyRollLocationId) {
        return Match(targetEmptyRollLocationId).of(
                Case($(instanceOf(EmptyRollLocationId.class)), id -> {
                    this.targetEmptyRollLocationId = id;
                    this.missionState = Ready;
                    return this;
                }),
                Case($(), () -> this)
        );
    }

    @Override
    public WindingRollerUnloadingMission executeBy(RobotTaskId scheduledRobotTaskId) {
        this.scheduledRobotTaskId = scheduledRobotTaskId;
        this.missionState = Executing;
        return this;
    }

    @Override
    public WindingRollerUnloadingMission complete() {
        this.missionState = Completed;
        return this;
    }

    @Override
    public WindingRollerUnloadingMission fail() {
        this.missionState = Failed;
        return this;
    }

}
