package com.luanguan.mcs.buffer_location.domain;

import com.luanguan.mcs.framework.domain.Version;
import com.luanguan.mcs.shared_kernel.BatteryModel;
import com.luanguan.mcs.shared_kernel.Position;
import com.luanguan.mcs.winding_machine.domain.ElectrodeType;

public abstract class BufferLocation {

    public BufferLocationId bufferLocationId() {
        return getBufferLocationInformation().getBufferLocationId();
    }

    public Position bufferLocationPosition() {
        return getBufferLocationInformation().getBufferLocationPosition();
    }

    public BatteryModel bufferBatteryModel() {
        return getBufferLocationInformation().getBufferBatteryModel();
    }

    public ElectrodeType fullRollElectrodeType() {
        return getBufferLocationInformation().getFullRollElectrodeType();
    }

    public Integer getMaxRollNumCanLoad() {
        return getBufferLocationInformation().getMaxRollNumCanLoad();
    }

    public abstract BufferLocationInformation getBufferLocationInformation();

    public abstract Version getVersion();

}
