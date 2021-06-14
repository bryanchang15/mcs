package com.luanguan.mcs.mission.domain;

import com.luanguan.mcs.empty_roll_location.domain.EmptyRollLocationInformation;
import com.luanguan.mcs.framework.domain.Version;
import com.luanguan.mcs.winding_machine.domain.WindingRoller;
import lombok.*;

@RequiredArgsConstructor
@EqualsAndHashCode(of = "missionInformation")
@Getter
public class EmptyRollUnloadingMission implements Mission {

    @NonNull
    MissionInformation missionInformation;

    @NonNull
    Version version;

    @NonNull
    @Setter
    MissionState missionState;

    @Setter
    MissionPendingReason missionPendingReason;

    @NonNull
    WindingRoller sourceWindingRoller;

    @Setter
    EmptyRollLocationInformation targetEmptyRollLocationInformation;

    @Setter
    RobotTaskId scheduledRobotTask;

}
