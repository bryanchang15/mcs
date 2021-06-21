package com.luanguan.mcs.empty_roll_location.application;

import com.luanguan.mcs.empty_roll_location.domain.*;
import com.luanguan.mcs.shared_kernel.BatteryModel;
import com.luanguan.mcs.winding_machine.domain.ElectrodeType;
import io.vavr.control.Option;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class FindUnloadedEmptyRollLocation {

    private final EmptyRollLocationService emptyRollLocationService;

    public Option<UnloadedEmptyRollLocation> findBy(BatteryModel batteryModel, ElectrodeType electrodeType) {
        return emptyRollLocationService.findUnloadedEmptyRollLocationBy(batteryModel, electrodeType);
    }

}
