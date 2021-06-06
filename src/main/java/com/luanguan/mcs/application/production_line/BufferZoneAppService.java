package com.luanguan.mcs.application.production_line;

import com.luanguan.mcs.application.ApplicationService;
import com.luanguan.mcs.application.production_line.dtos.BookBufferRackInput;
import com.luanguan.mcs.application.production_line.dtos.BookStoppedTrayInput;
import com.luanguan.mcs.application.production_line.dtos.BookTrayStopInput;
import com.luanguan.mcs.domain.shared_kernel.BufferRackId;
import com.luanguan.mcs.domain.shared_kernel.BufferRackPositionId;
import com.luanguan.mcs.domain.shared_kernel.RollState;
import com.luanguan.mcs.domain.shared_kernel.StoppedTrayPositionId;
import com.luanguan.mcs.domain.shared_kernel.TrayState;
import com.luanguan.mcs.domain.shared_kernel.TrayStopId;

public interface BufferZoneAppService extends ApplicationService {

    StoppedTrayPositionId bookStoppedTray(BookStoppedTrayInput input);

    void rollLoadedFromTray(StoppedTrayPositionId position);

    void rollUnloadedOnTray(StoppedTrayPositionId position, RollState roll);

    void cancelStoppedTrayBooking(TrayStopId trayStop);

    TrayStopId bookTrayStop(BookTrayStopInput input);

    void trayLeftStop(TrayStopId trayStop);

    void trayEnteredStop(TrayStopId trayStop, TrayState tray);

    void cancelTrayStopBooking(TrayStopId trayStopLocation);

    BufferRackPositionId bookBufferRack(BookBufferRackInput input);

    void rollLoadedFromRack(BufferRackPositionId position);

    void rollUnloadedOnRack(BufferRackPositionId position, RollState roll);

    void cancelBufferRackBooking(BufferRackId bufferRack);

}
