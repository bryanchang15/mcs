package com.luanguan.mcs.domain.shared_kernel;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class StoppedTrayPositionId {

    private final String stopId;

    private final Position stopPosition;

    private final RollPosition rollPosition;

    public TrayStopId getTrayStop() {
        return new TrayStopId(this.stopId, this.stopPosition);
    }

}
