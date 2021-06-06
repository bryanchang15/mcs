package com.luanguan.mcs.application.production_line.dtos;

import com.luanguan.mcs.domain.shared_kernel.OperationType;
import com.luanguan.mcs.domain.shared_kernel.PolePieceType;
import com.luanguan.mcs.domain.shared_kernel.RollState;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class BookStoppedTrayInput {

    private final OperationType operationType;

    private final String model;

    private final PolePieceType polePieceType;

    private final RollState exceptOrTargetRoll;

}