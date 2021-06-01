package com.luanguan.mcs.application;

import java.util.Map;

import com.luanguan.mcs.domain.shared_kernel.LoadingAreaId;
import com.luanguan.mcs.domain.shared_kernel.MaterialType;
import com.luanguan.mcs.domain.shared_kernel.UnloadingAreaId;
import com.luanguan.mcs.domain.shared_kernel.WarehouseId;

public interface WarehouseApplicationService extends ApplicationService
{

    void resetWarehouse(WarehouseId warehouse, Map<MaterialType, Integer> stocks);

    LoadingAreaId bookLoadingAreaToUnload(MaterialType materialType, Integer num); // To unload a tray on this loading area

    UnloadingAreaId bookUnloadingAreaToLoad(MaterialType materialType); // To load a tray to this unloading area

    void loadingAreaUnloaded(LoadingAreaId loadingArea);

    void unloadingAreaLoaded(UnloadingAreaId unloadingArea);

    void cancelLoadingAreaBook(LoadingAreaId loadingArea);

    void cancelUnloadingAreaBook(UnloadingAreaId unloadingArea);

}
