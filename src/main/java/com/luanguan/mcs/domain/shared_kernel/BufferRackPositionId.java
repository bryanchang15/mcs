package com.luanguan.mcs.domain.shared_kernel;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class BufferRackPositionId {

    private final String rackId;

    private final Position rackPosition;

    private final String locationId;

    private final Position locationPosition;

    private final RollPosition rollPosition;

    public BufferRackId getBufferRack() {
        return new BufferRackId(this.rackId, this.rackPosition);
    }

    public BufferRackLocationId getBufferRackLocation() {
        return new BufferRackLocationId(this.rackId, this.rackPosition, this.locationId, this.locationPosition);
    }

}
