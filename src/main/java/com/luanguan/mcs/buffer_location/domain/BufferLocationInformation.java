package com.luanguan.mcs.buffer_location.domain;

import com.luanguan.mcs.shared_kernel.BatteryModel;
import com.luanguan.mcs.shared_kernel.Position;
import com.luanguan.mcs.winding_machine.domain.ElectrodeType;

import lombok.NonNull;
import lombok.Value;

@Value
public class BufferLocationInformation {

    Integer TRAY_CAPACITY = 2;

    @NonNull
    BufferLocationId bufferLocationId;

    @NonNull
    Position bufferLocationPosition;

    @NonNull
    BatteryModel bufferBatteryModel;

    @NonNull
    ElectrodeType fullRollElectrodeType;

    public Integer getMaxRollNumCanLoad() {
        return TRAY_CAPACITY;
    }

}
