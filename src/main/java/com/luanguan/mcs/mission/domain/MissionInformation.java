package com.luanguan.mcs.mission.domain;

import org.springframework.lang.Nullable;

import lombok.NonNull;
import lombok.Value;

@Value
public class MissionInformation {

    @NonNull
    MissionId missionId;

}
