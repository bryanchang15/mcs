package com.luanguan.mcs.winding_machine.infrastructure;

import javax.persistence.*;

import com.luanguan.mcs.shared_kernel.Position;
import com.luanguan.mcs.winding_machine.domain.WindingMachine;
import com.luanguan.mcs.winding_machine.domain.WindingMachineId;

import java.util.UUID;

@Entity
@Table(name = "t_winding_machine")
public class WindingMachineJpaEntity {

    @Id
            @GeneratedValue
    Long id;

    @Column(unique = true, nullable = false)
    UUID winding_machine_id;

    @Column(nullable = false)
    String position_id;

    WindingMachine toDomainModel() {
        return new WindingMachine(new WindingMachineId(this.winding_machine_id), new Position(this.position_id));
    }

}
