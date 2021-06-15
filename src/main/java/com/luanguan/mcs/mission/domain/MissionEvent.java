package com.luanguan.mcs.mission.domain;

import com.luanguan.mcs.buffer_location.domain.BufferLocationId;
import com.luanguan.mcs.empty_roll_location.domain.EmptyRollLocationId;
import com.luanguan.mcs.framework.domain.DomainEvent;
import com.luanguan.mcs.winding_machine.domain.ElectrodeType;
import lombok.NonNull;
import lombok.Value;

import java.time.Instant;
import java.util.UUID;

public interface MissionEvent extends DomainEvent {

    default MissionId missionId() {
        return new MissionId(getMissionId());
    }

    UUID getMissionId();

    default UUID getAggregateId() {
        return getMissionId();
    }

    @Value
    class MissionCreated implements MissionEvent {

        @NonNull UUID eventId = UUID.randomUUID();

        @NonNull Instant when;

        @NonNull UUID missionId;

        public static MissionCreated now(MissionId missionId) {
            return new MissionCreated(Instant.now(), missionId.getId());
        }
    }

    @Value
    class MissionFailed implements MissionEvent {

        @NonNull
        UUID eventId = UUID.randomUUID();

        @NonNull
        Instant when;

        @NonNull
        UUID missionId;

        public static MissionFailed now(MissionId missionId) {
            return new MissionFailed(Instant.now(), missionId.getId());
        }

    }

    @Value
    class MissionPended implements MissionEvent {

        @NonNull
        UUID eventId = UUID.randomUUID();

        @NonNull
        Instant when;

        @NonNull
        UUID missionId;

        public static MissionPended now(MissionId missionId) {
            return new MissionPended(Instant.now(), missionId.getId());
        }

    }

    @Value
    class MissionCompleted implements MissionEvent {

        @NonNull
        UUID eventId = UUID.randomUUID();

        @NonNull
        Instant when;

        @NonNull
        UUID missionId;

        public static MissionCompleted now(MissionId missionId) {
            return new MissionCompleted(Instant.now(), missionId.getId());
        }

    }

    @Value
    class EmptyRollUnloadingTaskScheduled implements MissionEvent {

        @NonNull
        UUID eventId = UUID.randomUUID();

        @NonNull
        Instant when;

        @NonNull
        UUID missionId;

        @NonNull
        UUID targetEmptyRollLocationId;

        @NonNull
        Integer electrodeTypeValue;

        public static EmptyRollUnloadingTaskScheduled now(MissionId missionId,
                                                          EmptyRollLocationId targetEmptyRollLocationId, ElectrodeType electrodeType) {
            return new EmptyRollUnloadingTaskScheduled(Instant.now(), missionId.getId(),
                    targetEmptyRollLocationId.getId(), electrodeType.getValue());
        }

        public EmptyRollLocationId targeEmptyRollLocationId() {
            return new EmptyRollLocationId(getTargetEmptyRollLocationId());
        }

        public ElectrodeType electrodeType() {
            return ElectrodeType.getByValue(electrodeTypeValue);
        }

    }

    @Value
    class EmptyRollLoadingTaskScheduled implements MissionEvent {

        @NonNull
        UUID eventId = UUID.randomUUID();

        @NonNull
        Instant when;

        @NonNull
        UUID missionId;

        @NonNull
        UUID sourceEmptyRollLocationId;

        @NonNull
        UUID targetBufferLocationId;

        @NonNull
        Integer electrodeTypeValue;

        public static EmptyRollLoadingTaskScheduled now(MissionId missionId,
                                                        EmptyRollLocationId sourceEmptyRollLocationId, BufferLocationId targetBufferLocationId,
                                                        ElectrodeType electrodeType) {
            return new EmptyRollLoadingTaskScheduled(Instant.now(), missionId.getId(),
                    sourceEmptyRollLocationId.getId(), targetBufferLocationId.getId(), electrodeType.getValue());
        }

        public EmptyRollLocationId sourceEmptyRollLocationId() {
            return new EmptyRollLocationId(getSourceEmptyRollLocationId());
        }

        public BufferLocationId targetBufferLocationId() {
            return new BufferLocationId(getTargetBufferLocationId());
        }

        public ElectrodeType electrodeType() {
            return ElectrodeType.getByValue(electrodeTypeValue);
        }

    }

    @Value
    class FullRollLoadingTaskScheduled implements MissionEvent {

        @NonNull
        UUID eventId = UUID.randomUUID();

        @NonNull
        Instant when;

        @NonNull
        UUID missionId;

        @NonNull
        UUID sourceBufferLocationId;

        public static FullRollLoadingTaskScheduled now(MissionId missionId,
                                                       BufferLocationId sourceBufferLocationId) {
            return new FullRollLoadingTaskScheduled(Instant.now(), missionId.getId(),
                    sourceBufferLocationId.getId());
        }

        public BufferLocationId sourceBufferLocationId() {
            return new BufferLocationId(getSourceBufferLocationId());
        }

    }

    @Value
    class TrayUnloadingTaskScheduled implements MissionEvent {

        @NonNull
        UUID eventId = UUID.randomUUID();

        @NonNull
        Instant when;

        @NonNull
        UUID missionId;

        @NonNull
        UUID sourceBufferLocationId;

        @NonNull
        Integer electrodeTypeValue;

        public static TrayUnloadingTaskScheduled now(MissionId missionId, BufferLocationId sourceBufferLocationId,
                                                     ElectrodeType electrodeType) {
            return new TrayUnloadingTaskScheduled(Instant.now(), missionId.getId(), sourceBufferLocationId.getId(),
                    electrodeType.getValue());
        }

        public BufferLocationId sourceBufferLocationId() {
            return new BufferLocationId(getSourceBufferLocationId());
        }

        public ElectrodeType electrodeType() {
            return ElectrodeType.getByValue(electrodeTypeValue);
        }

    }

    @Value
    class TrayLoadingTaskScheduled implements MissionEvent {

        @NonNull
        UUID eventId = UUID.randomUUID();

        @NonNull
        Instant when;

        @NonNull
        UUID missionId;

        @NonNull
        UUID targetBufferLocationId;

        @NonNull
        Integer fullRollNum;

        public static TrayLoadingTaskScheduled now(MissionId missionId, BufferLocationId targetBufferLocationId,
                                                   Integer fullRollNum) {
            return new TrayLoadingTaskScheduled(Instant.now(), missionId.getId(), targetBufferLocationId.getId(),
                    fullRollNum);
        }

        public BufferLocationId targetBufferLocationId() {
            return new BufferLocationId(getTargetBufferLocationId());
        }

    }

}
