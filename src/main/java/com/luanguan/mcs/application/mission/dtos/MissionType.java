package com.luanguan.mcs.application.mission.dtos;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum MissionType {

    EMPTY_ROLL_UNLOADING(1),
    FULL_ROLL_LOADING(2),
    ROLL_RELOADING(3),

    TRAY_UNLOADING(4),
    TRAY_LOADING(8),
    TRAY_RELOADING(12);

    private final Integer type;

}
