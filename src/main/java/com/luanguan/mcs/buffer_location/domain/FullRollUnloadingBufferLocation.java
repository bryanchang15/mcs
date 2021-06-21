package com.luanguan.mcs.buffer_location.domain;

import com.luanguan.mcs.framework.domain.Version;
import com.luanguan.mcs.mission.domain.MissionEvent.*;
import com.luanguan.mcs.mission.domain.MissionId;
import lombok.*;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;

@Value
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false, of = "bufferLocationInformation")
public class FullRollUnloadingBufferLocation extends BufferLocation {

    @NonNull BufferLocationInformation bufferLocationInformation;

    @NonNull Version version;

    @NonNull MissionId byMission;

    @NonNull Integer fullRollNum;

    @Override
    public BufferLocation handle(MissionCompleted missionCompleted) {
        return Match(fullRollNum).of(
                Case($(1), () -> new EmptyBufferLocation(
                        bufferLocationInformation,
                        version
                )),
                Case($(), () -> new FullRollLoadedBufferLocation(
                        bufferLocationInformation,
                        version,
                        fullRollNum - 1
                ))
        );
    }

    @Override
    public BufferLocation handle(MissionFailed missionFailed) {
        return new FullRollLoadedBufferLocation(bufferLocationInformation, version, fullRollNum);
    }

}
