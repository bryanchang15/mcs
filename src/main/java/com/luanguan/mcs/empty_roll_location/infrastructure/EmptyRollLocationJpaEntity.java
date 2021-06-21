package com.luanguan.mcs.empty_roll_location.infrastructure;

import com.luanguan.mcs.empty_roll_location.domain.*;
import com.luanguan.mcs.framework.domain.Version;
import com.luanguan.mcs.mission.domain.MissionId;
import com.luanguan.mcs.shared_kernel.BatteryModel;
import com.luanguan.mcs.shared_kernel.Position;
import com.luanguan.mcs.winding_machine.domain.ElectrodeType;
import org.hibernate.annotations.OptimisticLockType;
import org.hibernate.annotations.OptimisticLocking;

import javax.persistence.*;

import static com.luanguan.mcs.empty_roll_location.infrastructure.EmptyRollLocationJpaEntity.EmptyRollLocationState.*;
import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;

@Entity
@Table(name = "t_empty_roll_location")
@OptimisticLocking(type = OptimisticLockType.VERSION)
class EmptyRollLocationJpaEntity {

    @Id
    @GeneratedValue
    Long id;

    @Column(nullable = false)
    String rack_position;

    @Column(nullable = false)
    String position;

    @Column(nullable = false)
    EmptyRollLocationState state;

    String battery_model;

    int electrode_type;

    Long mission_id;

    @javax.persistence.Version
    @Column(nullable = false)
    int version_number;

    EmptyRollLocation toDomainModel() {
        return Match(state).of(
                Case($(Loaded), () -> new LoadedEmptyRollLocation(
                        new EmptyRollLocationInformation(
                                new EmptyRollLocationId(id),
                                new Position(rack_position),
                                new Position(position)
                        ),
                        new Version(version_number),
                        new BatteryModel(battery_model),
                        ElectrodeType.getByValue(electrode_type)
                )),
                Case($(Loading), () -> new LoadingEmptyRollLocation(
                        new EmptyRollLocationInformation(
                                new EmptyRollLocationId(id),
                                new Position(rack_position),
                                new Position(position)
                        ),
                        new Version(version_number),
                        new BatteryModel(battery_model),
                        ElectrodeType.getByValue(electrode_type),
                        new MissionId(mission_id)
                )),
                Case($(Unloaded), () -> new UnloadedEmptyRollLocation(
                        new EmptyRollLocationInformation(
                                new EmptyRollLocationId(id),
                                new Position(rack_position),
                                new Position(position)
                        ),
                        new Version(version_number)
                )),
                Case($(Unloading), () -> new UnloadingEmptyRollLocation(
                        new EmptyRollLocationInformation(
                                new EmptyRollLocationId(id),
                                new Position(rack_position),
                                new Position(position)
                        ),
                        new Version(version_number),
                        new BatteryModel(battery_model),
                        ElectrodeType.getByValue(electrode_type),
                        new MissionId(mission_id)
                ))
        );
    }

    enum EmptyRollLocationState {
        Loaded, Loading, Unloaded, Unloading
    }

}
