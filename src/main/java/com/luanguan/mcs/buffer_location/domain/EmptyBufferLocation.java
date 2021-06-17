package com.luanguan.mcs.buffer_location.domain;

import com.luanguan.mcs.framework.domain.DomainEvent;
import com.luanguan.mcs.framework.domain.Version;
import com.luanguan.mcs.mission.domain.MissionEvent.*;

import com.luanguan.mcs.shared_kernel.*;
import io.vavr.control.Either;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Value;

@Value
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false, of = "bufferLocationInformation")
public class EmptyBufferLocation extends BufferLocation {

    @NonNull
    BufferLocationInformation bufferLocationInformation;

    @NonNull
    Version version;

    public TrayPosition getTargetTrayPosition() {
        return TrayPosition.Inner;
    }

    @Override
    public Either<DomainEvent, BufferLocation> handle(
            WindingRollerLoadingMissionScheduled windingRollerLoadingMissionScheduled
    ) {
        return Either.left(BufferLocationMisMatchedEvent.now(
                bufferLocationId(),
                windingRollerLoadingMissionScheduled.missionId()
        ));
    }

    @Override
    public Either<DomainEvent, BufferLocation> handle(
            BufferLocationEmptyRollLoadingMissionScheduled bufferLocationEmptyRollLoadingMissionScheduled
    ) {
        return Either.right(new EmptyRollLoadingBufferLocation(
                bufferLocationInformation,
                version,
                bufferLocationEmptyRollLoadingMissionScheduled.missionId(),
                bufferLocationEmptyRollLoadingMissionScheduled.electrodeType(),
                1
        ));
    }

    @Override
    public Either<DomainEvent, BufferLocation> handle(
            TrayUnloadingTaskScheduled trayUnloadingMissionScheduled
    ) {
        return Either.right(new NoTrayBufferLocation(bufferLocationInformation, version));
    }

    @Override
    public Either<DomainEvent, BufferLocation> handle(
            TrayLoadingTaskScheduled trayLoadingMissionScheduled
    ) {
        return Either.left(BufferLocationMisMatchedEvent.now(
                bufferLocationId(),
                trayLoadingMissionScheduled.missionId()
        ));
    }

}
