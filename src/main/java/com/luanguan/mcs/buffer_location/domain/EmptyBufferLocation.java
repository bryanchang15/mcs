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
@EqualsAndHashCode(of = "bufferLocationInformation", callSuper = false)
public class EmptyBufferLocation extends BufferLocation {

    @NonNull BufferLocationInformation bufferLocationInformation;

    @NonNull Version version;

    public TrayPosition getTargetTrayPosition() {
        return TrayPosition.Inner;
    }

    @Override
    public Either<DomainEvent, BufferLocation> handle(
            BufferLocationRollLoadingMissionScheduled bufferLocationRollLoadingMissionScheduled
    ) {
        return Either.right(new EmptyRollLoadingBufferLocation(
                bufferLocationInformation,
                version,
                bufferLocationRollLoadingMissionScheduled.missionId(),
                bufferLocationRollLoadingMissionScheduled.electrodeType(),
                1
        ));
    }

    @Override
    public Either<DomainEvent, BufferLocation> handle(
            BufferLocationTrayUnloadingMissionScheduled trayUnloadingMissionScheduled
    ) {
        return Either.right(new NoTrayBufferLocation(bufferLocationInformation, version));
    }

}
