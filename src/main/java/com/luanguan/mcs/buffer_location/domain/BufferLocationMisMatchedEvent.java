package com.luanguan.mcs.buffer_location.domain;

import com.luanguan.mcs.framework.domain.DomainEvent;
import com.luanguan.mcs.mission.domain.MissionId;
import lombok.NonNull;
import lombok.Value;

import java.time.Instant;
import java.util.UUID;

@Value
public class BufferLocationMisMatchedEvent implements DomainEvent {

    @NonNull UUID eventId = UUID.randomUUID();

    @NonNull Instant when;

    @NonNull UUID bufferLocationId;

    @NonNull UUID missionId;

    public BufferLocationId bufferLocationId() {
        return new BufferLocationId(getBufferLocationId());
    }

    public MissionId missionId() {
        return new MissionId(getMissionId());
    }

    @Override
    public UUID getAggregateId() {
        return getBufferLocationId();
    }

   public static BufferLocationMisMatchedEvent now(BufferLocationId bufferLocationId, MissionId missionId) {
        return new BufferLocationMisMatchedEvent(Instant.now(), bufferLocationId.getId(), missionId.getId());
   }

}
