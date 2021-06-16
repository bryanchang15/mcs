package com.luanguan.mcs.mission.domain;

import lombok.NonNull;
import lombok.Value;

public enum MissionPendingReason {

    PreMissionNotCompleted,
    NoAvailableBufferLocation,
    NoAvailableEmptyRollLocation,
    NoAvailableRobot;

}
