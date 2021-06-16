package com.luanguan.mcs.shared_kernel;

import com.luanguan.mcs.shared_kernel.*;
import lombok.NonNull;
import lombok.Value;

@Value
public class ScheduleRobotWindingRollerUnloadingRequest {

    @NonNull Position sourceWindingMachinePosition;

    @NonNull WindingRollerName sourceWindingRollerName;

    @NonNull BatteryModel batteryModel;

    @NonNull Position emptyRollRackPosition;

    @NonNull Position emptyRollLocationPosition;

}
