package com.luanguan.mcs.empty_roll_location.infrastructure;

import com.luanguan.mcs.empty_roll_location.application.MissionEventHandler;
import com.luanguan.mcs.mission.domain.MissionEvent.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class MissionEventHandlerSpringEvents {

    private final MissionEventHandler missionEventHandler;

    @EventListener
    public void handle(MissionCompleted missionCompleted) {
        missionEventHandler.handle(missionCompleted);
    }

    @EventListener
    public void handle(MissionFailed missionFailed) {
        missionEventHandler.handle(missionFailed);
    }

    @EventListener
    public void handle(BufferLocationRollLoadingMissionScheduled missionScheduled) {
        missionEventHandler.handle(missionScheduled);
    }

    @EventListener
    public void handle(WindingRollerUnloadingMissionScheduled missionScheduled) {
        missionEventHandler.handle(missionScheduled);
    }

}
