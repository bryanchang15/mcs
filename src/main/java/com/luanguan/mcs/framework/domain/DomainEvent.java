package com.luanguan.mcs.framework.domain;

import java.time.Instant;
import java.util.UUID;

public interface DomainEvent {

    UUID getEventId();

    Long getAggregateId();

    Instant getWhen();

}
