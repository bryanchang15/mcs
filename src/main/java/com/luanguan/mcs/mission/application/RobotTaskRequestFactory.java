package com.luanguan.mcs.mission.application;

import com.luanguan.mcs.mission.domain.*;
import io.vavr.control.Try;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;
import static io.vavr.Predicates.instanceOf;

public class RobotTaskRequestFactory {

    public Try<EmptyRollUnloadingRobotTaskRequest> createEmptyRollUnloadingFrom(Mission mission) {
        return Match(mission).of(
                Case($(instanceOf(EmptyRollUnloadingMission.class)), emptyRollUnloadingMission ->
                        Try.success(new EmptyRollUnloadingRobotTaskRequest(
                                emptyRollUnloadingMission.getSourceWindingRoller()
                                        .getWindingMachinePosition(),
                                emptyRollUnloadingMission.getSourceWindingRoller()
                                        .getElectrodeType().getRollerName(),
                                emptyRollUnloadingMission.getSourceWindingRoller()
                                        .getBatteryModel(),
                                emptyRollUnloadingMission.getTargetEmptyRollLocationInformation()
                                        .getEmptyRollRackPosition(),
                                emptyRollUnloadingMission.getTargetEmptyRollLocationInformation()
                                        .getEmptyRollLocationPosition()
                        ))
                ),
                Case($(instanceOf(RollReloadingMission.class)), rollReloadingMission ->
                        Try.success(new EmptyRollUnloadingRobotTaskRequest(
                                rollReloadingMission.getWindingRoller()
                                        .getWindingMachinePosition(),
                                rollReloadingMission.getWindingRoller()
                                        .getElectrodeType().getRollerName(),
                                rollReloadingMission.getWindingRoller()
                                        .getBatteryModel(),
                                rollReloadingMission.getEmptyRollLocationInformation()
                                        .getEmptyRollRackPosition(),
                                rollReloadingMission.getEmptyRollLocationInformation()
                                        .getEmptyRollLocationPosition()
                        ))
                ),
                Case($(), () ->
                        Try.failure(new IllegalStateException())
                )
        );
    }

    public Try<FullRollLoadingRobotTaskRequest> createFullRollLoadingFrom(Mission mission) {
        return Match(mission).of(
                Case($(instanceOf(FullRollLoadingMission.class)), fullRollLoadingMission ->
                                Try.success(new FullRollLoadingRobotTaskRequest(
                                        fullRollLoadingMission.getSourceBufferLocationInformation()
                                                .getBufferLocationPosition(),
                                        fullRollLoadingMission.getSourceTrayPosition(),
                                        fullRollLoadingMission.getTargetWindingRoller()
                                                .getWindingMachinePosition(),
                                        fullRollLoadingMission.getTargetWindingRoller()
                                                .getElectrodeType().getRollerName(),
                                        fullRollLoadingMission.getTargetWindingRoller()
                                                .getBatteryModel()
                                ))
                ),
                Case($(instanceOf(RollReloadingMission.class)), rollReloadingMission ->
                        Try.success(new FullRollLoadingRobotTaskRequest(
                                rollReloadingMission.getBufferLocationInformation()
                                        .getBufferLocationPosition(),
                                rollReloadingMission.getTrayPosition(),
                                rollReloadingMission.getWindingRoller()
                                        .getWindingMachinePosition(),
                                rollReloadingMission.getWindingRoller()
                                        .getElectrodeType().getRollerName(),
                                rollReloadingMission.getWindingRoller()
                                        .getBatteryModel()
                        ))
                ),
                Case($(), () ->
                                Try.failure(new IllegalStateException())
                )
        );
    }

}
