package com.luanguan.mcs.mission.web;

import lombok.NonNull;
import lombok.Value;

@Value
public class CreateMissionResponse {

    @NonNull Integer resultCode;

    String missionId;

    String failReason;

}
