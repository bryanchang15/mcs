package com.luanguan.mcs.winding_machine.domain;

import com.luanguan.mcs.shared_kernel.WindingRollerName;

import io.vavr.control.Try;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum ElectrodeType {

    PositiveLeft(0, WindingRollerName.A1),
    PositiveRight(1, WindingRollerName.A2),
    NegativeLeft(2, WindingRollerName.C1),
    NegativeRight(3, WindingRollerName.C2);

    private final Integer value;
    private final WindingRollerName rollerName;

    public static Try<ElectrodeType> getByRollerName(WindingRollerName rollerName) {
        for (ElectrodeType electrodeType : ElectrodeType.values()) {
            if (rollerName == electrodeType.rollerName)
                return Try.success(electrodeType);
        }

        return Try.failure(new IllegalArgumentException("no such winding roller"));
    }

    public static ElectrodeType getByValue(Integer value) {
        for (ElectrodeType electrodeType : ElectrodeType.values()) {
            if (value == electrodeType.value)
                return electrodeType;
        }

        throw new Error("ElectordeType value is changed which should not");
    }

}
