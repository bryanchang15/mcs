package com.luanguan.mcs.empty_roll_location.domain;

import com.luanguan.mcs.mission.domain.MissionId;
import com.luanguan.mcs.shared_kernel.BatteryModel;
import com.luanguan.mcs.winding_machine.domain.ElectrodeType;
import io.vavr.collection.List;
import io.vavr.control.Option;

public interface EmptyRollLocationRepository {

    List<LoadedEmptyRollLocation> findAllLoadedEmptyRollLocationBy(
            BatteryModel batteryModel,
            ElectrodeType electrodeType
    );

    List<UnloadedEmptyRollLocation> findAllUnloadedEmptyRollLocation();

    Option<EmptyRollLocation> findBy(MissionId missionId);

    Option<EmptyRollLocation> findBy(EmptyRollLocationId emptyRollLocationId);

    EmptyRollLocation save(EmptyRollLocation emptyRollLocation);

}
