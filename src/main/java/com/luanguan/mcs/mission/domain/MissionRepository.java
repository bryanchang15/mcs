package com.luanguan.mcs.mission.domain;

import io.vavr.collection.List;
import io.vavr.control.Option;

public interface MissionRepository {

    Option<Mission> findBy(MissionId missionId);

    List<Mission> findAllPendedMissionBy(MissionPendingReason reason);

    Mission save(Mission mission);

}
