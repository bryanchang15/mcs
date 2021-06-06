package com.luanguan.mcs.application.production_line.dtos;

import java.util.Map;

import com.luanguan.mcs.domain.shared_kernel.PolePieceType;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SetStocksInput {

    private final String warehouseId;

    private final Map<String, Map<PolePieceType, Integer>> stocks;

}