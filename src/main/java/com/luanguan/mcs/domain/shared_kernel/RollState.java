package com.luanguan.mcs.domain.shared_kernel;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum RollState {

    NO_ROLL(0),
    EMPTY_ROLL(1),
    FULL_ROLL(2);

    private final Integer state;

}
