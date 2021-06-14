package com.luanguan.mcs.mission.domain;

import com.luanguan.mcs.buffer_location.domain.BufferLocationInformation;
import com.luanguan.mcs.framework.domain.Version;
import com.luanguan.mcs.winding_machine.domain.WindingRoller;
import lombok.*;

@RequiredArgsConstructor
@EqualsAndHashCode(of = "missionInformation")
@Getter
public class FullRollLoadingMission implements Mission {

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
    WindingRoller targetWindingRoller;

    @Setter
    BufferLocationInformation sourceBufferLocationInformation;

    @Setter
    RobotTaskId scheduledRobotTask;

}
