package com.luanguan.mcs.empty_roll_location.infrastructure;

import com.luanguan.mcs.empty_roll_location.domain.*;
import com.luanguan.mcs.empty_roll_location.infrastructure.EmptyRollLocationJpaEntity.EmptyRollLocationState;
import com.luanguan.mcs.framework.domain.AggregateRootIsStale;
import com.luanguan.mcs.mission.domain.MissionId;
import com.luanguan.mcs.shared_kernel.BatteryModel;
import com.luanguan.mcs.winding_machine.domain.ElectrodeType;
import io.vavr.collection.List;
import io.vavr.control.Option;
import io.vavr.control.Try;
import lombok.*;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.orm.ObjectOptimisticLockingFailureException;

import java.util.Objects;
import java.util.function.Function;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;
import static io.vavr.Predicates.instanceOf;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class EmptyRollLocationRepositoryImpl implements EmptyRollLocationRepository {

    private final EmptyRollLocationJpaRepository jpaRepository;

    @Override
    public List<LoadedEmptyRollLocation> findAllLoadedEmptyRollLocationBy(
            BatteryModel batteryModel,
            ElectrodeType electrodeType
    ) {
        List<LoadedEmptyRollLocation> locations = List.empty();
        jpaRepository.findByState(EmptyRollLocationState.Loaded).stream()
                .filter(entity -> Objects.equals(entity.battery_model, batteryModel.getModelName()))
                .filter(entity -> Objects.equals(entity.electrode_type, electrodeType.getValue()))
                .map(EmptyRollLocationJpaEntity::toDomainModel)
                .forEach(emptyRollLocation -> Match(emptyRollLocation).of(
                        Case($(instanceOf(LoadedEmptyRollLocation.class)), Function.identity())
                ));

        return locations;
    }

    @Override
    public List<UnloadedEmptyRollLocation> findAllUnloadedEmptyRollLocation() {
        List<UnloadedEmptyRollLocation> locations = List.empty();
        jpaRepository.findByState(EmptyRollLocationState.Unloaded).stream()
                .map(EmptyRollLocationJpaEntity::toDomainModel)
                .forEach(emptyRollLocation -> Match(emptyRollLocation).of(
                        Case($(instanceOf(UnloadedEmptyRollLocation.class)), locations::append)
                ));

        return locations;
    }

    @Override
    public Option<EmptyRollLocation> findBy(MissionId missionId) {
        return Option.ofOptional(
                jpaRepository.findByMissionId(missionId.getId())
                        .map(EmptyRollLocationJpaEntity::toDomainModel)
        );
    }

    @Override
    public Option<EmptyRollLocation> findBy(EmptyRollLocationId emptyRollLocationId) {
        return Option.ofOptional(
                jpaRepository.findById(emptyRollLocationId.getId())
                        .map(EmptyRollLocationJpaEntity::toDomainModel)
        );
    }

    @Override
    public EmptyRollLocation save(EmptyRollLocation emptyRollLocation) {
        return jpaRepository.findById(emptyRollLocation.getEmptyRollLocationId().getId())
                .map(entity -> updateOptimistically(entity, emptyRollLocation))
                .orElseThrow(() -> new RuntimeException("empty roll location can't be created"));
    }

    @SneakyThrows
    private EmptyRollLocation updateOptimistically(
            EmptyRollLocationJpaEntity entity,
            EmptyRollLocation emptyRollLocation
    ) {
        return Match(emptyRollLocation).of(
                Case($(instanceOf(LoadedEmptyRollLocation.class)), loadedEmptyRollLocation ->
                        update(entity, loadedEmptyRollLocation)
                ),
                Case($(instanceOf(LoadingEmptyRollLocation.class)), loadingEmptyRollLocation ->
                        update(entity, loadingEmptyRollLocation)
                ),
                Case($(instanceOf(UnloadedEmptyRollLocation.class)), unloadedEmptyRollLocation ->
                        update(entity, unloadedEmptyRollLocation)
                ),
                Case($(instanceOf(UnloadingEmptyRollLocation.class)), unloadingEmptyRollLocation ->
                        update(entity, unloadingEmptyRollLocation)
                )
        ).getOrElseThrow(t -> t);
    }

    @Modifying
    private Try<EmptyRollLocation> update(
            EmptyRollLocationJpaEntity entity,
            LoadedEmptyRollLocation emptyRollLocation
    ) {
        assert entity.id.equals(emptyRollLocation.getEmptyRollLocationId().getId());

        entity.state = EmptyRollLocationState.Loaded;
        entity.rack_position = emptyRollLocation.getEmptyRollRackPosition().getPositionId();
        entity.position = emptyRollLocation.getEmptyRollLocationPosition().getPositionId();
        entity.battery_model = emptyRollLocation.getBatteryModel().getModelName();
        entity.electrode_type = emptyRollLocation.getElectrodeType().getValue();
        entity.version_number = emptyRollLocation.getVersion().getVersionNum();
        try {
            entity = jpaRepository.save(entity);
            return Try.success(entity.toDomainModel());
        } catch (ObjectOptimisticLockingFailureException e) {
            return Try.failure(new AggregateRootIsStale(
                    "empty roll location is updated at meantime, emptyRollLocation: " + emptyRollLocation
            ));
        }
    }

    private Try<EmptyRollLocation> update(
            EmptyRollLocationJpaEntity entity,
            LoadingEmptyRollLocation emptyRollLocation
    ) {
        assert entity.id.equals(emptyRollLocation.getEmptyRollLocationId().getId());

        entity.state = EmptyRollLocationState.Loaded;
        entity.rack_position = emptyRollLocation.getEmptyRollRackPosition().getPositionId();
        entity.position = emptyRollLocation.getEmptyRollLocationPosition().getPositionId();
        entity.battery_model = emptyRollLocation.getBatteryModel().getModelName();
        entity.electrode_type = emptyRollLocation.getElectrodeType().getValue();
        entity.mission_id = emptyRollLocation.getByMission().getId();
        entity.version_number = emptyRollLocation.getVersion().getVersionNum();
        try {
            entity = jpaRepository.save(entity);
            return Try.success(entity.toDomainModel());
        } catch (ObjectOptimisticLockingFailureException e) {
            return Try.failure(new AggregateRootIsStale(
                    "empty roll location is updated at meantime, emptyRollLocation: " + emptyRollLocation
            ));
        }
    }

    private Try<EmptyRollLocation> update(
            EmptyRollLocationJpaEntity entity,
            UnloadedEmptyRollLocation emptyRollLocation
    ) {
        assert entity.id.equals(emptyRollLocation.getEmptyRollLocationId().getId());

        entity.state = EmptyRollLocationState.Loaded;
        entity.rack_position = emptyRollLocation.getEmptyRollRackPosition().getPositionId();
        entity.position = emptyRollLocation.getEmptyRollLocationPosition().getPositionId();
        entity.version_number = emptyRollLocation.getVersion().getVersionNum();
        try {
            entity = jpaRepository.save(entity);
            return Try.success(entity.toDomainModel());
        } catch (ObjectOptimisticLockingFailureException e) {
            return Try.failure(new AggregateRootIsStale(
                    "empty roll location is updated at meantime, emptyRollLocation: " + emptyRollLocation
            ));
        }
    }

    private Try<EmptyRollLocation> update(
            EmptyRollLocationJpaEntity entity,
            UnloadingEmptyRollLocation emptyRollLocation
    ) {
        assert entity.id.equals(emptyRollLocation.getEmptyRollLocationId().getId());

        entity.state = EmptyRollLocationState.Loaded;
        entity.rack_position = emptyRollLocation.getEmptyRollRackPosition().getPositionId();
        entity.position = emptyRollLocation.getEmptyRollLocationPosition().getPositionId();
        entity.battery_model = emptyRollLocation.getBatteryModel().getModelName();
        entity.electrode_type = emptyRollLocation.getElectrodeType().getValue();
        entity.mission_id = emptyRollLocation.getByMission().getId();
        entity.version_number = emptyRollLocation.getVersion().getVersionNum();
        try {
            entity = jpaRepository.save(entity);
            return Try.success(entity.toDomainModel());
        } catch (ObjectOptimisticLockingFailureException e) {
            return Try.failure(new AggregateRootIsStale(
                    "empty roll location is updated at meantime, emptyRollLocation: " + emptyRollLocation
            ));
        }
    }

}
