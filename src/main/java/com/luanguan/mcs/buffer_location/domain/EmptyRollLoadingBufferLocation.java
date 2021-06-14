package com.luanguan.mcs.buffer_location.domain;

import com.luanguan.mcs.framework.domain.Version;
import com.luanguan.mcs.mission.domain.MissionEvent.*;
import com.luanguan.mcs.mission.domain.MissionId;
import com.luanguan.mcs.winding_machine.domain.ElectrodeType;
import lombok.*;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;

@Value
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false, of = "bufferLocationInformation")
public class EmptyRollLoadingBufferLocation extends BufferLocation {

    @NonNull
    BufferLocationInformation bufferLocationInformation;

    @NonNull
    Version version;

    @NonNull
    MissionId byMission;

    @NonNull
    ElectrodeType emptyRollElectrodeType;

    @NonNull
    Integer emptyRollNum;

    @Override
    public BufferLocation handle(MissionCompleted missionCompleted) {
        return new EmptyRollLoadedBufferLocation(
                bufferLocationInformation,
                version,
                emptyRollElectrodeType,
                emptyRollNum + 1
        );
    }

    @Override
    public BufferLocation handle(MissionFailed missionFailed) {
        return Match(emptyRollNum).of(
                Case($(0), () -> new EmptyBufferLocation(bufferLocationInformation, version)),
                Case($(), () -> new EmptyRollLoadedBufferLocation(
                        bufferLocationInformation,
                        version,
                        emptyRollElectrodeType,
                        emptyRollNum
                ))
        );
    }

    @Override
    public BufferLocation handle(MissionPended missionPended) {
        return Match(emptyRollNum).of(
                Case($(0), () -> new EmptyBufferLocation(bufferLocationInformation, version)),
                Case($(), () -> new EmptyRollLoadedBufferLocation(
                        bufferLocationInformation,
                        version,
                        emptyRollElectrodeType,
                        emptyRollNum
                ))
        );
    }

}
