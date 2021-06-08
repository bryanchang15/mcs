package com.luanguan.mcs.framework.infrastructure;

import com.luanguan.mcs.framework.domain.DomainEvent;
import com.luanguan.mcs.framework.domain.DomainEventRepository;

import org.springframework.context.ApplicationEventPublisher;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class DomainEventSpringEventsRepository implements DomainEventRepository {

    private final ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void publish(DomainEvent event) {
        applicationEventPublisher.publishEvent(event);
    }

}
