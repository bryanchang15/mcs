package com.luanguan.mcs.winding_machine.infrastructure;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.luanguan.mcs.shared_kernel.Position;
import com.luanguan.mcs.winding_machine.domain.WindingMachine;
import com.luanguan.mcs.winding_machine.domain.WindingMachineId;

@Entity
@Table(name = "t_winding_machine")
public class WindingMachineJpaEntity {

    @Id
    Long id;

    @Column(nullable = false)
    String position_id;

    WindingMachine toDomainModel() {
        return new WindingMachine(new WindingMachineId(this.id), new Position(this.position_id));
    }

}
