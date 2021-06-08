package com.luanguan.mcs.empty_roll_location.domain;

import com.luanguan.mcs.framework.domain.Version;

public interface EmptyRollLocation {

    EmptyRollLocationId getEmptyRollLocationId();

    EmptyRollLocationPosition getEmptyRollLocationPosition();

    Version getVersion();

}
