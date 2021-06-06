package com.luanguan.mcs.application.mission.dtos;

import com.luanguan.mcs.domain.shared_kernel.PolePieceType;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CreateRollMissionInput {

    private final String workstationId;

    private final String model;

    private final PolePieceType polePiece;

}
