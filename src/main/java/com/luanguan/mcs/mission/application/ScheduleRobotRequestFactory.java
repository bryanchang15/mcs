package com.luanguan.mcs.mission.application;

import com.luanguan.mcs.buffer_location.domain.FullRollLoadedBufferLocation;
import com.luanguan.mcs.empty_roll_location.domain.UnloadedEmptyRollLocation;
import com.luanguan.mcs.mission.domain.WindingRollerLoadingMission;
import com.luanguan.mcs.mission.domain.WindingRollerUnloadingMission;
import com.luanguan.mcs.shared_kernel.ScheduleRobotWindingRollerLoadingRequest;
import com.luanguan.mcs.shared_kernel.ScheduleRobotWindingRollerUnloadingRequest;

public class ScheduleRobotRequestFactory {

    public ScheduleRobotWindingRollerUnloadingRequest createBy(
            WindingRollerUnloadingMission mission,
            UnloadedEmptyRollLocation targetEmptyRollLocation
    ) {
        return new ScheduleRobotWindingRollerUnloadingRequest(
                mission.getSourceWindingRoller()
                        .getWindingMachinePosition(),
                mission.getSourceWindingRoller()
                        .getElectrodeType().getRollerName(),
                mission.getSourceWindingRoller().getBatteryModel(),
                targetEmptyRollLocation.emptyRollRackPosition(),
                targetEmptyRollLocation.emptyRollLocationPosition()
        );
    }

    public ScheduleRobotWindingRollerLoadingRequest createBy(
            WindingRollerLoadingMission mission,
            FullRollLoadedBufferLocation sourceBufferLocation
    ) {
        return new ScheduleRobotWindingRollerLoadingRequest(
                sourceBufferLocation.bufferLocationPosition(),
                sourceBufferLocation.getTrayPosition(),
                mission.getTargetWindingRoller().getWindingMachinePosition(),
                mission.getTargetWindingRoller().getElectrodeType().getRollerName(),
                mission.getTargetWindingRoller().getBatteryModel()
        );
    }

}
