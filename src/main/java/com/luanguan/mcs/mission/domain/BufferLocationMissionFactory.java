package com.luanguan.mcs.mission.domain;

import com.luanguan.mcs.buffer_location.domain.BufferLocationId;
import com.luanguan.mcs.framework.domain.Version;
import lombok.NonNull;

public class BufferLocationMissionFactory {

    public static BufferLocationRollLoadingMission rollLoading(
            @NonNull BufferLocationId targetBufferLocationId
    ) {
        return new BufferLocationRollLoadingMission(
                null,
                Version.zero(),
                MissionState.Created,
                null,
                null,
                targetBufferLocationId,
                null
        );
    }

}
