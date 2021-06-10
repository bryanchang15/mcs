package com.luanguan.mcs.mission.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum MissionType {

    EmptyRollUnloading(0),
    FullRollLoading(1),
    RollReloading(2),
    EmptyRollLoading(3);

    private final Integer value;



}
