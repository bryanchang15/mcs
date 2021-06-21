package com.luanguan.mcs.empty_roll_location.domain;

import com.luanguan.mcs.framework.domain.DomainEvent;
import com.luanguan.mcs.mission.domain.MissionId;
import lombok.NonNull;
import lombok.Value;

import java.time.Instant;
import java.util.UUID;

@Value
public class EmptyRollLocationMisMatchedEvent implements DomainEvent {

    @NonNull UUID eventId = UUID.randomUUID();

    @NonNull Instant when;

    @NonNull Long emptyRollLocationId;

    @NonNull Long missionId;

    public static EmptyRollLocationMisMatchedEvent now(
            EmptyRollLocationId emptyRollLocationId,
            MissionId missionId
    ) {
        return new EmptyRollLocationMisMatchedEvent(
                Instant.now(),
                emptyRollLocationId.getId(),
                missionId.getId());
    }

    public EmptyRollLocationId emptyRollLocationIdId() {
        return new EmptyRollLocationId(emptyRollLocationId);
    }

    public MissionId missionId() {
        return new MissionId(getMissionId());
    }

    @Override
    public Long getAggregateId() {
        return emptyRollLocationId;
    }

}
