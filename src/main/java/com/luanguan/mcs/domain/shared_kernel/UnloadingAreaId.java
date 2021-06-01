package com.luanguan.mcs.domain.shared_kernel;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UnloadingAreaId {

    private final WarehouseId warehouse;

    private final String localId;

    private final Position position;

}
