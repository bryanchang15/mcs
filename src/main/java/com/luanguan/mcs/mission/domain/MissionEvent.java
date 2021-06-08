package com.luanguan.mcs.mission.domain;

import java.time.Instant;
import java.util.UUID;

import com.luanguan.mcs.framework.domain.DomainEvent;

import lombok.NonNull;
import lombok.Value;

public interface MissionEvent extends DomainEvent {

    default MissionId missionId() {
        return new MissionId(getMissionId());
    }

    Long getMissionId();

    default Long getAggregateId() {
        return getMissionId();
    }

    @Value
    class MissionExpired implements MissionEvent {
        @NonNull
        UUID eventId = UUID.randomUUID();

        @NonNull
        Instant when;

        @NonNull
        Long missionId;

        @NonNull
        MissionType missionType;

        public static MissionExpired now(MissionId missionId, MissionType missionType) {
            return new MissionExpired(Instant.now(), missionId.getId(), missionType);
        }

    }

}
