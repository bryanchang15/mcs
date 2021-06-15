package com.luanguan.mcs.shared_kernel;

import lombok.*;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum TrayPosition {

    Outer("OUTER"),
    Inner("INNER");

    private final String name;

}
