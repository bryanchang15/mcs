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
    public void handle(FullRollLoadingTaskScheduled fullRollLoadingTaskScheduled) {
        missionEventHandler.handle(fullRollLoadingTaskScheduled);
    }

    @EventListener
    public void handle(EmptyRollLoadingTaskScheduled emptyRollLoadingTaskScheduled) {
        missionEventHandler.handle(emptyRollLoadingTaskScheduled);
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
