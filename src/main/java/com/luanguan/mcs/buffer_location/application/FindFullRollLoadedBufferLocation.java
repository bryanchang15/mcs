package com.luanguan.mcs.buffer_location.application;

import com.luanguan.mcs.buffer_location.domain.*;
import com.luanguan.mcs.shared_kernel.BatteryModel;
import com.luanguan.mcs.winding_machine.domain.ElectrodeType;
import io.vavr.control.Option;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class FindFullRollLoadedBufferLocation {

    private final BufferLocationService bufferLocationService;

    public Option<FullRollLoadedBufferLocation> findBy(
            BatteryModel batteryModel,
            ElectrodeType electrodeType
    ) {
        return bufferLocationService.findFullRollLoadedBufferLocationBy(batteryModel, electrodeType);
    }

}
