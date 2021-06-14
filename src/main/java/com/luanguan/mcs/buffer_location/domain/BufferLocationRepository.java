package com.luanguan.mcs.buffer_location.domain;


import com.luanguan.mcs.mission.domain.MissionId;
import com.luanguan.mcs.shared_kernel.BatteryModel;
import com.luanguan.mcs.winding_machine.domain.ElectrodeType;
import io.vavr.collection.List;
import io.vavr.control.Option;

public interface BufferLocationRepository {

    Option<BufferLocation> findBy(MissionId missionId);

    Option<BufferLocation> findBy(BufferLocationId bufferLocationId);

    BufferLocation save(BufferLocation bufferLocation);

}
