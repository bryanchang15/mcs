package com.luanguan.mcs.framework.domain;

import java.util.List;

public interface DomainEventRepository {

    DomainEvent publish(DomainEvent event);

    default List<DomainEvent> publish(List<DomainEvent> events) {
        events.forEach(this::publish);
        return events;
    }

}
