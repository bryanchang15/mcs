package com.luanguan.mcs.empty_roll_location.domain;

import com.luanguan.mcs.framework.domain.Version;
import com.luanguan.mcs.shared_kernel.Position;

public interface EmptyRollLocation {

    default EmptyRollLocationId emptyRollLocationId() {
        return getEmptyRollLocationInformation().getEmptyRollLocationId();
    }

    default Position emptyRollRackPosition() {
        return getEmptyRollLocationInformation().getEmptyRollRackPosition();
    }

    default Position emptyRollLocationPosition() {
        return getEmptyRollLocationInformation().getEmptyRollLocationPosition();
    }

    EmptyRollLocationInformation getEmptyRollLocationInformation();

    Version getVersion();

}
