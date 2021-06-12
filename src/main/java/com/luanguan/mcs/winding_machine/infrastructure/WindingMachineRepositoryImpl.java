package com.luanguan.mcs.winding_machine.infrastructure;

import java.util.Optional;

import com.luanguan.mcs.shared_kernel.Position;
import com.luanguan.mcs.winding_machine.domain.WindingMachine;
import com.luanguan.mcs.winding_machine.domain.WindingMachineRepository;

import io.vavr.control.Option;
import org.springframework.data.jpa.repository.JpaRepository;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class WindingMachineRepositoryImpl implements WindingMachineRepository {

    private final WindingMachineJpaRepository jpaRepository;


    @Override
    public Option<WindingMachine> findByPosition(Position position) {
        return jpaRepository.findByPositionId(position.getPositionId()).map(WindingMachineJpaEntity::toDomainModel);
    }
}

interface WindingMachineJpaRepository extends JpaRepository<WindingMachineJpaEntity, Long> {

    Optional<WindingMachineJpaEntity> findByPositionId(String position_id);

}
