package com.luanguan.mcs.empty_roll_location.domain;

import com.luanguan.mcs.framework.domain.DomainEvent;
import com.luanguan.mcs.framework.domain.Version;
import com.luanguan.mcs.mission.domain.MissionEvent.WindingRollerUnloadingMissionScheduled;
import io.vavr.control.Either;
import lombok.*;

@Value
@AllArgsConstructor
@EqualsAndHashCode(of = "emptyRollLocationInformation", callSuper = false)
public class UnloadedEmptyRollLocation extends EmptyRollLocation {

    @NonNull EmptyRollLocationInformation emptyRollLocationInformation;

    @NonNull Version version;

    public Either<DomainEvent, EmptyRollLocation> handle(
            WindingRollerUnloadingMissionScheduled missionScheduled
    ) {
        return Either.right(new LoadingEmptyRollLocation(
                emptyRollLocationInformation,
                version,
                missionScheduled.batteryModel(),
                missionScheduled.electrodeType(),
                missionScheduled.missionId()
        ));
    }

}
