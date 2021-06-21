package com.luanguan.mcs.buffer_location.infrastructure;

import com.luanguan.mcs.buffer_location.infrastructure.BufferLocationJpaEntity.BufferLocationState;
import io.vavr.Tuple1;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface BufferLocationJpaRepository extends JpaRepository<BufferLocationJpaEntity, Long> {

    Optional<BufferLocationJpaEntity> findByMissionId(Long mission_id);

    Optional<BufferLocationJpaEntity> findByStateAndBatteryModelAndFullElectrodeType(
            BufferLocationState state,
            String battery_model,
            Integer full_electrode_type
    );

}
