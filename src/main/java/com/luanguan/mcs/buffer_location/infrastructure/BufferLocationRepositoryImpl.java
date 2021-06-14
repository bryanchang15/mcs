package com.luanguan.mcs.buffer_location.infrastructure;

import com.luanguan.mcs.buffer_location.application.FindFullRollLoadedBufferLocation;
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
public class BufferLocationRepositoryImpl implements FindFullRollLoadedBufferLocation, BufferLocationRepository {

    private final BufferLocationJpaRepository bufferLocationJpaRepository;

    @Override
    public Option<FullRollLoadedBufferLocation> findBy(BatteryModel batteryModel, ElectrodeType electrodeType) {
        return Match(
                Option.ofOptional(
                        bufferLocationJpaRepository.findByBufferLocationStateAndBufferBatteryModelAndFullRollElectrodeType(
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
                bufferLocationJpaRepository.findByBufferLocationId(bufferLocationId.getId())
                        .map(BufferLocationJpaEntity::toDomainModel)
        );
    }

    @Override
    public BufferLocation save(BufferLocation bufferLocation) {
        return bufferLocationJpaRepository.findByBufferLocationId(bufferLocation.bufferLocationId().getId())
                .map(entity -> updateOptimistically(entity, bufferLocation))
                .orElse(insertNew(bufferLocation));
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
            EmptyBufferLocation emptyBufferLocation
    ) {
        entity.buffer_location_state = Empty;
        entity.buffer_location_id = emptyBufferLocation.bufferLocationId().getId();
        entity.buffer_location_position = emptyBufferLocation.bufferLocationPosition().getPositionId();
        entity.buffer_battery_model = emptyBufferLocation.bufferBatteryModel().getModelName();
        entity.full_roll_electrode_type = emptyBufferLocation.fullRollElectrodeType().getValue();
        entity.version_number = emptyBufferLocation.getVersion().getVersionNum();
        try {
            entity = bufferLocationJpaRepository.save(entity);
            return Try.success(entity.toDomainModel());
        } catch (ObjectOptimisticLockingFailureException e) {
            return Try.failure(new AggregateRootIsStale(
                    "buffer location is updated at meantime, bufferLocation: " + emptyBufferLocation
            ));
        }
    }

    private Try<BufferLocation> update(
            BufferLocationJpaEntity entity,
            EmptyRollLoadedBufferLocation emptyRollLoadedBufferLocation
    ) {
        entity.buffer_location_state = EmptyRollLoaded;
        entity.buffer_location_id = emptyRollLoadedBufferLocation.bufferLocationId().getId();
        entity.buffer_location_position = emptyRollLoadedBufferLocation.bufferLocationPosition().getPositionId();
        entity.buffer_battery_model = emptyRollLoadedBufferLocation.bufferBatteryModel().getModelName();
        entity.full_roll_electrode_type = emptyRollLoadedBufferLocation.fullRollElectrodeType().getValue();
        entity.empty_roll_electrode_type = emptyRollLoadedBufferLocation.getEmptyRollElectrodeType().getValue();
        entity.roll_number = emptyRollLoadedBufferLocation.getEmptyRollNum();
        entity.version_number = emptyRollLoadedBufferLocation.getVersion().getVersionNum();
        try {
            entity = bufferLocationJpaRepository.save(entity);
            return Try.success(entity.toDomainModel());
        } catch (ObjectOptimisticLockingFailureException e) {
            return Try.failure(new AggregateRootIsStale(
                    "buffer location is updated at meantime, bufferLocation: " + emptyRollLoadedBufferLocation
            ));
        }
    }

    private Try<BufferLocation> update(
            BufferLocationJpaEntity entity,
            EmptyRollLoadingBufferLocation emptyRollLoadingBufferLocation
    ) {
        entity.buffer_location_state = EmptyRollLoading;
        entity.buffer_location_id = emptyRollLoadingBufferLocation.bufferLocationId().getId();
        entity.buffer_location_position = emptyRollLoadingBufferLocation.bufferLocationPosition().getPositionId();
        entity.buffer_battery_model = emptyRollLoadingBufferLocation.bufferBatteryModel().getModelName();
        entity.full_roll_electrode_type = emptyRollLoadingBufferLocation.fullRollElectrodeType().getValue();
        entity.mission_id = emptyRollLoadingBufferLocation.getByMission().getId();
        entity.empty_roll_electrode_type = emptyRollLoadingBufferLocation.getEmptyRollElectrodeType().getValue();
        entity.roll_number = emptyRollLoadingBufferLocation.getEmptyRollNum();
        entity.version_number = emptyRollLoadingBufferLocation.getVersion().getVersionNum();
        try {
            entity = bufferLocationJpaRepository.save(entity);
            return Try.success(entity.toDomainModel());
        } catch (ObjectOptimisticLockingFailureException e) {
            return Try.failure(new AggregateRootIsStale(
                    "buffer location is updated at meantime, bufferLocation: " + emptyRollLoadingBufferLocation
            ));
        }
    }

    private Try<BufferLocation> update(
            BufferLocationJpaEntity entity,
            FullRollLoadedBufferLocation fullRollLoadedBufferLocation
    ) {
        entity.buffer_location_state = FullRollLoaded;
        entity.buffer_location_id = fullRollLoadedBufferLocation.bufferLocationId().getId();
        entity.buffer_location_position = fullRollLoadedBufferLocation.bufferLocationPosition().getPositionId();
        entity.buffer_battery_model = fullRollLoadedBufferLocation.bufferBatteryModel().getModelName();
        entity.full_roll_electrode_type = fullRollLoadedBufferLocation.fullRollElectrodeType().getValue();
        entity.roll_number = fullRollLoadedBufferLocation.getFullRollNum();
        entity.version_number = fullRollLoadedBufferLocation.getVersion().getVersionNum();
        try {
            entity = bufferLocationJpaRepository.save(entity);
            return Try.success(entity.toDomainModel());
        } catch (ObjectOptimisticLockingFailureException e) {
            return Try.failure(new AggregateRootIsStale(
                    "buffer location is updated at meantime, bufferLocation: " + fullRollLoadedBufferLocation
            ));
        }
    }

    private Try<BufferLocation> update(
            BufferLocationJpaEntity entity,
            FullRollUnloadingBufferLocation fullRollUnloadingBufferLocation
    ) {
        entity.buffer_location_state = FullRollUnloading;
        entity.buffer_location_id = fullRollUnloadingBufferLocation.bufferLocationId().getId();
        entity.buffer_location_position = fullRollUnloadingBufferLocation.bufferLocationPosition().getPositionId();
        entity.buffer_battery_model = fullRollUnloadingBufferLocation.bufferBatteryModel().getModelName();
        entity.full_roll_electrode_type = fullRollUnloadingBufferLocation.fullRollElectrodeType().getValue();
        entity.mission_id = fullRollUnloadingBufferLocation.getByMission().getId();
        entity.roll_number = fullRollUnloadingBufferLocation.getFullRollNum();
        entity.version_number = fullRollUnloadingBufferLocation.getVersion().getVersionNum();
        try {
            entity = bufferLocationJpaRepository.save(entity);
            return Try.success(entity.toDomainModel());
        } catch (ObjectOptimisticLockingFailureException e) {
            return Try.failure(new AggregateRootIsStale(
                    "buffer location is updated at meantime, bufferLocation: " + fullRollUnloadingBufferLocation
            ));
        }
    }

    private Try<BufferLocation> update(
            BufferLocationJpaEntity entity,
            NoTrayBufferLocation noTrayBufferLocation
    ) {
        entity.buffer_location_state = NoTray;
        entity.buffer_location_id = noTrayBufferLocation.bufferLocationId().getId();
        entity.buffer_location_position = noTrayBufferLocation.bufferLocationPosition().getPositionId();
        entity.buffer_battery_model = noTrayBufferLocation.bufferBatteryModel().getModelName();
        entity.full_roll_electrode_type = noTrayBufferLocation.fullRollElectrodeType().getValue();
        entity.version_number = noTrayBufferLocation.getVersion().getVersionNum();
        try {
            entity = bufferLocationJpaRepository.save(entity);
            return Try.success(entity.toDomainModel());
        } catch (ObjectOptimisticLockingFailureException e) {
            return Try.failure(new AggregateRootIsStale(
                    "buffer location is updated at meantime, bufferLocation: " + noTrayBufferLocation
            ));
        }
    }

    private Try<BufferLocation> update(
            BufferLocationJpaEntity entity,
            TrayLoadingBufferLocation trayLoadingBufferLocation
    ) {
        entity.buffer_location_state = TrayLoading;
        entity.buffer_location_id = trayLoadingBufferLocation.bufferLocationId().getId();
        entity.buffer_location_position = trayLoadingBufferLocation.bufferLocationPosition().getPositionId();
        entity.buffer_battery_model = trayLoadingBufferLocation.bufferBatteryModel().getModelName();
        entity.full_roll_electrode_type = trayLoadingBufferLocation.fullRollElectrodeType().getValue();
        entity.mission_id = trayLoadingBufferLocation.getByMission().getId();
        entity.roll_number = trayLoadingBufferLocation.getFullRollNum();
        entity.version_number = trayLoadingBufferLocation.getVersion().getVersionNum();
        try {
            entity = bufferLocationJpaRepository.save(entity);
            return Try.success(entity.toDomainModel());
        } catch (ObjectOptimisticLockingFailureException e) {
            return Try.failure(new AggregateRootIsStale(
                    "buffer location is updated at meantime, bufferLocation: " + trayLoadingBufferLocation
            ));
        }
    }

    private BufferLocation insertNew(BufferLocation bufferLocation) {
        return Match(bufferLocation).of(
                Case($(instanceOf(EmptyBufferLocation.class)), this::insert),
                Case($(instanceOf(EmptyRollLoadedBufferLocation.class)), this::insert),
                Case($(instanceOf(EmptyRollLoadingBufferLocation.class)), this::insert),
                Case($(instanceOf(FullRollLoadedBufferLocation.class)), this::insert),
                Case($(instanceOf(FullRollUnloadingBufferLocation.class)), this::insert),
                Case($(instanceOf(NoTrayBufferLocation.class)), this::insert),
                Case($(instanceOf(TrayLoadingBufferLocation.class)), this::insert)
        );
    }

    @Modifying
    private BufferLocation insert(
            EmptyBufferLocation emptyBufferLocation
    ) {
        BufferLocationJpaEntity entity = new BufferLocationJpaEntity();

        entity.buffer_location_state = Empty;
        entity.buffer_location_id = emptyBufferLocation.bufferLocationId().getId();
        entity.buffer_location_position = emptyBufferLocation.bufferLocationPosition().getPositionId();
        entity.buffer_battery_model = emptyBufferLocation.bufferBatteryModel().getModelName();
        entity.full_roll_electrode_type = emptyBufferLocation.fullRollElectrodeType().getValue();
        entity.version_number = emptyBufferLocation.getVersion().getVersionNum();
        entity = bufferLocationJpaRepository.save(entity);

        return entity.toDomainModel();
    }

    private BufferLocation insert(
            EmptyRollLoadedBufferLocation emptyRollLoadedBufferLocation
    ) {
        BufferLocationJpaEntity entity = new BufferLocationJpaEntity();

        entity.buffer_location_state = EmptyRollLoaded;
        entity.buffer_location_id = emptyRollLoadedBufferLocation.bufferLocationId().getId();
        entity.buffer_location_position = emptyRollLoadedBufferLocation.bufferLocationPosition().getPositionId();
        entity.buffer_battery_model = emptyRollLoadedBufferLocation.bufferBatteryModel().getModelName();
        entity.full_roll_electrode_type = emptyRollLoadedBufferLocation.fullRollElectrodeType().getValue();
        entity.empty_roll_electrode_type = emptyRollLoadedBufferLocation.getEmptyRollElectrodeType().getValue();
        entity.roll_number = emptyRollLoadedBufferLocation.getEmptyRollNum();
        entity.version_number = emptyRollLoadedBufferLocation.getVersion().getVersionNum();
        entity = bufferLocationJpaRepository.save(entity);

        return entity.toDomainModel();
    }

    private BufferLocation insert(
            EmptyRollLoadingBufferLocation emptyRollLoadingBufferLocation
    ) {
        BufferLocationJpaEntity entity = new BufferLocationJpaEntity();

        entity.buffer_location_state = EmptyRollLoading;
        entity.buffer_location_id = emptyRollLoadingBufferLocation.bufferLocationId().getId();
        entity.buffer_location_position = emptyRollLoadingBufferLocation.bufferLocationPosition().getPositionId();
        entity.buffer_battery_model = emptyRollLoadingBufferLocation.bufferBatteryModel().getModelName();
        entity.full_roll_electrode_type = emptyRollLoadingBufferLocation.fullRollElectrodeType().getValue();
        entity.mission_id = emptyRollLoadingBufferLocation.getByMission().getId();
        entity.empty_roll_electrode_type = emptyRollLoadingBufferLocation.getEmptyRollElectrodeType().getValue();
        entity.roll_number = emptyRollLoadingBufferLocation.getEmptyRollNum();
        entity.version_number = emptyRollLoadingBufferLocation.getVersion().getVersionNum();
        entity = bufferLocationJpaRepository.save(entity);

        return entity.toDomainModel();
    }

    private BufferLocation insert(
            FullRollLoadedBufferLocation fullRollLoadedBufferLocation
    ) {
        BufferLocationJpaEntity entity = new BufferLocationJpaEntity();

        entity.buffer_location_state = FullRollLoaded;
        entity.buffer_location_id = fullRollLoadedBufferLocation.bufferLocationId().getId();
        entity.buffer_location_position = fullRollLoadedBufferLocation.bufferLocationPosition().getPositionId();
        entity.buffer_battery_model = fullRollLoadedBufferLocation.bufferBatteryModel().getModelName();
        entity.full_roll_electrode_type = fullRollLoadedBufferLocation.fullRollElectrodeType().getValue();
        entity.roll_number = fullRollLoadedBufferLocation.getFullRollNum();
        entity.version_number = fullRollLoadedBufferLocation.getVersion().getVersionNum();
        entity = bufferLocationJpaRepository.save(entity);

        return entity.toDomainModel();
    }

    private BufferLocation insert(
            FullRollUnloadingBufferLocation fullRollUnloadingBufferLocation
    ) {
        BufferLocationJpaEntity entity = new BufferLocationJpaEntity();

        entity.buffer_location_state = FullRollUnloading;
        entity.buffer_location_id = fullRollUnloadingBufferLocation.bufferLocationId().getId();
        entity.buffer_location_position = fullRollUnloadingBufferLocation.bufferLocationPosition().getPositionId();
        entity.buffer_battery_model = fullRollUnloadingBufferLocation.bufferBatteryModel().getModelName();
        entity.full_roll_electrode_type = fullRollUnloadingBufferLocation.fullRollElectrodeType().getValue();
        entity.mission_id = fullRollUnloadingBufferLocation.getByMission().getId();
        entity.roll_number = fullRollUnloadingBufferLocation.getFullRollNum();
        entity.version_number = fullRollUnloadingBufferLocation.getVersion().getVersionNum();
        entity = bufferLocationJpaRepository.save(entity);

        return entity.toDomainModel();
    }

    private BufferLocation insert(
            NoTrayBufferLocation noTrayBufferLocation
    ) {
        BufferLocationJpaEntity entity = new BufferLocationJpaEntity();

        entity.buffer_location_state = NoTray;
        entity.buffer_location_id = noTrayBufferLocation.bufferLocationId().getId();
        entity.buffer_location_position = noTrayBufferLocation.bufferLocationPosition().getPositionId();
        entity.buffer_battery_model = noTrayBufferLocation.bufferBatteryModel().getModelName();
        entity.full_roll_electrode_type = noTrayBufferLocation.fullRollElectrodeType().getValue();
        entity.version_number = noTrayBufferLocation.getVersion().getVersionNum();
        entity = bufferLocationJpaRepository.save(entity);

        return entity.toDomainModel();
    }

    private BufferLocation insert(
            TrayLoadingBufferLocation trayLoadingBufferLocation
    ) {
        BufferLocationJpaEntity entity = new BufferLocationJpaEntity();

        entity.buffer_location_state = TrayLoading;
        entity.buffer_location_id = trayLoadingBufferLocation.bufferLocationId().getId();
        entity.buffer_location_position = trayLoadingBufferLocation.bufferLocationPosition().getPositionId();
        entity.buffer_battery_model = trayLoadingBufferLocation.bufferBatteryModel().getModelName();
        entity.full_roll_electrode_type = trayLoadingBufferLocation.fullRollElectrodeType().getValue();
        entity.mission_id = trayLoadingBufferLocation.getByMission().getId();
        entity.roll_number = trayLoadingBufferLocation.getFullRollNum();
        entity.version_number = trayLoadingBufferLocation.getVersion().getVersionNum();
        entity = bufferLocationJpaRepository.save(entity);

        return entity.toDomainModel();
    }

}
