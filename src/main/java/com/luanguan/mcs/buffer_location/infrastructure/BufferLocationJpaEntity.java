package com.luanguan.mcs.buffer_location.infrastructure;

import com.luanguan.mcs.buffer_location.domain.BufferLocation;
import io.vavr.API.Match.Case;

import javax.persistence.*;
import java.util.UUID;

import static com.luanguan.mcs.buffer_location.infrastructure.BufferLocationJpaEntity.BufferLocationState.*;
import static io.vavr.API.$;
import static io.vavr.API.Match;

@Entity
@Table(name = "t_buffer_location")
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
    @Column(nullable = false)
    int version;

    BufferLocation toDomainModel() {
        return Match(buffer_location_state).of(
                Case($(Empty), () ->),
                Case($(EmptyRollLoaded), () ->),
                Case($(EmptyRollLoading), () ->),
                Case($(FullRollLoaded), () ->),
                Case($(FullRollUnloading), () ->),
                Case($(NoTray), () ->),
                Case($(TrayLoading), () ->)
        );
    }

    enum BufferLocationState {
        Empty, EmptyRollLoaded, EmptyRollLoading, FullRollLoaded, FullRollUnloading, NoTray, TrayLoading;
    }

}
