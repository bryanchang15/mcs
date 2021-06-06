package com.luanguan.mcs.domain.shared_kernel;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum PolePieceType {

    POSITIVE_LEFT(0),
    POSITIVE_RIGHT(1),
    NEGATIVE_LEFT(2),
    NEGATIVE_RIGHT(3);

    private final Integer polePiece;

}
