package com.luanguan.mcs.application.mission;

import com.luanguan.mcs.application.ApplicationService;
import com.luanguan.mcs.application.mission.dtos.CreateRollMissionInput;
import com.luanguan.mcs.application.mission.dtos.CreateTrayMissionInput;
import com.luanguan.mcs.application.mission.dtos.MissionId;

public interface MissionAppService extends ApplicationService {

    public MissionId createRollUnloadingMission(CreateRollMissionInput input);

    public MissionId createRollLoadingMission(CreateRollMissionInput input);

    public MissionId createRollReloadingMission(CreateRollMissionInput input);

    public MissionId createTrayUnloadingMission(CreateTrayMissionInput input);

    public MissionId createTrayLoadingMission(CreateTrayMissionInput input);

    public MissionId createTrayReloadingMission(CreateTrayMissionInput input);

}
