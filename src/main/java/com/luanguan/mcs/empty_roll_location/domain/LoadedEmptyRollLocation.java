package com.luanguan.mcs.empty_roll_location.domain;

import com.luanguan.mcs.framework.domain.DomainEvent;
import com.luanguan.mcs.framework.domain.Version;
import com.luanguan.mcs.mission.domain.MissionEvent.BufferLocationRollLoadingMissionScheduled;
import com.luanguan.mcs.shared_kernel.BatteryModel;
import com.luanguan.mcs.winding_machine.domain.ElectrodeType;
import io.vavr.control.Either;
import lombok.*;

@Value
@AllArgsConstructor
@EqualsAndHashCode(of = "emptyRollLocationInformation", callSuper = false)
public class LoadedEmptyRollLocation extends EmptyRollLocation {

    @NonNull EmptyRollLocationInformation emptyRollLocationInformation;

    @NonNull Version version;

    @NonNull BatteryModel batteryModel;

    @NonNull ElectrodeType electrodeType;

    public Either<DomainEvent, EmptyRollLocation> handle(
            BufferLocationRollLoadingMissionScheduled missionScheduled
    ) {
        return Either.right(new UnloadingEmptyRollLocation(
                emptyRollLocationInformation,
                version,
                batteryModel,
                electrodeType,
                missionScheduled.missionId()
        ));
    }

}
