package com.luanguan.mcs.buffer_location.infrastructure;

import com.luanguan.mcs.buffer_location.application.FindFullRollLoadedBufferLocation;
import com.luanguan.mcs.buffer_location.application.MissionEventHandler;
import com.luanguan.mcs.buffer_location.domain.BufferLocationRepository;
import com.luanguan.mcs.framework.domain.DomainEventRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories
public class BufferLocationConfig {

    @Bean
    MissionEventHandler missionEventHandler(
            BufferLocationRepository bufferLocationRepository,
            DomainEventRepository domainEventRepository
    ) {
        return new MissionEventHandler(bufferLocationRepository, domainEventRepository);
    }

    @Bean
    BufferLocationRepository bufferLocationRepository(
            BufferLocationJpaRepository bufferLocationJpaRepository
    ) {
        return new BufferLocationRepositoryImpl(bufferLocationJpaRepository);
    }

    @Bean
    MissionEventHandlerSpringEvents missionEventHandlerSpringEvents(
            MissionEventHandler missionEventHandler
    ) {
        return new MissionEventHandlerSpringEvents(missionEventHandler);
    }

}
