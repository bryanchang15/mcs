package com.luanguan.mcs.mission.domain;

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

    private MissionId preMissionId;

    public Option<MissionPendingReason> getMissionPendingReason() {
        return Match(missionState).of(
                Case($(Pending), () -> Option.some(missionPendingReason)),
                Case($(), Option::none)
        );
    }

    void ready() {
        this.missionState = Ready;
    }

    public void pendBy(MissionPendingReason missionPendingReason) {
        this.missionState = Pending;
        this.missionPendingReason = missionPendingReason;
    }

    void execute() {
        this.missionState = Executing;
    }

    void complete() {
        this.missionState = Completed;
    }

    void fail() {
        this.missionState = Failed;
    }

}
