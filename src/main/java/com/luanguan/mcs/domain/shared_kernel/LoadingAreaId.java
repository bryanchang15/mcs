package com.luanguan.mcs.domain.shared_kernel;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class LoadingAreaId {

    private final WarehouseId warehouse;

    private final String localId;

    private final Position position;

}
