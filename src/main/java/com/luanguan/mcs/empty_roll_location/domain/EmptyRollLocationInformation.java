package com.luanguan.mcs.empty_roll_location.domain;

import com.luanguan.mcs.shared_kernel.Position;
import lombok.NonNull;
import lombok.Value;

@Value
public class EmptyRollLocationInformation {

    @NonNull
    EmptyRollLocationId emptyRollLocationId;

    @NonNull
    Position emptyRollRackPosition;

    @NonNull
    Position emptyRollLocationPosition;
}
