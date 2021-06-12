package com.luanguan.mcs.buffer_location.domain;

import com.luanguan.mcs.framework.domain.DomainEvent;
import com.luanguan.mcs.shared_kernel.BatteryModel;
import com.luanguan.mcs.winding_machine.domain.ElectrodeType;
import lombok.NonNull;
import lombok.Value;

import java.time.Instant;
import java.util.UUID;

public interface BufferLocationEvent extends DomainEvent {

    default BufferLocationId bufferLocationId() {
        return new BufferLocationId(getBufferLocationId());
    }

    UUID getBufferLocationId();

    default UUID getAggregateId() {
        return getBufferLocationId();
    }

    @Value
    class BufferLocationEmptied implements BufferLocationEvent {

        @NonNull
        UUID eventId = UUID.randomUUID();

        @NonNull
        Instant when;

        @NonNull
        UUID bufferLocationId;

        public static BufferLocationEmptied now(BufferLocationId bufferLocationId) {
            return new BufferLocationEmptied(Instant.now(), bufferLocationId.getId());
        }

    }

    @Value
    class BufferLocationLoaded implements BufferLocationEvent {

        @NonNull
        UUID eventId = UUID.randomUUID();

        @NonNull
        Instant when;

        @NonNull
        UUID bufferLocationId;

        @NonNull
        String batteryModelName;

        @NonNull
        Integer electrodeTypeValue;

        public static BufferLocationLoaded now(BufferLocationId bufferLocationId, BatteryModel batteryModel,
                                               ElectrodeType electrodeType) {
            return new BufferLocationLoaded(Instant.now(), bufferLocationId.getId(), batteryModel.getModelName(),
                    electrodeType.getValue());
        }

        public BatteryModel batteryModel() {
            return new BatteryModel(getBatteryModelName());
        }

        public ElectrodeType electrodeType() {
            return ElectrodeType.getByValue(getElectrodeTypeValue());
        }

    }

}
