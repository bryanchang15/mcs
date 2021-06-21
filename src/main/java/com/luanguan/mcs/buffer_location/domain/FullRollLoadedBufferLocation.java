package com.luanguan.mcs.buffer_location.domain;

import com.luanguan.mcs.framework.domain.DomainEvent;
import com.luanguan.mcs.framework.domain.Version;
import com.luanguan.mcs.mission.domain.MissionEvent.*;

import com.luanguan.mcs.shared_kernel.TrayPosition;
import io.vavr.control.Either;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Value;

import static io.vavr.API.*;

@Value
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false, of = "bufferLocationInformation")
public class FullRollLoadedBufferLocation extends BufferLocation {

    @NonNull BufferLocationInformation bufferLocationInformation;

    @NonNull Version version;

    @NonNull Integer fullRollNum;

    public TrayPosition getTargetTrayPosition() {
        return Match(fullRollNum).of(
                Case($(2), () -> TrayPosition.Outer),
                Case($(1), () -> TrayPosition.Inner)
        );
    }

    @Override
    public Either<DomainEvent, BufferLocation> handle(
            WindingRollerLoadingMissionScheduled missionScheduled
    ) {
        return Either.right(new FullRollUnloadingBufferLocation(
                bufferLocationInformation,
                version,
                missionScheduled.missionId(),
                fullRollNum
        ));
    }

}
