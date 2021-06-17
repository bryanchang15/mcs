package com.luanguan.mcs.mission.application;

import com.luanguan.mcs.shared_kernel.*;
import lombok.NonNull;
import lombok.Value;

@Value
public class CreateWindingRollerMissionCommand {

    @NonNull Position windingMachinePosition;

    @NonNull BatteryModel batteryModel;

    @NonNull WindingRollerName rollerName;

    public static CreateWindingRollerMissionCommand create(
            @NonNull String windingMachineId,
            @NonNull String batteryModelName,
            @NonNull String windingRollerName
    ) {
        return new CreateWindingRollerMissionCommand(
                new Position(windingMachineId),
                new BatteryModel(batteryModelName),
                WindingRollerName.getByName(windingRollerName)
        );
    }

}
