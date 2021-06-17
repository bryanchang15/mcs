package com.luanguan.mcs.empty_roll_location.application;

import java.util.Optional;

import com.luanguan.mcs.empty_roll_location.domain.EmptyRollLocationService;
import com.luanguan.mcs.empty_roll_location.domain.LoadedEmptyRollLocation;
import com.luanguan.mcs.shared_kernel.BatteryModel;
import com.luanguan.mcs.winding_machine.domain.ElectrodeType;

import io.vavr.control.Option;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class FindLoadedEmptyRollLocation {

    private final EmptyRollLocationService emptyRollLocationService;

    Option<LoadedEmptyRollLocation> findBy(BatteryModel batteryModel, ElectrodeType electrodeType) {
        return emptyRollLocationService.findLoadedEmptyRollLocationBy(batteryModel, electrodeType);
    }

}
