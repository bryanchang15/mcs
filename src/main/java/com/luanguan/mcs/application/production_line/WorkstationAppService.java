package com.luanguan.mcs.application.production_line;

import com.luanguan.mcs.application.ApplicationService;
import com.luanguan.mcs.application.production_line.dtos.BookWorkstationInput;
import com.luanguan.mcs.domain.shared_kernel.WorkstationId;

public interface WorkstationAppService extends ApplicationService {

    WorkstationId book(BookWorkstationInput input);

    void roleLoadedFrom(WorkstationId workstation);

    void rollUnLoadedOn(WorkstationId workstation);

    void cancelBooking(WorkstationId workstation);

}
