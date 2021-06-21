package com.luanguan.mcs.mission.domain;

import com.luanguan.mcs.framework.domain.Version;
import com.luanguan.mcs.mission.domain.MissionEvent.WindingRollerLoadingMissionScheduled;
import com.luanguan.mcs.winding_machine.domain.WindingRoller;
import lombok.NonNull;
import org.springframework.lang.Nullable;

public class WindingRollerMissionFactory {

    public static WindingRollerLoadingMission loading(
            @Nullable MissionId preMissionId,
            @NonNull WindingRoller targetWindingRoller
            ) {
        return new WindingRollerLoadingMission(
                null,
                Version.zero(),
                MissionState.Created,
                null,
                null,
                preMissionId,
                targetWindingRoller,
                null
        );
    }

    public static WindingRollerUnloadingMission unloading(
            @NonNull WindingRoller sourceWindingRoller
    ) {
        return new WindingRollerUnloadingMission(
                null,
                Version.zero(),
                MissionState.Created,
                null,
                null,
                sourceWindingRoller,
                null
        );
    }


}
