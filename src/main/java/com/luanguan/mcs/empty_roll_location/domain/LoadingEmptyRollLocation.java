package com.luanguan.mcs.empty_roll_location.domain;

import com.luanguan.mcs.framework.domain.Version;
import com.luanguan.mcs.mission.domain.MissionEvent;
import com.luanguan.mcs.mission.domain.MissionId;
import com.luanguan.mcs.shared_kernel.BatteryModel;
import com.luanguan.mcs.winding_machine.domain.ElectrodeType;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Value;

@Value
@AllArgsConstructor
@EqualsAndHashCode(of = "emptyRollLocationId")
public class LoadingEmptyRollLocation implements EmptyRollLocation {

    @NonNull
    EmptyRollLocationId emptyRollLocationId;

    @NonNull
    EmptyRollLocationPosition emptyRollLocationPosition;

    @NonNull
    Version version;

    @NonNull
    BatteryModel batteryModel;

    @NonNull
    ElectrodeType electrodeType;

    @NonNull
    MissionId byMission;

    public LoadedEmptyRollLocation handle(MissionEvent.MissionCompleted missionExpired) {
        return new LoadedEmptyRollLocation(emptyRollLocationId, emptyRollLocationPosition, version, batteryModel,
                electrodeType);
    }

    public UnloadedEmptyRollLocation handle(MissionEvent.MissionCanceled missionCanceled) {
        return new UnloadedEmptyRollLocation(emptyRollLocationId, emptyRollLocationPosition, version);
    }

    public UnloadedEmptyRollLocation handle(MissionEvent.MissionFailed missionFailed) {
        return new UnloadedEmptyRollLocation(emptyRollLocationId, emptyRollLocationPosition, version);
    }

}
