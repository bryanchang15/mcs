package com.luanguan.mcs.domain.mission.aggregates;

import com.luanguan.mcs.domain.shared_kernel.BufferRackPositionId;
import com.luanguan.mcs.domain.shared_kernel.RobotId;
import com.luanguan.mcs.domain.shared_kernel.WorkstationId;

public class RollUnloadingTask extends RollTask {

    private BufferRackPositionId bookedBufferRackPosition;

    public void assignBufferRackPosition(BufferRackPositionId bufferRackPosition) {

    }

}
