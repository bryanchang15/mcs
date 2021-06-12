package com.luanguan.mcs.winding_machine.domain;

import com.luanguan.mcs.framework.domain.ValueObject;

import lombok.NonNull;
import lombok.Value;

import java.util.UUID;

@Value
public class WindingMachineId implements ValueObject {

    @NonNull
    UUID id;

}
