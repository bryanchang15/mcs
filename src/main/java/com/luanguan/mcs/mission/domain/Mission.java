package com.luanguan.mcs.mission.domain;

import com.luanguan.mcs.external.robot.application.RobotTaskId;
import com.luanguan.mcs.framework.domain.Version;
import io.vavr.control.Option;
import lombok.*;

import static com.luanguan.mcs.mission.domain.MissionState.*;
import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;

public interface Mission {

    MissionId getMissionId();

    Version getVersion();

    MissionState getMissionState();

    Option<MissionPendingReason> getMissionPendingReason();

    Option<RobotTaskId> getScheduledRobotTaskId();

    Option<MissionId> getPreMissionId();

    Mission assignOf(Object resource);

    Mission pendBy(MissionPendingReason reason);

    Mission executeBy(RobotTaskId robotTaskId);

    Mission complete();

    Mission fail();

}
