package com.luanguan.mcs.buffer_location.domain;

import com.luanguan.mcs.framework.domain.Version;
import com.luanguan.mcs.mission.domain.MissionEvent.MissionCompleted;
import com.luanguan.mcs.mission.domain.MissionEvent.MissionFailed;
import com.luanguan.mcs.mission.domain.MissionEvent.MissionPended;
import com.luanguan.mcs.mission.domain.MissionId;
import com.luanguan.mcs.winding_machine.domain.ElectrodeType;

import io.vavr.control.Either;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Value;

@Value
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@EqualsAndHashCode(callSuper = false, of = "bufferLocationInformation")
public class EmptyRollLoadingBufferLocation extends BufferLocation {

    @NonNull
    BufferLocationInformation bufferLocationInformation;

    @NonNull
    Version version;

    @NonNull
    MissionId byRollMission;

    @NonNull
    ElectrodeType emptyRollElectrodeType;

    @NonNull
    Integer emptyRollNum;

    public EmptyRollLoadedBufferLocation handle(MissionCompleted missionCompleted) {
        return new EmptyRollLoadedBufferLocation(bufferLocationInformation, version, emptyRollElectrodeType,
                emptyRollNum + 1);
    }

    public Either<EmptyBufferLocation, EmptyRollLoadedBufferLocation> handle(MissionFailed missionFailed) {
        if (emptyRollNum == 0) {
            return Either.left(new EmptyBufferLocation(bufferLocationInformation, version));
        }

        return Either.right(new EmptyRollLoadedBufferLocation(bufferLocationInformation, version,
                emptyRollElectrodeType, emptyRollNum));
    }

    public Either<EmptyBufferLocation, EmptyRollLoadedBufferLocation> handle(MissionPended missionPended) {
        if (emptyRollNum == 0) {
            return Either.left(new EmptyBufferLocation(bufferLocationInformation, version));
        }

        return Either.right(new EmptyRollLoadedBufferLocation(bufferLocationInformation, version,
                emptyRollElectrodeType, emptyRollNum));
    }

}
