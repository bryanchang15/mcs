package com.luanguan.mcs.mission.web;

import lombok.NonNull;
import lombok.Value;

@Value
public class CreateMissionResult {

    @NonNull Integer resultCode;

    String missionId;

    String failReason;

}
