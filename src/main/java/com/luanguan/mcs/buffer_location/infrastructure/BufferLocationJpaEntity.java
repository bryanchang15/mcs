package com.luanguan.mcs.buffer_location.infrastructure;

import com.luanguan.mcs.buffer_location.domain.*;
import com.luanguan.mcs.framework.domain.Version;
import com.luanguan.mcs.mission.domain.MissionId;
import com.luanguan.mcs.shared_kernel.BatteryModel;
import com.luanguan.mcs.shared_kernel.Position;
import com.luanguan.mcs.winding_machine.domain.ElectrodeType;
import org.hibernate.annotations.OptimisticLockType;
import org.hibernate.annotations.OptimisticLocking;

import javax.persistence.*;
import java.util.UUID;

import static com.luanguan.mcs.buffer_location.infrastructure.BufferLocationJpaEntity.BufferLocationState.*;
import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;

@Entity
@Table(name = "t_buffer_location")
@OptimisticLocking(type= OptimisticLockType.VERSION)
class BufferLocationJpaEntity {

    @Id
    @GeneratedValue
    Long id;
    @Column(unique = true, nullable = false)
    UUID buffer_location_id;
    @Column(nullable = false)
    String buffer_location_position;
    @Column(nullable = false)
    String buffer_battery_model;
    @Column(nullable = false)
    int full_roll_electrode_type;
    @Column(nullable = false)
    BufferLocationState buffer_location_state;
    int empty_roll_electrode_type;
    int roll_number;
    UUID mission_id;
    @javax.persistence.Version
    int version_number;

    BufferLocation toDomainModel() {
        return Match(buffer_location_state).of(
                Case($(Empty), () -> new EmptyBufferLocation(
                        new BufferLocationInformation(
                                new BufferLocationId(buffer_location_id),
                                new Position(buffer_location_position),
                                new BatteryModel(buffer_battery_model),
                                ElectrodeType.getByValue(full_roll_electrode_type)
                        ),
                        new Version(version_number)
                )),
                Case($(EmptyRollLoaded), () -> new EmptyRollLoadedBufferLocation(
                        new BufferLocationInformation(
                                new BufferLocationId(buffer_location_id),
                                new Position(buffer_location_position),
                                new BatteryModel(buffer_battery_model),
                                ElectrodeType.getByValue(full_roll_electrode_type)
                        ),
                        new Version(version_number),
                        ElectrodeType.getByValue(empty_roll_electrode_type),
                        roll_number
                )),
                Case($(EmptyRollLoading), () -> new EmptyRollLoadingBufferLocation(
                        new BufferLocationInformation(
                                new BufferLocationId(buffer_location_id),
                                new Position(buffer_location_position),
                                new BatteryModel(buffer_battery_model),
                                ElectrodeType.getByValue(full_roll_electrode_type)
                        ),
                        new Version(version_number),
                        new MissionId(mission_id),
                        ElectrodeType.getByValue(empty_roll_electrode_type),
                        roll_number
                )),
                Case($(FullRollLoaded), () -> new FullRollLoadedBufferLocation(
                        new BufferLocationInformation(
                                new BufferLocationId(buffer_location_id),
                                new Position(buffer_location_position),
                                new BatteryModel(buffer_battery_model),
                                ElectrodeType.getByValue(full_roll_electrode_type)
                        ),
                        new Version(version_number),
                        roll_number
                )),
                Case($(FullRollUnloading), () -> new FullRollUnloadingBufferLocation(
                        new BufferLocationInformation(
                                new BufferLocationId(buffer_location_id),
                                new Position(buffer_location_position),
                                new BatteryModel(buffer_battery_model),
                                ElectrodeType.getByValue(full_roll_electrode_type)
                        ),
                        new Version(version_number),
                        new MissionId(mission_id),
                        roll_number
                )),
                Case($(NoTray), () -> new NoTrayBufferLocation(
                        new BufferLocationInformation(
                                new BufferLocationId(buffer_location_id),
                                new Position(buffer_location_position),
                                new BatteryModel(buffer_battery_model),
                                ElectrodeType.getByValue(full_roll_electrode_type)
                        ),
                        new Version(version_number)
                )),
                Case($(TrayLoading), () -> new TrayLoadingBufferLocation(
                        new BufferLocationInformation(
                                new BufferLocationId(buffer_location_id),
                                new Position(buffer_location_position),
                                new BatteryModel(buffer_battery_model),
                                ElectrodeType.getByValue(full_roll_electrode_type)
                        ),
                        new Version(version_number),
                        new MissionId(mission_id),
                        roll_number
                ))
        );
    }

    enum BufferLocationState {
        Empty, EmptyRollLoaded, EmptyRollLoading, FullRollLoaded, FullRollUnloading, NoTray, TrayLoading;
    }

}
