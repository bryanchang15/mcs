package com.luanguan.mcs.mission.domain;

import io.vavr.control.Option;

public interface MissionRepository {

    Option<Mission> findBy(MissionId missionId);

    Mission save(Mission mission);

}
