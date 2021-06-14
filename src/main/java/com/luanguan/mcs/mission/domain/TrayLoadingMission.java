package com.luanguan.mcs.mission.domain;

import com.luanguan.mcs.buffer_location.domain.BufferLocationInformation;
import com.luanguan.mcs.framework.domain.Version;
import lombok.*;

@RequiredArgsConstructor
@EqualsAndHashCode(of = "missionInformation")
@Getter
public class TrayLoadingMission implements Mission {

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
    BufferLocationInformation targetBufferLocationInformation;

    @Setter
    RobotTaskId scheduledRobotTask;

}
