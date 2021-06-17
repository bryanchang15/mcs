package com.luanguan.mcs.external.robot.application;


import io.vavr.control.Try;

public interface SchedulingRobot {

    Try<ScheduleRobotResult> schedule(ScheduleRobotWindingRollerUnloadingCommand command);

    Try<ScheduleRobotResult> schedule(ScheduleRobotWindingRollerLoadingCommand command);

}
