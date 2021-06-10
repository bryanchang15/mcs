package com.luanguan.mcs.mission.domain;

import java.util.UUID;

import lombok.NonNull;
import lombok.Value;

@Value
public class MissionId {

    @NonNull
    UUID id;

}
