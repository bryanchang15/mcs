package com.luanguan.mcs.empty_roll_location.domain;

import com.luanguan.mcs.shared_kernel.Position;

import lombok.NonNull;
import lombok.Value;

@Value
public class EmptyRollLocationPosition {

    @NonNull
    Position rackPosition;

    @NonNull
    Position locationPosition;

}
