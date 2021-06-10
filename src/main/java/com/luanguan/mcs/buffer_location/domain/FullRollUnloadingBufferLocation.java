package com.luanguan.mcs.buffer_location.domain;

import com.luanguan.mcs.framework.domain.Version;
import com.luanguan.mcs.mission.domain.MissionEvent.MissionCompleted;
import com.luanguan.mcs.mission.domain.MissionEvent.MissionFailed;
import com.luanguan.mcs.mission.domain.MissionEvent.MissionPended;
import com.luanguan.mcs.mission.domain.MissionId;

import io.vavr.control.Either;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Value;

@Value
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@EqualsAndHashCode(callSuper = false, of = "bufferLocationInformation")
public class FullRollUnloadingBufferLocation extends BufferLocation {

    @NonNull
    BufferLocationInformation bufferLocationInformation;

    @NonNull
    Version version;

    @NonNull
    MissionId byRollMission;

    @NonNull
    Integer fullRollNum;

    public Either<EmptyBufferLocation, FullRollLoadedBufferLocation> handle(MissionCompleted missionCompleted) {
        if (1 == fullRollNum) {
            return Either.left(new EmptyBufferLocation(bufferLocationInformation, version));
        }

        return Either.right(new FullRollLoadedBufferLocation(bufferLocationInformation, version, fullRollNum - 1));
    }

    public FullRollLoadedBufferLocation handle(MissionFailed missionFailed) {
        return new FullRollLoadedBufferLocation(bufferLocationInformation, version, fullRollNum);
    }

    public FullRollLoadedBufferLocation handle(MissionPended missionPended) {
        return new FullRollLoadedBufferLocation(bufferLocationInformation, version, fullRollNum);
    }

}
