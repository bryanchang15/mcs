package com.luanguan.mcs.mission.application;

import com.luanguan.mcs.mission.domain.Mission;
import com.luanguan.mcs.mission.domain.MissionId;
import io.vavr.control.Try;

public class CreateWindingRollerReloadingMission {

    public Try<Mission> createBy() {
        return Try.failure(new IllegalArgumentException());
    }

}
