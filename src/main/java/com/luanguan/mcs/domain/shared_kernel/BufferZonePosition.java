package com.luanguan.mcs.domain.shared_kernel;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class BufferZonePosition {

    private final BufferZoneId bufferZone;

    private final String localId;

    private final Position position;

}
