package com.luanguan.mcs.empty_roll_location.domain;

import java.util.List;

import com.luanguan.mcs.shared_kernel.BatteryModel;
import com.luanguan.mcs.winding_machine.domain.ElectrodeType;

public interface EmptyRollLocationRepository {

    List<LoadedEmptyRollLocation> findLoadedEmptyRollLocationBy(BatteryModel batteryModel, ElectrodeType electrodeType);

    List<UnloadedEmptyRollLocation> findAllUnloadedEmptyRollLocation();

}
