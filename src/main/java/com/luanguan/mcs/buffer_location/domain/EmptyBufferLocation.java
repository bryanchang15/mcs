package com.luanguan.mcs.buffer_location.domain;

import com.luanguan.mcs.framework.domain.Version;
import com.luanguan.mcs.mission.domain.MissionEvent.*;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Value;

@Value
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@EqualsAndHashCode(callSuper = false, of = "bufferLocationInformation")
public class EmptyBufferLocation extends BufferLocation {

    @NonNull
    BufferLocationInformation bufferLocationInformation;

    @NonNull
    Version version;

    public EmptyRollLoadingBufferLocation handle(EmptyRollLoadingTaskScheduled emptyRollLoadingTaskScheduled) {
        return new EmptyRollLoadingBufferLocation(bufferLocationInformation, version,
                emptyRollLoadingTaskScheduled.missionId(), emptyRollLoadingTaskScheduled.electrodeType(), 1);
    }

    public NoTrayBufferLocation handle(TrayUnloadingTaskScheduled trayUnloadingMissionScheduled) {
        return new NoTrayBufferLocation(bufferLocationInformation, version);
    }

}
