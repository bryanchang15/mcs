package com.luanguan.mcs.buffer_location.infrastructure;

import com.luanguan.mcs.buffer_location.domain.*;
import com.luanguan.mcs.framework.domain.AggregateRootIsStale;
import com.luanguan.mcs.mission.domain.MissionId;
import com.luanguan.mcs.shared_kernel.BatteryModel;
import com.luanguan.mcs.winding_machine.domain.ElectrodeType;
import io.vavr.control.Option;
import io.vavr.control.Try;
import lombok.*;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.orm.ObjectOptimisticLockingFailureException;

import static com.luanguan.mcs.buffer_location.infrastructure.BufferLocationJpaEntity.BufferLocationState.*;
import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;
import static io.vavr.Patterns.$Some;
import static io.vavr.Predicates.instanceOf;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class BufferLocationRepositoryImpl implements BufferLocationRepository {

    private final BufferLocationJpaRepository bufferLocationJpaRepository;

    @Override
    public Option<FullRollLoadedBufferLocation> findFullRollLoadedBufferLocationBy(
            BatteryModel batteryModel,
            ElectrodeType electrodeType
    ) {
        return Match(
                Option.ofOptional(
                        bufferLocationJpaRepository.findByStateAndBatteryModelAndFullElectrodeType(
                                FullRollLoaded,
                                batteryModel.getModelName(),
                                electrodeType.getValue()
                        ).map(BufferLocationJpaEntity::toDomainModel)
                )
        ).of(
                Case($Some($(instanceOf(FullRollLoadedBufferLocation.class))), Option::of),
                Case($(), Option::none)
        );
    }

    @Override
    public Option<BufferLocation> findBy(MissionId missionId) {
        return Option.ofOptional(
                bufferLocationJpaRepository.findByMissionId(missionId.getId())
                        .map(BufferLocationJpaEntity::toDomainModel)
        );
    }

    @Override
    public Option<BufferLocation> findBy(BufferLocationId bufferLocationId) {
        return Option.ofOptional(
                bufferLocationJpaRepository.findById(bufferLocationId.getId())
                        .map(BufferLocationJpaEntity::toDomainModel)
        );
    }

    @Override
    public BufferLocation save(BufferLocation bufferLocation) {
        return bufferLocationJpaRepository.findById(bufferLocation.getBufferLocationId().getId())
                .map(entity -> updateOptimistically(entity, bufferLocation))
                .orElseThrow(() -> new RuntimeException("buffer location can't be created"));
    }

    @SneakyThrows
    private BufferLocation updateOptimistically(BufferLocationJpaEntity entity, BufferLocation bufferLocation) {
        return Match(bufferLocation).of(
                Case($(instanceOf(EmptyBufferLocation.class)), emptyBufferLocation ->
                        update(entity, emptyBufferLocation)
                ),
                Case($(instanceOf(EmptyRollLoadedBufferLocation.class)), emptyRollLoadedBufferLocation ->
                        update(entity, emptyRollLoadedBufferLocation)
                ),
                Case($(instanceOf(EmptyRollLoadingBufferLocation.class)), emptyRollLoadingBufferLocation ->
                        update(entity, emptyRollLoadingBufferLocation)
                ),
                Case($(instanceOf(FullRollLoadedBufferLocation.class)), fullRollLoadedBufferLocation ->
                        update(entity, fullRollLoadedBufferLocation)
                ),
                Case($(instanceOf(FullRollUnloadingBufferLocation.class)), fullRollUnloadingBufferLocation ->
                        update(entity, fullRollUnloadingBufferLocation)
                ),
                Case($(instanceOf(NoTrayBufferLocation.class)), noTrayBufferLocation ->
                        update(entity, noTrayBufferLocation)
                ),
                Case($(instanceOf(TrayLoadingBufferLocation.class)), trayLoadingBufferLocation ->
                        update(entity, trayLoadingBufferLocation)
                )
        ).getOrElseThrow(t -> t);
    }

    @Modifying
    private Try<BufferLocation> update(
            BufferLocationJpaEntity entity,
            EmptyBufferLocation bufferLocation
    ) {
        assert entity.id.equals(bufferLocation.getBufferLocationId().getId());

        entity.state = Empty;
        entity.position = bufferLocation.getBufferLocationPosition().getPositionId();
        entity.battery_model = bufferLocation.getBufferBatteryModel().getModelName();
        entity.full_electrode_type = bufferLocation.getFullRollElectrodeType().getValue();
        entity.version_number = bufferLocation.getVersion().getVersionNum();
        try {
            entity = bufferLocationJpaRepository.save(entity);
            return Try.success(entity.toDomainModel());
        } catch (ObjectOptimisticLockingFailureException e) {
            return Try.failure(new AggregateRootIsStale(
                    "buffer location is updated at meantime, bufferLocation: " + bufferLocation
            ));
        }
    }

    private Try<BufferLocation> update(
            BufferLocationJpaEntity entity,
            EmptyRollLoadedBufferLocation bufferLocation
    ) {
        assert entity.id.equals(bufferLocation.getBufferLocationId().getId());

        entity.state = EmptyRollLoaded;
        entity.position = bufferLocation.getBufferLocationPosition().getPositionId();
        entity.battery_model = bufferLocation.getBufferBatteryModel().getModelName();
        entity.full_electrode_type = bufferLocation.getFullRollElectrodeType().getValue();
        entity.empty_electrode_type = bufferLocation.getEmptyRollElectrodeType().getValue();
        entity.roll_number = bufferLocation.getEmptyRollNum();
        entity.version_number = bufferLocation.getVersion().getVersionNum();
        try {
            entity = bufferLocationJpaRepository.save(entity);
            return Try.success(entity.toDomainModel());
        } catch (ObjectOptimisticLockingFailureException e) {
            return Try.failure(new AggregateRootIsStale(
                    "buffer location is updated at meantime, bufferLocation: " + bufferLocation
            ));
        }
    }

    private Try<BufferLocation> update(
            BufferLocationJpaEntity entity,
            EmptyRollLoadingBufferLocation bufferLocation
    ) {
        assert entity.id.equals(bufferLocation.getBufferLocationId().getId());

        entity.state = EmptyRollLoading;
        entity.position = bufferLocation.getBufferLocationPosition().getPositionId();
        entity.battery_model = bufferLocation.getBufferBatteryModel().getModelName();
        entity.full_electrode_type = bufferLocation.getFullRollElectrodeType().getValue();
        entity.mission_id = bufferLocation.getByMission().getId();
        entity.empty_electrode_type = bufferLocation.getEmptyRollElectrodeType().getValue();
        entity.roll_number = bufferLocation.getEmptyRollNum();
        entity.version_number = bufferLocation.getVersion().getVersionNum();
        try {
            entity = bufferLocationJpaRepository.save(entity);
            return Try.success(entity.toDomainModel());
        } catch (ObjectOptimisticLockingFailureException e) {
            return Try.failure(new AggregateRootIsStale(
                    "buffer location is updated at meantime, bufferLocation: " + bufferLocation
            ));
        }
    }

    private Try<BufferLocation> update(
            BufferLocationJpaEntity entity,
            FullRollLoadedBufferLocation bufferLocation
    ) {
        assert entity.id.equals(bufferLocation.getBufferLocationId().getId());

        entity.state = FullRollLoaded;
        entity.position = bufferLocation.getBufferLocationPosition().getPositionId();
        entity.battery_model = bufferLocation.getBufferBatteryModel().getModelName();
        entity.full_electrode_type = bufferLocation.getFullRollElectrodeType().getValue();
        entity.roll_number = bufferLocation.getFullRollNum();
        entity.version_number = bufferLocation.getVersion().getVersionNum();
        try {
            entity = bufferLocationJpaRepository.save(entity);
            return Try.success(entity.toDomainModel());
        } catch (ObjectOptimisticLockingFailureException e) {
            return Try.failure(new AggregateRootIsStale(
                    "buffer location is updated at meantime, bufferLocation: " + bufferLocation
            ));
        }
    }

    private Try<BufferLocation> update(
            BufferLocationJpaEntity entity,
            FullRollUnloadingBufferLocation bufferLocation
    ) {
        assert entity.id.equals(bufferLocation.getBufferLocationId().getId());

        entity.state = FullRollUnloading;
        entity.position = bufferLocation.getBufferLocationPosition().getPositionId();
        entity.battery_model = bufferLocation.getBufferBatteryModel().getModelName();
        entity.full_electrode_type = bufferLocation.getFullRollElectrodeType().getValue();
        entity.mission_id = bufferLocation.getByMission().getId();
        entity.roll_number = bufferLocation.getFullRollNum();
        entity.version_number = bufferLocation.getVersion().getVersionNum();
        try {
            entity = bufferLocationJpaRepository.save(entity);
            return Try.success(entity.toDomainModel());
        } catch (ObjectOptimisticLockingFailureException e) {
            return Try.failure(new AggregateRootIsStale(
                    "buffer location is updated at meantime, bufferLocation: " + bufferLocation
            ));
        }
    }

    private Try<BufferLocation> update(
            BufferLocationJpaEntity entity,
            NoTrayBufferLocation bufferLocation
    ) {
        assert entity.id.equals(bufferLocation.getBufferLocationId().getId());

        entity.state = NoTray;
        entity.position = bufferLocation.getBufferLocationPosition().getPositionId();
        entity.battery_model = bufferLocation.getBufferBatteryModel().getModelName();
        entity.full_electrode_type = bufferLocation.getFullRollElectrodeType().getValue();
        entity.version_number = bufferLocation.getVersion().getVersionNum();
        try {
            entity = bufferLocationJpaRepository.save(entity);
            return Try.success(entity.toDomainModel());
        } catch (ObjectOptimisticLockingFailureException e) {
            return Try.failure(new AggregateRootIsStale(
                    "buffer location is updated at meantime, bufferLocation: " + bufferLocation
            ));
        }
    }

    private Try<BufferLocation> update(
            BufferLocationJpaEntity entity,
            TrayLoadingBufferLocation bufferLocation
    ) {
        assert entity.id.equals(bufferLocation.getBufferLocationId().getId());

        entity.state = TrayLoading;
        entity.position = bufferLocation.getBufferLocationPosition().getPositionId();
        entity.battery_model = bufferLocation.getBufferBatteryModel().getModelName();
        entity.full_electrode_type = bufferLocation.getFullRollElectrodeType().getValue();
        entity.mission_id = bufferLocation.getByMission().getId();
        entity.roll_number = bufferLocation.getFullRollNum();
        entity.version_number = bufferLocation.getVersion().getVersionNum();
        try {
            entity = bufferLocationJpaRepository.save(entity);
            return Try.success(entity.toDomainModel());
        } catch (ObjectOptimisticLockingFailureException e) {
            return Try.failure(new AggregateRootIsStale(
                    "buffer location is updated at meantime, bufferLocation: " + bufferLocation
            ));
        }
    }

}
