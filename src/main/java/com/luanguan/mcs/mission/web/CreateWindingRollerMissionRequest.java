package com.luanguan.mcs.mission.web;

import lombok.NonNull;
import lombok.Value;

@Value
public class CreateWindingRollerMissionRequest {

    @NonNull String windingMachineId;

    @NonNull String windingRollerName;

    @NonNull String batteryModelName;
}
