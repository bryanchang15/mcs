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

import static io.vavr.API.*;
import static io.vavr.Predicates.is;

@Value
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@EqualsAndHashCode(callSuper = false, of = "bufferLocationInformation")
public class FullRollLoadedBufferLocation extends BufferLocation {

    @NonNull
    BufferLocationInformation bufferLocationInformation;

    @NonNull
    Version version;

    @NonNull
    Integer fullRollNum;

    @Override
    public Either<DomainEvent, BufferLocation> handle(FullRollLoadingTaskScheduled fullRollLoadingTaskScheduled) {
        return Either.right(new FullRollUnloadingBufferLocation(
                bufferLocationInformation,
                version,
                fullRollLoadingTaskScheduled.missionId(),
                fullRollNum
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
        return Either.left(BufferLocationMisMatchedEvent.now(
                bufferLocationId(),
                trayLoadingTaskScheduled.missionId()
        ));
    }

}
