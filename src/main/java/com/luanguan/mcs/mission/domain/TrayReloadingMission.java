package com.luanguan.mcs.mission.domain;

import com.luanguan.mcs.buffer_location.domain.BufferLocationInformation;
import com.luanguan.mcs.empty_roll_location.domain.EmptyRollLocationInformation;
import com.luanguan.mcs.framework.domain.Version;
import io.vavr.collection.List;
import lombok.*;

@RequiredArgsConstructor
@EqualsAndHashCode(of = "missionInformation")
@Getter
public class TrayReloadingMission implements Mission {

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
    BufferLocationInformation sourceBufferLocationInformation;

    @NonNull
    List<EmptyRollLocationInformation> emptyRollLocationInformationList = List.empty();

    @NonNull
    List<RobotTaskId> scheduledEmptyRollLoadingRobotTasks = List.empty();

    @Setter
    RobotTaskId scheduledTrayUnloadingRobotTask;

    @Setter
    RobotTaskId scheduledTrayLoadingRobotTask;

    public void addEmptyRollFrom(EmptyRollLocationInformation emptyRollLocationInformation) {
       emptyRollLocationInformationList.append(emptyRollLocationInformation);
    }

    public void addScheduledEmptyRollLoadingTask(RobotTaskId robotTaskId) {
        scheduledEmptyRollLoadingRobotTasks.append(robotTaskId);
    }

}
