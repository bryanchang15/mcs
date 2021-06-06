package com.luanguan.mcs.application.production_line;

import com.luanguan.mcs.application.ApplicationService;
import com.luanguan.mcs.application.production_line.dtos.BookLoadingLocationInput;
import com.luanguan.mcs.application.production_line.dtos.BookUnloadingLocationInput;
import com.luanguan.mcs.application.production_line.dtos.SetStockInput;
import com.luanguan.mcs.application.production_line.dtos.SetStocksInput;
import com.luanguan.mcs.domain.shared_kernel.WarehouseLocationId;

public interface WarehouseAppService extends ApplicationService
{

    void setStock(SetStockInput input);

    void setStocks(SetStocksInput input);

    WarehouseLocationId bookLoadingLocation(BookLoadingLocationInput input);

    WarehouseLocationId bookUnloadingLocation(BookUnloadingLocationInput input);

    void trayLoadedFrom(WarehouseLocationId laodingLocation);

    void trayUnloadedOn(WarehouseLocationId unloadingLocation);

    void cancelLoadingLocationBooking(WarehouseLocationId loadingLocation);

    void cancelUnloadingLocationBooking(WarehouseLocationId unloadingLocation);

}
