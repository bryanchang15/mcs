package com.luanguan.mcs.buffer_location.domain;

import com.luanguan.mcs.framework.domain.DomainEvent;
import com.luanguan.mcs.framework.domain.Version;
import com.luanguan.mcs.mission.domain.MissionEvent;
import com.luanguan.mcs.shared_kernel.BatteryModel;
import com.luanguan.mcs.shared_kernel.Position;
import com.luanguan.mcs.winding_machine.domain.ElectrodeType;
import io.vavr.control.Either;

public abstract class BufferLocation {

    public BufferLocationId bufferLocationId() {
        return getBufferLocationInformation().getBufferLocationId();
    }

    public Position bufferLocationPosition() {
        return getBufferLocationInformation().getBufferLocationPosition();
    }

    public BatteryModel bufferBatteryModel() {
        return getBufferLocationInformation().getBufferBatteryModel();
    }

    public ElectrodeType fullRollElectrodeType() {
        return getBufferLocationInformation().getFullRollElectrodeType();
    }

    public Integer trayCapacity() {
        return getBufferLocationInformation().getTrayCapacity();
    }

    public abstract BufferLocationInformation getBufferLocationInformation();

    public abstract Version getVersion();

    public BufferLocation handle(MissionEvent.MissionCompleted missionCompleted) {
        return this;
    }

    public BufferLocation handle(MissionEvent.MissionFailed missionFailed) {
        return this;
    }

    public Either<DomainEvent, BufferLocation> handle(
            MissionEvent.FullRollLoadingTaskScheduled fullRollLoadingTaskScheduled
    ) {
        return Either.right(this);
    }

    public Either<DomainEvent, BufferLocation> handle(
            MissionEvent.EmptyRollLoadingTaskScheduled emptyRollLoadingTaskScheduled
    ) {
        return Either.right(this);
    }

    public Either<DomainEvent, BufferLocation> handle(
            MissionEvent.TrayUnloadingTaskScheduled trayUnloadingTaskScheduled
    ) {
        return Either.right(this);
    }

    public Either<DomainEvent, BufferLocation> handle(
            MissionEvent.TrayLoadingTaskScheduled trayLoadingTaskScheduled
    ) {
        return Either.right(this);
    }

}
