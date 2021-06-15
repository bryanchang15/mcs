package com.luanguan.mcs.mission.domain;

import com.luanguan.mcs.buffer_location.domain.BufferLocationInformation;
import com.luanguan.mcs.empty_roll_location.domain.EmptyRollLocationInformation;
import com.luanguan.mcs.framework.domain.Version;
import com.luanguan.mcs.shared_kernel.TrayPosition;
import com.luanguan.mcs.winding_machine.domain.WindingRoller;
import lombok.*;

@Getter
public class RollReloadingMission extends Mission {

    @NonNull private final WindingRoller windingRoller;

    private EmptyRollLocationInformation emptyRollLocationInformation = null;

    private BufferLocationInformation bufferLocationInformation = null;

    private TrayPosition trayPosition = null;

    private RobotTaskId scheduledUnloadingRobotTask = null;

    private RobotTaskId scheduledLoadingRobotTask = null;

    public RollReloadingMission(
            @NonNull MissionInformation missionInformation,
            @NonNull Version version,
            @NonNull MissionState missionState,
            @NonNull MissionPendingReason missionPendingReason,
            @NonNull WindingRoller windingRoller
    ) {
        super(missionInformation, version, missionState, missionPendingReason);
        this.windingRoller = windingRoller;
    }

    public void assignOf(
            EmptyRollLocationInformation emptyRollLocationInformation,
            BufferLocationInformation bufferLocationInformation,
            TrayPosition trayPosition
    ) {
        super.ready();
        this.emptyRollLocationInformation = emptyRollLocationInformation;
        this.bufferLocationInformation = bufferLocationInformation;
        this.trayPosition = trayPosition;
    }

    public void executeBy(RobotTaskId emptyRollUnloadingTask, RobotTaskId fullRollLoadingTask) {
        super.execute();
        this.scheduledLoadingRobotTask = fullRollLoadingTask;
        this.scheduledUnloadingRobotTask = emptyRollUnloadingTask;
    }

}
