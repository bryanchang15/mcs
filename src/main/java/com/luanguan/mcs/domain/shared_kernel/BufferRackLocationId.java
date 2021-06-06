package com.luanguan.mcs.domain.shared_kernel;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class BufferRackLocationId {

    private final String rackId;

    private final Position rackPosition;

    private final String locationId;

    private final Position locationPosition;

}
