package com.luanguan.mcs.mission.domain;

import com.luanguan.mcs.framework.domain.Version;
import io.vavr.control.Option;

public interface Mission {

    default MissionId missionId() {
        return getMissionInformation().getMissionId();
    }

    MissionInformation getMissionInformation();

    Version getVersion();

    MissionState getMissionState();

    Option<MissionPendingReason> getMissionPendingReason();

}
