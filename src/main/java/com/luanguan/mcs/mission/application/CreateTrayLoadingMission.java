package com.luanguan.mcs.mission.application;

import com.luanguan.mcs.mission.domain.Mission;
import io.vavr.control.Try;

public class CreateTrayLoadingMission {

    public Try<Mission> createBy() {
        return Try.failure(new IllegalArgumentException());
    }

}
