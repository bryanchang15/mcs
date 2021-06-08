package com.luanguan.mcs.winding_machine.domain;

import com.luanguan.mcs.framework.domain.ValueObject;
import com.luanguan.mcs.shared_kernel.BatteryModel;
import com.luanguan.mcs.shared_kernel.Position;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

@AllArgsConstructor
@Getter
public class WindingRoller implements ValueObject {

    @NonNull
    WindingMachineId windingMachineId;

    @NonNull
    Position windingMachinePosition;

    @NonNull
    BatteryModel batteryModel;

    @NonNull
    ElectrodeType electrodeType;

}
