package com.luanguan.mcs.buffer_location.application;

import com.luanguan.mcs.buffer_location.domain.*;
import com.luanguan.mcs.framework.domain.DomainEvent;
import com.luanguan.mcs.framework.domain.DomainEventRepository;
import com.luanguan.mcs.mission.domain.MissionEvent.*;
import lombok.AllArgsConstructor;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;
import static io.vavr.Predicates.instanceOf;

@AllArgsConstructor
public class MissionEventHandler {

    private final BufferLocationRepository bufferLocationRepository;

    private final DomainEventRepository domainEventRepository;

    public void handle(MissionCompleted missionCompleted) {
        bufferLocationRepository.findBy(missionCompleted.missionId())
                .map(bufferLocation -> bufferLocation.handle(missionCompleted))
                .map(this::saveBufferLocation)
                .map(bufferLocation -> Match(bufferLocation).of(
                        Case($(instanceOf(EmptyBufferLocation.class)), emptyBufferLocation ->
                                raiseDomainEvent(
                                        emptyBufferLocation,
                                        BufferLocationEvent.BufferLocationEmptied.now(
                                                bufferLocation.bufferLocationId()
                                        )
                                )
                        ),
                        Case($(instanceOf(FullRollLoadedBufferLocation.class)), fullRollLoadedBufferLocation ->
                                raiseDomainEvent(
                                        fullRollLoadedBufferLocation,
                                        BufferLocationEvent.BufferLocationLoaded.now(
                                                bufferLocation.bufferLocationId(),
                                                bufferLocation.bufferBatteryModel(),
                                                bufferLocation.fullRollElectrodeType()
                                        )
                                )
                        )
                ));
    }

    public void handle(MissionFailed missionFailed) {
        bufferLocationRepository.findBy(missionFailed.missionId())
                .map(bufferLocation -> bufferLocation.handle(missionFailed))
                .map(this::saveBufferLocation);
    }

    public void handle(WindingRollerLoadingMissionScheduled windingRollerLoadingMissionScheduled) {
        bufferLocationRepository.findBy(windingRollerLoadingMissionScheduled.sourceBufferLocationId())
                .map(bufferLocation ->
                        bufferLocation.handle(windingRollerLoadingMissionScheduled)
                                .mapLeft(domainEvent -> raiseDomainEvent(bufferLocation, domainEvent))
                                .map(this::saveBufferLocation)
                );
    }

    public void handle(BufferLocationEmptyRollLoadingMissionScheduled bufferLocationEmptyRollLoadingMissionScheduled) {
        bufferLocationRepository.findBy(bufferLocationEmptyRollLoadingMissionScheduled.targetBufferLocationId())
                .map(bufferLocation ->
                        bufferLocation.handle(bufferLocationEmptyRollLoadingMissionScheduled)
                                .mapLeft(domainEvent -> raiseDomainEvent(bufferLocation, domainEvent))
                                .map(this::saveBufferLocation)
                );
    }

    public void handle(TrayUnloadingTaskScheduled trayUnloadingTaskScheduled) {
        bufferLocationRepository.findBy(trayUnloadingTaskScheduled.sourceBufferLocationId())
                .map(bufferLocation ->
                        bufferLocation.handle(trayUnloadingTaskScheduled)
                                .mapLeft(domainEvent -> raiseDomainEvent(bufferLocation, domainEvent))
                                .map(this::saveBufferLocation)
                );
    }

    public void handle(TrayLoadingTaskScheduled trayLoadingTaskScheduled) {
        bufferLocationRepository.findBy(trayLoadingTaskScheduled.targetBufferLocationId())
                .map(bufferLocation ->
                        bufferLocation.handle(trayLoadingTaskScheduled)
                                .mapLeft(domainEvent -> raiseDomainEvent(bufferLocation, domainEvent))
                                .map(this::saveBufferLocation)
                );
    }

    private BufferLocation raiseDomainEvent(BufferLocation bufferLocation, DomainEvent domainEvent) {
        domainEventRepository.publish(domainEvent);

        return bufferLocation;
    }

    private BufferLocation saveBufferLocation(BufferLocation bufferLocation) {
        return bufferLocationRepository.save(bufferLocation);
    }

}
