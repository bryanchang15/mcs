package com.luanguan.mcs.mission.domain;

import com.luanguan.mcs.framework.domain.Version;
import com.luanguan.mcs.winding_machine.domain.WindingRoller;

import org.springframework.lang.Nullable;

import lombok.NonNull;

public class RollUnloadingMission implements Mission {

    @NonNull
    MissionInformation missionInformation;

    @NonNull
    Version version;

    @NonNull
    WindingRoller sourceWindingRoller;

    @Nullable
    StagingRackPosition targetStagingRackPosition;

    @Nullable
    RobotTaskId executingRobotTaskId;

}
