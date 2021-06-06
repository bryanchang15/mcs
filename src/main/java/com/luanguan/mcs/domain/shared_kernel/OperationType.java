package com.luanguan.mcs.domain.shared_kernel;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum OperationType {

    OPERATION_UNLOADING(0),
    OPERATION_LOADING(1),
    OPERATION_RELOADING(2);

    private final Integer operation;

}
