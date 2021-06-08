package com.luanguan.mcs.winding_machine.domain;

import com.luanguan.mcs.shared_kernel.BatteryModel;
import com.luanguan.mcs.shared_kernel.Position;
import com.luanguan.mcs.shared_kernel.WindingRollerName;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class WindingMachineService {
    // 此处需要考虑极片类型与料轴外部名称的转换关系

    private final WindingMachineRepository windingMachineRepository;

    public WindingRoller findWindingRollerBy(Position windingMachinePosition, BatteryModel batteryModel, WindingRollerName rollerName) {

        WindingMachine machine = windingMachineRepository.findByPosition(windingMachinePosition)
                .orElseThrow(() -> new IllegalArgumentException("No such winding machine"));
        ElectrodeType electrodeType = ElectrodeType.getByRollerName(rollerName)
                .orElseThrow(() -> new IllegalArgumentException("No such winding roller"));

        return new WindingRoller(machine.windingMachineId(), machine.position, batteryModel, electrodeType);
    }

}
