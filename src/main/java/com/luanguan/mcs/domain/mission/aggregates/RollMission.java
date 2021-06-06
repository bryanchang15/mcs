package com.luanguan.mcs.domain.mission.aggregates;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public abstract class RollMission extends Mission {

    private String sourceWorkstationId;

    private String model;

    private String polePieceType;

}
