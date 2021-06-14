package com.luanguan.mcs.buffer_location.infrastructure;

import com.luanguan.mcs.buffer_location.infrastructure.BufferLocationJpaEntity.BufferLocationState;
import io.vavr.Tuple1;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface BufferLocationJpaRepository extends JpaRepository<BufferLocationJpaEntity, Long> {

    Optional<BufferLocationJpaEntity> findByBufferLocationId(UUID buffer_location_id);

    Optional<BufferLocationJpaEntity> findByMissionId(UUID mission_id);

    Optional<BufferLocationJpaEntity> findByBufferLocationStateAndBufferBatteryModelAndFullRollElectrodeType(
            BufferLocationState buffer_location_state,
            String buffer_batter_model,
            Integer full_roll_electrode_type
    );

}
