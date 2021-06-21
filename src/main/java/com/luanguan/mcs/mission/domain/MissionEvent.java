package com.luanguan.mcs.mission.domain;

import com.luanguan.mcs.buffer_location.domain.BufferLocationId;
import com.luanguan.mcs.empty_roll_location.domain.EmptyRollLocationId;
import com.luanguan.mcs.framework.domain.DomainEvent;
import com.luanguan.mcs.shared_kernel.BatteryModel;
import com.luanguan.mcs.winding_machine.domain.ElectrodeType;
import lombok.NonNull;
import lombok.Value;

import java.time.Instant;
import java.util.UUID;

public interface MissionEvent extends DomainEvent {

    default MissionId missionId() {
        return new MissionId(getMissionId());
    }

    Long getMissionId();

    default Long getAggregateId() {
        return getMissionId();
    }

    @Value
    class MissionCreated implements MissionEvent {

        @NonNull UUID eventId = UUID.randomUUID();

        @NonNull Instant when;

        @NonNull Long missionId;

        public static MissionCreated now(MissionId missionId) {
            return new MissionCreated(Instant.now(), missionId.getId());
        }
    }

    @Value
    class MissionFailed implements MissionEvent {

        @NonNull UUID eventId = UUID.randomUUID();

        @NonNull Instant when;

        @NonNull Long missionId;

        public static MissionFailed now(MissionId missionId) {
            return new MissionFailed(Instant.now(), missionId.getId());
        }

    }

    @Value
    class MissionPended implements MissionEvent {

        @NonNull UUID eventId = UUID.randomUUID();

        @NonNull Instant when;

        @NonNull Long missionId;

        public static MissionPended now(MissionId missionId) {
            return new MissionPended(Instant.now(), missionId.getId());
        }

    }

    @Value
    class MissionCompleted implements MissionEvent {

        @NonNull UUID eventId = UUID.randomUUID();

        @NonNull Instant when;

        @NonNull Long missionId;

        public static MissionCompleted now(MissionId missionId) {
            return new MissionCompleted(Instant.now(), missionId.getId());
        }

    }

    @Value
    class WindingRollerUnloadingMissionScheduled implements MissionEvent {

        @NonNull UUID eventId = UUID.randomUUID();

        @NonNull Instant when;

        @NonNull Long missionId;

        @NonNull Long targetEmptyRollLocationId;

        @NonNull String batteryModelName;

        @NonNull Integer electrodeTypeValue;

        public static WindingRollerUnloadingMissionScheduled now(
                MissionId missionId,
                EmptyRollLocationId targetEmptyRollLocationId,
                BatteryModel batteryModel,
                ElectrodeType electrodeType
        ) {
            return new WindingRollerUnloadingMissionScheduled(
                    Instant.now(),
                    missionId.getId(),
                    targetEmptyRollLocationId.getId(),
                    batteryModel.getModelName(),
                    electrodeType.getValue()
            );
        }

        public EmptyRollLocationId targetEmptyRollLocationId() {
            return new EmptyRollLocationId(targetEmptyRollLocationId);
        }

        public BatteryModel batteryModel() {
            return new BatteryModel(batteryModelName);
        }

        public ElectrodeType electrodeType() {
            return ElectrodeType.getByValue(electrodeTypeValue);
        }

    }

    @Value
    class BufferLocationRollLoadingMissionScheduled implements MissionEvent {

        @NonNull UUID eventId = UUID.randomUUID();

        @NonNull Instant when;

        @NonNull Long missionId;

        @NonNull Long sourceEmptyRollLocationId;

        @NonNull Long targetBufferLocationId;

        @NonNull Integer electrodeTypeValue;

        public static BufferLocationRollLoadingMissionScheduled now(
                MissionId missionId,
                EmptyRollLocationId sourceEmptyRollLocationId, BufferLocationId targetBufferLocationId,
                ElectrodeType electrodeType
        ) {
            return new BufferLocationRollLoadingMissionScheduled(
                    Instant.now(),
                    missionId.getId(),
                    sourceEmptyRollLocationId.getId(),
                    targetBufferLocationId.getId(),
                    electrodeType.getValue()
            );
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
    class WindingRollerLoadingMissionScheduled implements MissionEvent {

        @NonNull UUID eventId = UUID.randomUUID();

        @NonNull Instant when;

        @NonNull Long missionId;

        @NonNull Long sourceBufferLocationId;

        public static WindingRollerLoadingMissionScheduled now(
                MissionId missionId,
                BufferLocationId sourceBufferLocationId
        ) {
            return new WindingRollerLoadingMissionScheduled(
                    Instant.now(),
                    missionId.getId(),
                    sourceBufferLocationId.getId()
            );
        }

        public BufferLocationId sourceBufferLocationId() {
            return new BufferLocationId(getSourceBufferLocationId());
        }

    }

    @Value
    class BufferLocationTrayUnloadingMissionScheduled implements MissionEvent {

        @NonNull UUID eventId = UUID.randomUUID();

        @NonNull Instant when;

        @NonNull Long missionId;

        @NonNull Long sourceBufferLocationId;

        @NonNull Integer electrodeTypeValue;

        public static BufferLocationTrayUnloadingMissionScheduled now(
                MissionId missionId,
                BufferLocationId sourceBufferLocationId,
                ElectrodeType electrodeType
        ) {
            return new BufferLocationTrayUnloadingMissionScheduled(
                    Instant.now(),
                    missionId.getId(),
                    sourceBufferLocationId.getId(),
                    electrodeType.getValue()
            );
        }

        public BufferLocationId sourceBufferLocationId() {
            return new BufferLocationId(getSourceBufferLocationId());
        }

        public ElectrodeType electrodeType() {
            return ElectrodeType.getByValue(electrodeTypeValue);
        }

    }

    @Value
    class BufferLocationTrayLoadingMissionScheduled implements MissionEvent {

        @NonNull UUID eventId = UUID.randomUUID();

        @NonNull Instant when;

        @NonNull Long missionId;

        @NonNull Long targetBufferLocationId;

        @NonNull Integer fullRollNum;

        public static BufferLocationTrayLoadingMissionScheduled now(
                MissionId missionId,
                BufferLocationId targetBufferLocationId,
                Integer fullRollNum
        ) {
            return new BufferLocationTrayLoadingMissionScheduled(
                    Instant.now(),
                    missionId.getId(),
                    targetBufferLocationId.getId(),
                    fullRollNum
            );
        }

        public BufferLocationId targetBufferLocationId() {
            return new BufferLocationId(getTargetBufferLocationId());
        }

    }

}
