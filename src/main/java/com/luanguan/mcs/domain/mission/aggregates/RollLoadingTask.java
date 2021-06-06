package com.luanguan.mcs.domain.mission.aggregates;

import com.luanguan.mcs.domain.shared_kernel.RobotId;
import com.luanguan.mcs.domain.shared_kernel.StoppedTrayPositionId;
import com.luanguan.mcs.domain.shared_kernel.WorkstationId;

public class RollLoadingTask extends RollTask {

    private StoppedTrayPositionId bookedTrayPosition;

    public void assignTrayPosition(StoppedTrayPositionId trayPosition) {

    }

}
