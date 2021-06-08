package com.luanguan.mcs.empty_roll_location.domain;

import java.util.List;
import java.util.Optional;

import com.luanguan.mcs.shared_kernel.BatteryModel;
import com.luanguan.mcs.winding_machine.domain.ElectrodeType;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class EmptyRollLocationService {
    // 此处如果想要优化业务逻辑，需要提供怎样判断空卷暂存库位更合适的业务规则

    private final EmptyRollLocationRepository emptyRollLocationRepository;

    public Optional<LoadedEmptyRollLocation> findLoadedEmptyRollLocationBy(BatteryModel batteryModel,
            ElectrodeType electrodeType) {
        List<LoadedEmptyRollLocation> locations = emptyRollLocationRepository
                .findLoadedEmptyRollLocationBy(batteryModel, electrodeType);
        if (locations.size() > 0) {
            return Optional.of(locations.get(0));
        }

        return Optional.empty();
    }

    public Optional<UnloadedEmptyRollLocation> findUnloadedEmptyRollLocationBy(BatteryModel batteryModel,
            ElectrodeType electrodeType) {
        List<UnloadedEmptyRollLocation> locations = emptyRollLocationRepository.findAllUnloadedEmptyRollLocation();
        if (locations.size() > 0) {
            return Optional.of(locations.get(0));
        }

        return Optional.empty();
    }

}
