package com.luanguan.mcs.empty_roll_location.domain;

import java.util.UUID;

import lombok.NonNull;
import lombok.Value;

@Value
public class EmptyRollLocationId {

    @NonNull
    UUID id;

}
