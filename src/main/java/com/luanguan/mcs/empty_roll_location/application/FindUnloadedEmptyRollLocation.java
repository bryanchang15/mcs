package com.luanguan.mcs.empty_roll_location.application;

import java.util.Optional;

import com.luanguan.mcs.empty_roll_location.domain.EmptyRollLocationService;
import com.luanguan.mcs.empty_roll_location.domain.UnloadedEmptyRollLocation;
import com.luanguan.mcs.shared_kernel.BatteryModel;
import com.luanguan.mcs.winding_machine.domain.ElectrodeType;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class FindUnloadedEmptyRollLocation {

    private final EmptyRollLocationService emptyRollLocationService;

    Optional<UnloadedEmptyRollLocation> findBy(BatteryModel batteryModel, ElectrodeType electrodeType) {
        return emptyRollLocationService.findUnloadedEmptyRollLocationBy(batteryModel, electrodeType);
    }

}