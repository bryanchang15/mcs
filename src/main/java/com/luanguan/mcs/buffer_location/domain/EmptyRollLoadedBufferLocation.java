package com.luanguan.mcs.buffer_location.domain;

import com.luanguan.mcs.framework.domain.DomainEvent;
import com.luanguan.mcs.framework.domain.Version;
import com.luanguan.mcs.mission.domain.MissionEvent.*;
import com.luanguan.mcs.shared_kernel.TrayPosition;
import com.luanguan.mcs.winding_machine.domain.ElectrodeType;
import io.vavr.control.Either;
import lombok.*;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;

@Value
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false, of = "bufferLocationInformation")
public class EmptyRollLoadedBufferLocation extends BufferLocation {

    @NonNull BufferLocationInformation bufferLocationInformation;

    @NonNull Version version;

    @NonNull ElectrodeType emptyRollElectrodeType;

    @NonNull Integer emptyRollNum;

    public TrayPosition getTargetTrayPosition() {
        return TrayPosition.Outer;
    }

    @Override
    public Either<DomainEvent, BufferLocation> handle(
            BufferLocationRollLoadingMissionScheduled missionScheduled
    ) {
        return Match(canLoad()).of(
                Case($(false), () -> Either.left(BufferLocationMisMatchedEvent.now(
                        getBufferLocationId(),
                        missionScheduled.missionId()
                ))),
                Case($(true), () -> Either.right(new EmptyRollLoadingBufferLocation(
                        bufferLocationInformation,
                        version,
                        missionScheduled.missionId(),
                        emptyRollElectrodeType,
                        emptyRollNum
                )))
        );
    }

    @Override
    public Either<DomainEvent, BufferLocation> handle(
            BufferLocationTrayUnloadingMissionScheduled missionScheduled
    ) {
        return Either.right(new NoTrayBufferLocation(
                bufferLocationInformation,
                version
        ));
    }

    private Boolean canLoad() {
        return (this.emptyRollNum < this.getTrayCapacity());
    }

}
