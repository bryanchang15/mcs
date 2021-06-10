package com.luanguan.mcs.buffer_location.domain;

import com.luanguan.mcs.framework.domain.Version;
import com.luanguan.mcs.mission.domain.MissionEvent.EmptyRollLoadingTaskScheduled;
import com.luanguan.mcs.mission.domain.MissionEvent.TrayUnloadingTaskScheduled;
import com.luanguan.mcs.winding_machine.domain.ElectrodeType;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Value;

@Value
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@EqualsAndHashCode(callSuper = false, of = "bufferLocationInformation")
public class EmptyRollLoadedBufferLocation extends BufferLocation {

    @NonNull
    BufferLocationInformation bufferLocationInformation;

    @NonNull
    Version version;

    @NonNull
    ElectrodeType emptyRollElectrodeType;

    @NonNull
    Integer emptyRollNum;

    public EmptyRollLoadingBufferLocation handle(EmptyRollLoadingTaskScheduled emptyRollLoadingTaskScheduled) {
        return new EmptyRollLoadingBufferLocation(bufferLocationInformation, version,
                emptyRollLoadingTaskScheduled.missionId(), emptyRollElectrodeType, emptyRollNum);
    }

    public NoTrayBufferLocation handle(TrayUnloadingTaskScheduled trayUnloadingTaskScheduled) {
        return new NoTrayBufferLocation(bufferLocationInformation, version);
    }

}
