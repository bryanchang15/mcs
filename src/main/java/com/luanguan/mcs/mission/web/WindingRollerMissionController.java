package com.luanguan.mcs.mission.web;

import com.luanguan.mcs.mission.application.CreateWindingRollerLoadingMission;
import com.luanguan.mcs.mission.domain.Mission;
import com.luanguan.mcs.mission.domain.MissionId;
import com.luanguan.mcs.shared_kernel.*;
import io.vavr.control.Try;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/_mission/winding-rollers")
@AllArgsConstructor
public class WindingRollerMissionController {

    private final CreateWindingRollerLoadingMission createWindingRollerLoadingMission;

    @PostMapping("/unloading")
    public ResponseEntity<CreateMissionResult> createUnloading(
            @RequestBody CreateWindingRollerMissionRequest request
    ) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @PostMapping("/loading")
    public ResponseEntity<CreateMissionResult> createLoading(
            @RequestBody CreateWindingRollerMissionRequest request
    ) {
        Try<Mission> mission = createWindingRollerLoadingMission.createBy(
                new Position(request.getWindingMachineId()),
                new BatteryModel(request.getBatteryModelName()),
                WindingRollerName.getByName(request.getWindingRollerName())
        );
        return mission.map(m -> ResponseEntity.ok(new CreateMissionResult(
                        0,
                        null,
                        m.getMissionId().getId().toString()
        ))).recover(r -> ResponseEntity.ok(new CreateMissionResult(
                1, r.getMessage(), null
        ))).getOrElse(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
    }

    @PostMapping("/reloading")
    public ResponseEntity<CreateMissionResult> createReloading(
            @RequestBody CreateWindingRollerMissionRequest request
    ) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

}
