package com.luanguan.mcs.framework.infrastructure;

import com.luanguan.mcs.framework.domain.DomainEventRepository;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DomainEventConfig {

    @Bean
    DomainEventRepository domainEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        return new DomainEventSpringEventsRepository(applicationEventPublisher);
    }

}
