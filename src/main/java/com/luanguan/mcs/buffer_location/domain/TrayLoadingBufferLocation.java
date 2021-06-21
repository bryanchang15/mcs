package com.luanguan.mcs.buffer_location.domain;

import com.luanguan.mcs.framework.domain.Version;
import com.luanguan.mcs.mission.domain.MissionEvent.MissionCompleted;
import com.luanguan.mcs.mission.domain.MissionEvent.MissionFailed;
import com.luanguan.mcs.mission.domain.MissionEvent.MissionPended;
import com.luanguan.mcs.mission.domain.MissionId;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Value;

@Value
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false, of = "bufferLocationInformation")
public class TrayLoadingBufferLocation extends BufferLocation {

    @NonNull BufferLocationInformation bufferLocationInformation;

    @NonNull Version version;

    @NonNull MissionId byMission;

    @NonNull Integer fullRollNum;

    @Override
    public BufferLocation handle(MissionCompleted missionCompleted) {
        return new FullRollLoadedBufferLocation(bufferLocationInformation, version, fullRollNum);
    }

    @Override
    public BufferLocation handle(MissionFailed missionFailed) {
        return new NoTrayBufferLocation(bufferLocationInformation, version);
    }

}
