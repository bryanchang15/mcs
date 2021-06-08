package com.luanguan.mcs.mission.domain;

import org.springframework.lang.Nullable;

import lombok.NonNull;
import lombok.Value;

@Value
public class MissionInformation {

    @NonNull
    MissionId missionId;

    @NonNull
    MissionType missionType;

    @NonNull
    MissionState missionState;

    @NonNull
    PendingReason pendingReason;

    public Boolean isRollReloading() {
        return (this.missionType == MissionType.RollReloading);
    }

    @Value
    public static class PendingReason {

        @Nullable
        String reason;

    }

}
