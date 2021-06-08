package com.luanguan.mcs.winding_machine.domain;

import com.luanguan.mcs.framework.domain.AggregateRoot;
import com.luanguan.mcs.shared_kernel.Position;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

@AllArgsConstructor
@EqualsAndHashCode(of = "windingMachineId")
public class WindingMachine implements AggregateRoot {

    @NonNull
    WindingMachineId windingMachineId;

    @NonNull
    Position position;

    public WindingMachineId windingMachineId() {
        return this.windingMachineId;
    }

    public Position position() {
        return this.position;
    }

}
