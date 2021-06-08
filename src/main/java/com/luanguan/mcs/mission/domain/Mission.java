package com.luanguan.mcs.mission.domain;

import com.luanguan.mcs.framework.domain.Version;

public interface Mission {

    MissionInformation getMissionInformation();

    Version getVersion();

    default MissionId missionId() {
        return getMissionInformation().getMissionId();
    }

    default MissionType missionType() {
        return getMissionInformation().getMissionType();
    }

}
