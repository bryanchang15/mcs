package com.luanguan.mcs.external.robot.application;


import lombok.NonNull;
import lombok.Value;

@Value
public class ScheduleRobotResult {

    @NonNull RobotTaskId robotTaskId;

}
