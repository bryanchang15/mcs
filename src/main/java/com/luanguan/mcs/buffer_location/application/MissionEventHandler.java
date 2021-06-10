package com.luanguan.mcs.buffer_location.application;

import com.luanguan.mcs.buffer_location.domain.*;
import com.luanguan.mcs.framework.domain.DomainEventRepository;
import com.luanguan.mcs.mission.domain.MissionEvent.MissionCompleted;
import io.vavr.API;
import lombok.AllArgsConstructor;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.Predicates.instanceOf;

@AllArgsConstructor
public class MissionEventHandler {

    private final BufferLocationRepository bufferLocationRepository;

    private final DomainEventRepository domainEventRepository;

    public void handle(MissionCompleted missionCompleted) {
        bufferLocationRepository.findBy(missionCompleted.missionId()).stream()
                .map(bufferLocation -> handleMissionCompleted(bufferLocation, missionCompleted))
                .map(this::saveBufferLocation)
                .close();
    }

    private BufferLocation handleMissionCompleted(BufferLocation bufferLocation, MissionCompleted missionCompleted) {
        return API
                .Match(bufferLocation).of(
                        Case($(instanceOf(EmptyRollLoadingBufferLocation.class)),
                                emptyRollLoadingBufferLocation -> emptyRollLoadingBufferLocation
                                        .handle(missionCompleted)),
                        Case($(instanceOf(FullRollUnloadingBufferLocation.class)),
                                fullRollUnloadingBufferLocation -> fullRollUnloadingBufferLocation
                                        .handle(missionCompleted).mapLeft(emptyBufferLocation -> {
                                            domainEventRepository.publish(BufferLocationEvent.BufferLocationEmptied
                                                    .now(emptyBufferLocation.bufferLocationId()));
                                            return emptyBufferLocation;
                                        }).fold(l -> l, r -> r)));
    }

    private BufferLocation saveBufferLocation(BufferLocation bufferLocation) {
        bufferLocationRepository.save(bufferLocation);

        return bufferLocation;
    }

}
