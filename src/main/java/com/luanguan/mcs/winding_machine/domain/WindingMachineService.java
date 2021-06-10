package com.luanguan.mcs.winding_machine.domain;

import com.luanguan.mcs.shared_kernel.BatteryModel;
import com.luanguan.mcs.shared_kernel.Position;
import com.luanguan.mcs.shared_kernel.WindingRollerName;

import io.vavr.control.Try;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class WindingMachineService {
    // 此处需要考虑极片类型与料轴外部名称的转换关系

    private final WindingMachineRepository windingMachineRepository;

    public Try<WindingRoller> findWindingRollerBy(Position windingMachinePosition, BatteryModel batteryModel,
            WindingRollerName rollerName) {

        return windingMachineRepository.findByPosition(windingMachinePosition)
                .map(machine -> ElectrodeType.getByRollerName(rollerName)
                        .map(electrodeType -> new WindingRoller(machine.windingMachineId(), machine.position,
                                batteryModel, electrodeType)))
                .getOrElse(Try.failure(new IllegalArgumentException("no such winding machine")));
    }

}
