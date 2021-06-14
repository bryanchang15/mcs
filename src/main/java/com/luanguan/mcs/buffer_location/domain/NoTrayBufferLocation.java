package com.luanguan.mcs.buffer_location.domain;

import com.luanguan.mcs.framework.domain.DomainEvent;
import com.luanguan.mcs.framework.domain.Version;
import com.luanguan.mcs.mission.domain.MissionEvent;

import com.luanguan.mcs.mission.domain.MissionEvent.*;
import io.vavr.control.Either;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Value;

@Value
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false, of = "bufferLocationInformation")
public class NoTrayBufferLocation extends BufferLocation {

    @NonNull
    BufferLocationInformation bufferLocationInformation;

    @NonNull
    Version version;

    @Override
    public Either<DomainEvent, BufferLocation> handle(FullRollLoadingTaskScheduled fullRollLoadingTaskScheduled) {
        return Either.left(BufferLocationMisMatchedEvent.now(
                bufferLocationId(),
                fullRollLoadingTaskScheduled.missionId()
        ));
    }

    @Override
    public Either<DomainEvent, BufferLocation> handle(EmptyRollLoadingTaskScheduled emptyRollLoadingTaskScheduled) {
        return Either.left(BufferLocationMisMatchedEvent.now(
                bufferLocationId(),
                emptyRollLoadingTaskScheduled.missionId()
        ));
    }

    @Override
    public Either<DomainEvent, BufferLocation> handle(TrayUnloadingTaskScheduled trayUnloadingTaskScheduled) {
        return Either.left(BufferLocationMisMatchedEvent.now(
                bufferLocationId(),
                trayUnloadingTaskScheduled.missionId()
        ));
    }

    @Override
    public Either<DomainEvent, BufferLocation> handle(TrayLoadingTaskScheduled trayLoadingTaskScheduled) {
        return Either.right(new TrayLoadingBufferLocation(
                bufferLocationInformation,
                version,
                trayLoadingTaskScheduled.missionId(),
                trayLoadingTaskScheduled.getFullRollNum()
        ));
    }

}
