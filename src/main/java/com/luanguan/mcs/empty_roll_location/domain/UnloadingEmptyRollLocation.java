package com.luanguan.mcs.empty_roll_location.domain;

import com.luanguan.mcs.framework.domain.Version;
import com.luanguan.mcs.mission.domain.MissionEvent.MissionCompleted;
import com.luanguan.mcs.mission.domain.MissionEvent.MissionFailed;
import com.luanguan.mcs.mission.domain.MissionId;
import com.luanguan.mcs.shared_kernel.BatteryModel;
import com.luanguan.mcs.winding_machine.domain.ElectrodeType;
import lombok.*;

@Value
@AllArgsConstructor
@EqualsAndHashCode(of = "emptyRollLocationInformation", callSuper = false)
public class UnloadingEmptyRollLocation extends EmptyRollLocation {

    @NonNull EmptyRollLocationInformation emptyRollLocationInformation;

    @NonNull Version version;

    @NonNull BatteryModel batteryModel;

    @NonNull ElectrodeType electrodeType;

    @NonNull MissionId byMission;

    public LoadedEmptyRollLocation handle(MissionFailed missionFailed) {
        return new LoadedEmptyRollLocation(
                emptyRollLocationInformation,
                version,
                batteryModel,
                electrodeType
        );
    }

    public UnloadedEmptyRollLocation handle(MissionCompleted missionCompleted) {
        return new UnloadedEmptyRollLocation(emptyRollLocationInformation, version);
    }

}
