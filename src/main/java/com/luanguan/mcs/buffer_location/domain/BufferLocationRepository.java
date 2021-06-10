package com.luanguan.mcs.buffer_location.domain;

import java.util.List;

import com.luanguan.mcs.mission.domain.MissionId;

public interface BufferLocationRepository {

    List<BufferLocation> findBy(MissionId missionId);

    BufferLocation save(BufferLocation bufferLocation);

}
