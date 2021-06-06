package com.luanguan.mcs.application.mission.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class MissionId {

    private final String id;

    private final MissionType type;

}
