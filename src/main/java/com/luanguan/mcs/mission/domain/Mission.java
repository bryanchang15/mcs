package com.luanguan.mcs.mission.domain;

import com.luanguan.mcs.external.robot.application.RobotTaskId;
import com.luanguan.mcs.framework.domain.Version;
import io.vavr.control.Option;
import lombok.*;

import static com.luanguan.mcs.mission.domain.MissionState.*;
import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;

@AllArgsConstructor
@Getter
@EqualsAndHashCode(of = "missionId")
public abstract class Mission {

    @NonNull private final MissionId missionId;

    @NonNull private final Version version;

    @NonNull private MissionState missionState;

    private MissionPendingReason missionPendingReason;

    private RobotTaskId scheduledRobotTaskId;

    private MissionId preMissionId;

    public Option<MissionPendingReason> getMissionPendingReason() {
        return Match(this.missionState).of(
                Case($(Pending), () -> Option.of(this.missionPendingReason)),
                Case($(), Option::none)
        );
    }

    public Option<RobotTaskId> getScheduledRobotTaskId() {
        if (this.scheduledRobotTaskId == null) {
            return Option.none();
        }

        return Option.of(this.scheduledRobotTaskId);
    }

    public Option<MissionId> getPreMissionId() {
        if (this.preMissionId == null) {
            return Option.none();
        }

        return Option.of(this.preMissionId);
    }

    Mission becomeReady() {
        this.missionState = Ready;
        return this;
    }

    public Mission pendBy(MissionPendingReason missionPendingReason) {
        this.missionState = Pending;
        this.missionPendingReason = missionPendingReason;

        return this;
    }

    public Mission executeBy(RobotTaskId scheduledRobotTaskId) {
        this.missionState = Executing;
        this.scheduledRobotTaskId = scheduledRobotTaskId;
        return this;
    }

    public Mission complete() {
        this.missionState = Completed;
        return this;
    }

    public Mission fail() {
        this.missionState = Failed;
        return this;
    }

}
