package com.luanguan.mcs.buffer_location.domain;

import com.luanguan.mcs.framework.domain.DomainEvent;
import com.luanguan.mcs.framework.domain.Version;
import com.luanguan.mcs.mission.domain.MissionEvent.*;
import com.luanguan.mcs.shared_kernel.BatteryModel;
import com.luanguan.mcs.shared_kernel.Position;
import com.luanguan.mcs.winding_machine.domain.ElectrodeType;
import io.vavr.control.Either;

public abstract class BufferLocation {

    public final BufferLocationId getBufferLocationId() {
        return getBufferLocationInformation().getBufferLocationId();
    }

    public final Position getBufferLocationPosition() {
        return getBufferLocationInformation().getBufferLocationPosition();
    }

    public final BatteryModel getBufferBatteryModel() {
        return getBufferLocationInformation().getBufferBatteryModel();
    }

    public final ElectrodeType getFullRollElectrodeType() {
        return getBufferLocationInformation().getFullRollElectrodeType();
    }

    public final Integer getTrayCapacity() {
        return getBufferLocationInformation().getTrayCapacity();
    }

    public abstract BufferLocationInformation getBufferLocationInformation();

    public abstract Version getVersion();

    public BufferLocation handle(MissionCompleted missionCompleted) {
        return this;
    }

    public BufferLocation handle(MissionFailed missionFailed) {
        return this;
    }

    public Either<DomainEvent, BufferLocation> handle(
            WindingRollerLoadingMissionScheduled missionScheduled
    ) {
        return Either.left(BufferLocationMisMatchedEvent.now(
                getBufferLocationId(),
                missionScheduled.missionId()
        ));
    }

    public Either<DomainEvent, BufferLocation> handle(
            BufferLocationRollLoadingMissionScheduled missionScheduled
    ) {
        return Either.left(BufferLocationMisMatchedEvent.now(
                getBufferLocationId(),
                missionScheduled.missionId()
        ));
    }

    public Either<DomainEvent, BufferLocation> handle(
            BufferLocationTrayUnloadingMissionScheduled missionScheduled
    ) {
        return Either.left(BufferLocationMisMatchedEvent.now(
                getBufferLocationId(),
                missionScheduled.missionId()
        ));
    }

    public Either<DomainEvent, BufferLocation> handle(
            BufferLocationTrayLoadingMissionScheduled missionScheduled
    ) {
        return Either.left(BufferLocationMisMatchedEvent.now(
                getBufferLocationId(),
                missionScheduled.missionId()
        ));
    }

}
