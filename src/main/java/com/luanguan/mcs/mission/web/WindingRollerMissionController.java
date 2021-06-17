package com.luanguan.mcs.mission.web;

import com.luanguan.mcs.mission.application.CreateWindingRollerMissionCommand;
import com.luanguan.mcs.mission.application.CreatingWindingRollerMission;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/_mission/winding-rollers")
@AllArgsConstructor
public class WindingRollerMissionController {

    private final CreatingWindingRollerMission creatingWindingRollerMission;

    @PostMapping("/unloading")
    public ResponseEntity<CreateMissionResponse> createUnloading(
            @RequestBody CreateWindingRollerMissionRequest request
    ) {
        return creatingWindingRollerMission.createUnloading(CreateWindingRollerMissionCommand.create(
                request.getWindingMachineId(),
                request.getBatteryModelName(),
                request.getWindingRollerName()
        )).map(result -> ResponseEntity.ok(new CreateMissionResponse(
                0,
                result.getMissionId().toString(),
                null
        ))).recover(r -> ResponseEntity.ok(new CreateMissionResponse(
                1, null, r.getMessage()
        ))).getOrElse(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
    }

    @PostMapping("/loading")
    public ResponseEntity<CreateMissionResponse> createLoading(
            @RequestBody CreateWindingRollerMissionRequest request
    ) {
        return creatingWindingRollerMission.createLoading(CreateWindingRollerMissionCommand.create(
                request.getWindingMachineId(),
                request.getBatteryModelName(),
                request.getWindingRollerName()
        )).map(result -> ResponseEntity.ok(new CreateMissionResponse(
                0,
                result.getMissionId().toString(),
                null
        ))).recover(r -> ResponseEntity.ok(new CreateMissionResponse(
                1, null, r.getMessage()
        ))).getOrElse(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
    }

    @PostMapping("/reloading")
    public ResponseEntity<CreateMissionResponse> createReloading(
            @RequestBody CreateWindingRollerMissionRequest request
    ) {
        return creatingWindingRollerMission.createReloading(CreateWindingRollerMissionCommand.create(
                request.getWindingMachineId(),
                request.getBatteryModelName(),
                request.getWindingRollerName()
        )).map(result -> ResponseEntity.ok(new CreateMissionResponse(
                0,
                result.getMissionId().toString(),
                null
        ))).recover(r -> ResponseEntity.ok(new CreateMissionResponse(
                1, null, r.getMessage()
        ))).getOrElse(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
    }

}
