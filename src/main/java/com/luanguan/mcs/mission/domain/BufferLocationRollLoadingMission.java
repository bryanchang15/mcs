package com.luanguan.mcs.mission.domain;

import com.luanguan.mcs.buffer_location.domain.BufferLocationId;
import com.luanguan.mcs.empty_roll_location.domain.EmptyRollLocationId;
import com.luanguan.mcs.framework.domain.Version;
import com.luanguan.mcs.external.robot.application.RobotTaskId;
import io.vavr.control.Option;
import lombok.*;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;
import static io.vavr.Predicates.instanceOf;

@Getter
@AllArgsConstructor
@EqualsAndHashCode(of = "missionId")
public class BufferLocationRollLoadingMission implements Mission {

    private final MissionId missionId;

    @NonNull private final Version version;

    @NonNull private MissionState missionState;

    private MissionPendingReason missionPendingReason;

    private RobotTaskId scheduledRobotTaskId;

    private final MissionId preMissionId = null;

    @NonNull private final BufferLocationId targetBufferLocationId;

    private EmptyRollLocationId sourceEmptyRollLocationId;

    public Option<MissionId> getPreMissionId() {
        return Option.none();
    }

    @Override
    public BufferLocationRollLoadingMission assignOf(Object sourceEmptyRollLocationId) {
        return Match(sourceEmptyRollLocationId).of(
                Case($(instanceOf(EmptyRollLocationId.class)), id -> {
                    this.sourceEmptyRollLocationId = id;
                    this.missionState = MissionState.Ready;
                    return this;
                }),
                Case($(), () -> this)
        );
    }

    @Override
    public Mission pendBy(MissionPendingReason reason) {
        this.missionPendingReason = reason;
        this.missionState = MissionState.Pending;

        return this;
    }

    public BufferLocationRollLoadingMission executeBy(RobotTaskId scheduledRobotTaskId) {
        this.scheduledRobotTaskId = scheduledRobotTaskId;
        this.missionState = MissionState.Executing;

        return this;
    }

    @Override
    public Mission complete() {
        this.missionState = MissionState.Completed;

        return this;
    }

    @Override
    public Mission fail() {
        this.missionState = MissionState.Failed;

        return this;
    }

}
