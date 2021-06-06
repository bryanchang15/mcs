package com.luanguan.mcs.domain.shared_kernel;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum RollPosition {

    OUTER(0),
    INNER(1);

    private final Integer position;

}
