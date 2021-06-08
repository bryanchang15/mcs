package com.luanguan.mcs.winding_machine.infrastructure;

import com.luanguan.mcs.winding_machine.application.FindWindingRoller;
import com.luanguan.mcs.winding_machine.domain.WindingMachineRepository;
import com.luanguan.mcs.winding_machine.domain.WindingMachineService;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories
public class WindingMachineConfig {

    @Bean
    public FindWindingRoller findWindingRoller(WindingMachineService windingMachineService) {
        return new FindWindingRoller(windingMachineService);
    }

    @Bean
    public WindingMachineService windingMachineService(WindingMachineRepository windingMachineRepository) {
        return new WindingMachineService(windingMachineRepository);
    }

    @Bean
    public WindingMachineRepository windingMachineRepository(WindingMachineJpaRepository jpaRepository) {
        return new WindingMachineRepositoryImpl(jpaRepository);
    }

}
