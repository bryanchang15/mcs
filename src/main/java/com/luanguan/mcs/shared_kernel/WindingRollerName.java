package com.luanguan.mcs.shared_kernel;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum WindingRollerName {

    A1("A1"),A2("A2"),C1("C1"),C2("C2");

    private final String name;

    public static WindingRollerName getByName(String name) {
        for (WindingRollerName rollerName : WindingRollerName.values()) {
            if (name.equalsIgnoreCase(rollerName.name)) {
                return rollerName;
            }
        }

        return null;
    }

}
