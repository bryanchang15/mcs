package com.luanguan.mcs.buffer_location.domain;

import com.luanguan.mcs.framework.domain.DomainEvent;
import com.luanguan.mcs.framework.domain.Version;
import com.luanguan.mcs.mission.domain.MissionEvent.*;
import io.vavr.control.Either;
import lombok.*;

@Value
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false, of = "bufferLocationInformation")
public class NoTrayBufferLocation extends BufferLocation {

    @NonNull BufferLocationInformation bufferLocationInformation;

    @NonNull Version version;

    @Override
    public Either<DomainEvent, BufferLocation> handle(
            BufferLocationTrayLoadingMissionScheduled missionScheduled
    ) {
        return Either.right(new TrayLoadingBufferLocation(
                bufferLocationInformation,
                version,
                missionScheduled.missionId(),
                missionScheduled.getFullRollNum()
        ));
    }

}
