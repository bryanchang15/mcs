package com.luanguan.mcs.mission.application;

import lombok.NonNull;
import lombok.Value;

import java.util.UUID;

@Value
public class CreateMissionResult {

    @NonNull UUID missionId;

}
