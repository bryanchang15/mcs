package com.luanguan.mcs.empty_roll_location.domain;

import com.luanguan.mcs.framework.domain.DomainEvent;
import lombok.NonNull;
import lombok.Value;

import java.time.Instant;
import java.util.UUID;

public interface EmptyRollLocationEvent extends DomainEvent {

    default EmptyRollLocationId emptyRollLocationId() {
        return new EmptyRollLocationId(getEmptyRollLocationId());
    }

    Long getEmptyRollLocationId();

    default Long getAggregateId() {
        return getEmptyRollLocationId();
    }

    @Value
    class EmptyRollLocationUnloaded implements EmptyRollLocationEvent {

        @NonNull UUID eventId = UUID.randomUUID();

        @NonNull Instant when;

        @NonNull Long emptyRollLocationId;

        public static EmptyRollLocationUnloaded now(EmptyRollLocationId emptyRollLocationId) {
            return new EmptyRollLocationUnloaded(Instant.now(), emptyRollLocationId.getId());
        }

    }

}
