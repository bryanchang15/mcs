package com.luanguan.mcs.external.robot.application;

import com.luanguan.mcs.shared_kernel.*;
import lombok.NonNull;
import lombok.Value;

@Value
public class ScheduleRobotWindingRollerLoadingCommand {

    @NonNull Position sourceBufferLocationPosition;

    @NonNull TrayPosition sourceTrayPosition;

    @NonNull Position targetWindingMachinePosition;

    @NonNull WindingRollerName targetWindingRollerName;

    @NonNull BatteryModel targetBatteryModel;

}
