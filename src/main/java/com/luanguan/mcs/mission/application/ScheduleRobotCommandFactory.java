package com.luanguan.mcs.mission.application;

import com.luanguan.mcs.buffer_location.domain.FullRollLoadedBufferLocation;
import com.luanguan.mcs.empty_roll_location.domain.UnloadedEmptyRollLocation;
import com.luanguan.mcs.mission.domain.WindingRollerLoadingMission;
import com.luanguan.mcs.mission.domain.WindingRollerUnloadingMission;
import com.luanguan.mcs.external.robot.application.ScheduleRobotWindingRollerLoadingCommand;
import com.luanguan.mcs.external.robot.application.ScheduleRobotWindingRollerUnloadingCommand;

public class ScheduleRobotCommandFactory {

    public static ScheduleRobotWindingRollerUnloadingCommand create(
            WindingRollerUnloadingMission mission,
            UnloadedEmptyRollLocation targetEmptyRollLocation
    ) {
        return new ScheduleRobotWindingRollerUnloadingCommand(
                mission.getSourceWindingRoller()
                        .getWindingMachinePosition(),
                mission.getSourceWindingRoller()
                        .getElectrodeType().getRollerName(),
                mission.getSourceWindingRoller().getBatteryModel(),
                targetEmptyRollLocation.getEmptyRollRackPosition(),
                targetEmptyRollLocation.getEmptyRollLocationPosition()
        );
    }

    public static ScheduleRobotWindingRollerLoadingCommand create(
            WindingRollerLoadingMission mission,
            FullRollLoadedBufferLocation sourceBufferLocation
    ) {
        return new ScheduleRobotWindingRollerLoadingCommand(
                sourceBufferLocation.getBufferLocationPosition(),
                sourceBufferLocation.getTargetTrayPosition(),
                mission.getTargetWindingRoller().getWindingMachinePosition(),
                mission.getTargetWindingRoller().getElectrodeType().getRollerName(),
                mission.getTargetWindingRoller().getBatteryModel()
        );
    }

}
