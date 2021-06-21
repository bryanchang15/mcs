package com.luanguan.mcs.mission.domain;

import com.luanguan.mcs.buffer_location.domain.BufferLocationId;
import com.luanguan.mcs.external.robot.application.RobotTaskId;
import com.luanguan.mcs.framework.domain.Version;
import com.luanguan.mcs.winding_machine.domain.WindingRoller;
import io.vavr.API.Match.Case;
import io.vavr.control.Option;
import lombok.*;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;
import static io.vavr.Predicates.instanceOf;

@Getter
@AllArgsConstructor
@EqualsAndHashCode(of = "missionId")
public class WindingRollerLoadingMission implements Mission {

    private final MissionId missionId;

    @NonNull private final Version version;

    @NonNull private MissionState missionState;

    private MissionPendingReason missionPendingReason;

    private RobotTaskId scheduledRobotTaskId;

    private final MissionId preMissionId;

    @NonNull private final WindingRoller targetWindingRoller;

    private BufferLocationId sourceBufferLocationId;

    public Option<MissionId> getPreMissionId() {
        if (null == preMissionId) return Option.none();

        return Option.of(preMissionId);
    }

    public Option<BufferLocationId> getSourceBufferLocationId() {
        if (null == this.sourceBufferLocationId) {
            return Option.none();
        }

        return Option.some(this.sourceBufferLocationId);
    }

    @Override
    public WindingRollerLoadingMission pendBy(MissionPendingReason reason) {
        this.missionPendingReason = reason;
        this.missionState = MissionState.Pending;
        return this;
    }

    @Override
    public WindingRollerLoadingMission assignOf(Object sourceBufferLocationId) {
        return Match(sourceBufferLocationId).of(
                Case($(instanceOf(BufferLocationId.class)), id -> {
                    this.sourceBufferLocationId = id;
                    this.missionState = MissionState.Ready;
                    return this;
                }),
                Case($(), () -> this)
        );
    }

    @Override
    public WindingRollerLoadingMission executeBy(RobotTaskId scheduledRobotTaskId) {
        this.scheduledRobotTaskId = scheduledRobotTaskId;
        this.missionState = MissionState.Executing;
        return this;
    }

    @Override
    public WindingRollerLoadingMission complete() {
        this.missionState = MissionState.Completed;
        return this;
    }

    @Override
    public WindingRollerLoadingMission fail() {
        this.missionState = MissionState.Failed;
        return this;
    }

}
