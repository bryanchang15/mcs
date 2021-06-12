package com.luanguan.mcs.buffer_location.application;

import com.luanguan.mcs.buffer_location.domain.FullRollLoadedBufferLocation;
import com.luanguan.mcs.shared_kernel.BatteryModel;
import com.luanguan.mcs.winding_machine.domain.ElectrodeType;
import io.vavr.control.Option;

@FunctionalInterface
public interface FindFullRollLoadedBufferLocation {

    Option<FullRollLoadedBufferLocation> findBy(BatteryModel batteryModel, ElectrodeType electrodeType);

}
