package com.luanguan.mcs.empty_roll_location.application;

import com.luanguan.mcs.empty_roll_location.domain.*;
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

    private final EmptyRollLocationRepository emptyRollLocationRepository;

    private final DomainEventRepository domainEventRepository;

    public void handle(MissionCompleted missionCompleted) {
        emptyRollLocationRepository.findBy(missionCompleted.missionId())
                .map(emptyRollLocation -> emptyRollLocation.handle(missionCompleted))
                .map(this::saveEmptyRollLocation)
                .map(emptyRollLocation -> Match(emptyRollLocation).of(
                        Case($(instanceOf(UnloadedEmptyRollLocation.class)), emptyBufferLocation ->
                                raiseDomainEvent(
                                        emptyRollLocation,
                                        EmptyRollLocationEvent.EmptyRollLocationUnloaded.now(
                                                emptyRollLocation.getEmptyRollLocationId()
                                        )
                                )
                        )
                ));
    }

    public void handle(MissionFailed missionFailed) {
        emptyRollLocationRepository.findBy(missionFailed.missionId())
                .map(emptyRollLocation -> emptyRollLocation.handle(missionFailed))
                .map(this::saveEmptyRollLocation)
                .map(emptyRollLocation -> Match(emptyRollLocation).of(
                        Case($(instanceOf(UnloadedEmptyRollLocation.class)), emptyBufferLocation ->
                                raiseDomainEvent(
                                        emptyRollLocation,
                                        EmptyRollLocationEvent.EmptyRollLocationUnloaded.now(
                                                emptyRollLocation.getEmptyRollLocationId()
                                        )
                                )
                        )
                ));
    }

    public void handle(WindingRollerUnloadingMissionScheduled missionScheduled) {
        emptyRollLocationRepository.findBy(missionScheduled.targetEmptyRollLocationId())
                .map(emptyRollLocation ->
                        emptyRollLocation.handle(missionScheduled)
                                .mapLeft(domainEvent -> raiseDomainEvent(
                                        emptyRollLocation,
                                        domainEvent
                                ))
                                .map(this::saveEmptyRollLocation)
                );
    }

    public void handle(BufferLocationRollLoadingMissionScheduled missionScheduled) {
        emptyRollLocationRepository.findBy(missionScheduled.sourceEmptyRollLocationId())
                .map(emptyRollLocation ->
                        emptyRollLocation.handle(missionScheduled)
                                .mapLeft(domainEvent -> raiseDomainEvent(
                                        emptyRollLocation,
                                        domainEvent
                                ))
                                .map(this::saveEmptyRollLocation)
                );
    }

    private EmptyRollLocation raiseDomainEvent(
            EmptyRollLocation emptyRollLocation,
            DomainEvent domainEvent
    ) {
        domainEventRepository.publish(domainEvent);

        return emptyRollLocation;
    }

    private EmptyRollLocation saveEmptyRollLocation(EmptyRollLocation emptyRollLocation) {
        return emptyRollLocationRepository.save(emptyRollLocation);
    }

}
