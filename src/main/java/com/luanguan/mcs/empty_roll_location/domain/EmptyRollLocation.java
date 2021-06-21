package com.luanguan.mcs.empty_roll_location.domain;

import com.luanguan.mcs.framework.domain.DomainEvent;
import com.luanguan.mcs.framework.domain.Version;
import com.luanguan.mcs.mission.domain.MissionEvent.*;
import com.luanguan.mcs.shared_kernel.Position;
import io.vavr.control.Either;

public abstract class EmptyRollLocation {

    public final EmptyRollLocationId getEmptyRollLocationId() {
        return getEmptyRollLocationInformation().getEmptyRollLocationId();
    }

    public final Position getEmptyRollRackPosition() {
        return getEmptyRollLocationInformation().getEmptyRollRackPosition();
    }

    public final Position getEmptyRollLocationPosition() {
        return getEmptyRollLocationInformation().getEmptyRollLocationPosition();
    }

    public abstract EmptyRollLocationInformation getEmptyRollLocationInformation();

    public abstract Version getVersion();

    public EmptyRollLocation handle(MissionCompleted missionCompleted) {
        return this;
    }

    public EmptyRollLocation handle(MissionFailed missionFailed) {
        return this;
    }

    public Either<DomainEvent, EmptyRollLocation> handle(
            WindingRollerUnloadingMissionScheduled missionScheduled
    ) {
        return Either.left(EmptyRollLocationMisMatchedEvent.now(
                getEmptyRollLocationId(),
                missionScheduled.missionId()
        ));
    }

    public Either<DomainEvent, EmptyRollLocation> handle(
            BufferLocationRollLoadingMissionScheduled missionScheduled
    ) {
        return Either.left(EmptyRollLocationMisMatchedEvent.now(
                getEmptyRollLocationId(),
                missionScheduled.missionId()
        ));
    }

}
