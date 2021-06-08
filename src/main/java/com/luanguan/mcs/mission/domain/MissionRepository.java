package com.luanguan.mcs.mission.domain;

public interface MissionRepository {

    Mission loadBy(MissionId missionId);

    void save(Mission mission);

}
