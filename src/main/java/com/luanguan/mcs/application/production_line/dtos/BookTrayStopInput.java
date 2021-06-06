package com.luanguan.mcs.application.production_line.dtos;

import com.luanguan.mcs.domain.shared_kernel.OperationType;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class BookTrayStopInput {

    private final OperationType operation;

    private final String id;

}
