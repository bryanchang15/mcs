package com.luanguan.mcs.empty_roll_location.domain;

import com.luanguan.mcs.framework.domain.Version;
import com.luanguan.mcs.mission.domain.MissionEvent;
import com.luanguan.mcs.mission.domain.MissionId;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Value;

@Value
@AllArgsConstructor
@EqualsAndHashCode(of = "emptyRollLocationInformation")
public class UnloadedEmptyRollLocation implements EmptyRollLocation {

    @NonNull
    EmptyRollLocationInformation emptyRollLocationInformation;

    @NonNull
    Version version;

    public LoadingEmptyRollLocation handle(MissionEvent.MissionScheduled missionScheduled) {
        return new LoadingEmptyRollLocation(emptyRollLocationId, emptyRollLocationPosition, version,
                missionScheduled.getBatteryModel(), missionScheduled.getElectrodeType,
                new MissionId(missionScheduled.getMissionId()));
    }

}
