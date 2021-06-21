package com.luanguan.mcs.empty_roll_location.infrastructure;

import com.luanguan.mcs.empty_roll_location.application.*;
import com.luanguan.mcs.empty_roll_location.domain.*;
import com.luanguan.mcs.framework.domain.DomainEventRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories
public class EmptyRollLocationConfig {

    @Bean
    FindUnloadedEmptyRollLocation findUnloadedEmptyRollLocation(
            EmptyRollLocationService emptyRollLocationService
    ) {
        return new FindUnloadedEmptyRollLocation(emptyRollLocationService);
    }

    @Bean
    FindLoadedEmptyRollLocation findLoadedEmptyRollLocation(
            EmptyRollLocationService emptyRollLocationService
    ) {
        return new FindLoadedEmptyRollLocation(emptyRollLocationService);
    }

    @Bean
    MissionEventHandler missionEventHandler(
            EmptyRollLocationRepository emptyRollLocationRepository,
            DomainEventRepository domainEventRepository
    ) {
        return new MissionEventHandler(emptyRollLocationRepository, domainEventRepository);
    }

    @Bean
    EmptyRollLocationService emptyRollLocationService(
            EmptyRollLocationRepository emptyRollLocationRepository
    ) {
        return new EmptyRollLocationService(emptyRollLocationRepository);
    }

    @Bean
    EmptyRollLocationRepository emptyRollLocationRepository(
            EmptyRollLocationJpaRepository emptyRollLocationJpaRepository
    ) {
        return new EmptyRollLocationRepositoryImpl(emptyRollLocationJpaRepository);
    }

    @Bean
    MissionEventHandlerSpringEvents missionEventHandlerSpringEvents(
            MissionEventHandler missionEventHandler
    ) {
        return new MissionEventHandlerSpringEvents(missionEventHandler);
    }

}
