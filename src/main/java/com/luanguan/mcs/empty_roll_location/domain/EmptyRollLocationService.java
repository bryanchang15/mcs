package com.luanguan.mcs.empty_roll_location.domain;

import com.luanguan.mcs.shared_kernel.BatteryModel;
import com.luanguan.mcs.winding_machine.domain.ElectrodeType;
import io.vavr.collection.List;
import io.vavr.control.Option;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class EmptyRollLocationService {
    // 此处如果想要优化业务逻辑，需要提供怎样判断空卷暂存库位更合适的业务规则

    private final EmptyRollLocationRepository emptyRollLocationRepository;

    public Option<LoadedEmptyRollLocation> findLoadedEmptyRollLocationBy(
            BatteryModel batteryModel,
            ElectrodeType electrodeType
    ) {
        List<LoadedEmptyRollLocation> locations =
                emptyRollLocationRepository.findAllLoadedEmptyRollLocationBy(
                        batteryModel,
                        electrodeType
                );
        if (locations.size() > 0) {
            return Option.of(locations.get(0));
        }

        return Option.none();
    }

    public Option<UnloadedEmptyRollLocation> findUnloadedEmptyRollLocationBy(
            BatteryModel batteryModel,
            ElectrodeType electrodeType
    ) {
        List<UnloadedEmptyRollLocation> locations =
                emptyRollLocationRepository.findAllUnloadedEmptyRollLocation();
        if (locations.size() > 0) {
            return Option.of(locations.get(0));
        }

        return Option.none();
    }

}
