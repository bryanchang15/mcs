package com.luanguan.mcs.empty_roll_location.infrastructure;

import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EmptyRollLocationJpaRepository extends JpaRepository<EmptyRollLocationJpaEntity, Long> {

    Optional<EmptyRollLocationJpaEntity> findByMissionId(@NonNull Long mission_id);

    List<EmptyRollLocationJpaEntity> findByState(EmptyRollLocationJpaEntity.EmptyRollLocationState state);

}
