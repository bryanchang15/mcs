package com.luanguan.mcs.mission.domain;

import com.luanguan.mcs.buffer_location.domain.BufferLocationInformation;
import com.luanguan.mcs.framework.domain.Version;
import com.luanguan.mcs.mission.application.FullRollLoadingRobotTaskRequest;
import com.luanguan.mcs.shared_kernel.TrayPosition;
import com.luanguan.mcs.winding_machine.domain.WindingRoller;
import lombok.Getter;
import lombok.NonNull;

@Getter
public class FullRollLoadingMission extends Mission {

    @NonNull private final WindingRoller targetWindingRoller;

    BufferLocationInformation sourceBufferLocationInformation = null;

    TrayPosition sourceTrayPosition = null;

    RobotTaskId scheduledRobotTask = null;

    public FullRollLoadingMission(
            @NonNull MissionInformation missionInformation,
            @NonNull Version version,
            @NonNull MissionState missionState,
            @NonNull MissionPendingReason missionPendingReason,
            @NonNull WindingRoller targetWindingRoller
    ) {
        super(missionInformation, version, missionState, missionPendingReason);
        this.targetWindingRoller = targetWindingRoller;
    }

    public void assignOf(BufferLocationInformation bufferLocationInformation, TrayPosition trayPosition) {
        super.ready();
        this.sourceBufferLocationInformation = bufferLocationInformation;
        this.sourceTrayPosition = trayPosition;
    }

    public void executeBy(RobotTaskId scheduledRobotTask) {
        super.execute();
        this.scheduledRobotTask = scheduledRobotTask;
    }

    public FullRollLoadingRobotTaskRequest getFullRollLoadingRobotTaskRequest() {
        return new FullRollLoadingRobotTaskRequest(
                sourceBufferLocationInformation.getBufferLocationPosition(),
                sourceTrayPosition,
                targetWindingRoller.getWindingMachinePosition(),
                targetWindingRoller.getElectrodeType().getRollerName(),
                targetWindingRoller.getBatteryModel()
        );
    }

}
