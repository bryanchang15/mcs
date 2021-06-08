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
public class LoadedEmptyRollLocation implements EmptyRollLocation {

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

    public UnloadingEmptyRollLocation handle(MissionEvent.MissionScheduled missionScheduled) {
        return new UnloadingEmptyRollLocation(emptyRollLocationId, emptyRollLocationPosition, version, batteryModel,
                electrodeType, new MissionId(missionScheduled.getMissionId()));
    }

}
