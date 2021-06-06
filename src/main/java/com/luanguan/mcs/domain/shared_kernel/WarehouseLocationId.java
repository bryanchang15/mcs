package com.luanguan.mcs.domain.shared_kernel;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class WarehouseLocationId {

    private final String warehouseId;

    private final String id;

    private final Position position;

}
