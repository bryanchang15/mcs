package com.luanguan.mcs.winding_machine.application;

import com.luanguan.mcs.framework.application.ApplicationService;
import com.luanguan.mcs.shared_kernel.BatteryModel;
import com.luanguan.mcs.shared_kernel.Position;
import com.luanguan.mcs.shared_kernel.WindingRollerName;
import com.luanguan.mcs.winding_machine.domain.WindingMachineService;
import com.luanguan.mcs.winding_machine.domain.WindingRoller;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class FindWindingRoller implements ApplicationService {

    private final WindingMachineService windingMachineService;

    WindingRoller findBy(Position windingMachinePosition, BatteryModel batteryModel, WindingRollerName rollerName) {
        return windingMachineService.findWindingRollerBy(windingMachinePosition, batteryModel, rollerName);
    }

}
