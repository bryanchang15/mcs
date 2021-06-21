package com.luanguan.mcs.buffer_location.domain;

import com.luanguan.mcs.shared_kernel.BatteryModel;
import com.luanguan.mcs.winding_machine.domain.ElectrodeType;
import io.vavr.control.Option;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BufferLocationService {

    private final BufferLocationRepository bufferLocationRepository;

    public Option<FullRollLoadedBufferLocation> findFullRollLoadedBufferLocationBy(
            BatteryModel batteryModel,
            ElectrodeType electrodeType
    ) {
        return bufferLocationRepository.findFullRollLoadedBufferLocationBy(
                batteryModel,
                electrodeType
        );
    }

}
