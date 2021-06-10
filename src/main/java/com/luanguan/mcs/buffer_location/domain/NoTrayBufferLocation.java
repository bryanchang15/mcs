package com.luanguan.mcs.buffer_location.domain;

import com.luanguan.mcs.framework.domain.Version;
import com.luanguan.mcs.mission.domain.MissionEvent;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Value;

@Value
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@EqualsAndHashCode(callSuper = false, of = "bufferLocationInformation")
public class NoTrayBufferLocation extends BufferLocation {

    @NonNull
    BufferLocationInformation bufferLocationInformation;

    @NonNull
    Version version;

    public TrayLoadingBufferLocation handle(MissionEvent.TrayLoadingTaskScheduled trayLoadingTaskScheduled) {
        return new TrayLoadingBufferLocation(bufferLocationInformation, version, trayLoadingTaskScheduled.missionId(),
                trayLoadingTaskScheduled.getFullRollNum());
    }

}
