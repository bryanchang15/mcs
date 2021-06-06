package com.luanguan.mcs.domain.shared_kernel;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class TrayState {

    private final Integer emptyRollsNum;

    private final Integer fullRollsNum;

    public static TrayState createEmptyTray() {
        return new TrayState(0, 0);
    }

    public static TrayState createEmptyRollTray(Integer rollsNum) {
        if (rollsNum <= 0) {
            throw new RuntimeException("Invalid tray state is given");
        }

        return new TrayState(rollsNum, 0);

    }

    public static TrayState createFullRollTray(Integer rollsNum) {
        if (rollsNum <= 0) {
            throw new RuntimeException("Invalid tray state is given");
        }

        return new TrayState(0, rollsNum);
    }

    public Boolean isEmpty() {

        return (this.emptyRollsNum == 0) && (this.fullRollsNum == 0);

    }

    public Boolean hasEmptyRolls() {

        return (this.emptyRollsNum > 0);

    }

    public Boolean hasFullRolls() {

        return (this.fullRollsNum > 0);

    }

}
