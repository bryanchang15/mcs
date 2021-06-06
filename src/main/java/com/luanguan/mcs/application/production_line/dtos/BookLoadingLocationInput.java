package com.luanguan.mcs.application.production_line.dtos;

import com.luanguan.mcs.domain.shared_kernel.PolePieceType;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class BookLoadingLocationInput {

    private final String model;

    private final PolePieceType polePieceType;

    private final Integer trayCapacity;

}