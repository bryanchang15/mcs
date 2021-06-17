package com.luanguan.mcs.buffer_location.infrastructure;

import com.luanguan.mcs.buffer_location.application.MissionEventHandler;
import com.luanguan.mcs.mission.domain.MissionEvent.*;
import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;

@AllArgsConstructor
public class MissionEventHandlerSpringEvents {

    private final MissionEventHandler missionEventHandler;

    @EventListener
    public void handle(MissionCompleted missionCompleted) {
        missionEventHandler.handle(missionCompleted);
    }

    @EventListener
    public void handle(MissionPended missionPended) {
        missionEventHandler.handle(missionPended);
    }

    @EventListener
    public void handle(MissionFailed missionFailed) {
        missionEventHandler.handle(missionFailed);
    }

    @EventListener
    public void handle(WindingRollerLoadingMissionScheduled windingRollerLoadingMissionScheduled) {
        missionEventHandler.handle(windingRollerLoadingMissionScheduled);
    }

    @EventListener
    public void handle(BufferLocationEmptyRollLoadingMissionScheduled bufferLocationEmptyRollLoadingMissionScheduled) {
        missionEventHandler.handle(bufferLocationEmptyRollLoadingMissionScheduled);
    }

    @EventListener
    public void handle(TrayUnloadingTaskScheduled trayUnloadingTaskScheduled) {
        missionEventHandler.handle(trayUnloadingTaskScheduled);
    }

    @EventListener
    public void handle(TrayLoadingTaskScheduled trayLoadingTaskScheduled) {
        missionEventHandler.handle(trayLoadingTaskScheduled);
    }

}
