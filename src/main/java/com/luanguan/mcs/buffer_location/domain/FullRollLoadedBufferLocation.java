package com.luanguan.mcs.buffer_location.domain;

import com.luanguan.mcs.framework.domain.Version;
import com.luanguan.mcs.mission.domain.MissionEvent.FullRollLoadingTaskScheduled;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Value;

@Value
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@EqualsAndHashCode(callSuper = false, of = "bufferLocationInformation")
public class FullRollLoadedBufferLocation extends BufferLocation {

    @NonNull
    BufferLocationInformation bufferLocationInformation;

    @NonNull
    Version version;

    @NonNull
    Integer fullRollNum;

    public FullRollUnloadingBufferLocation handle(FullRollLoadingTaskScheduled fullRollLoadingTaskScheduled) {
        return new FullRollUnloadingBufferLocation(bufferLocationInformation, version,
                fullRollLoadingTaskScheduled.missionId(), fullRollNum);
    }

}
