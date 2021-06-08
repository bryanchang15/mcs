package com.luanguan.mcs.winding_machine.domain;

import java.util.Optional;

import com.luanguan.mcs.shared_kernel.WindingRollerName;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum ElectrodeType {

    PositiveLeft("A1"), PositiveRight("A2"), NegativeLeft("C1"), NegativeRight("C2");

    private final String rollerName;

    public static Optional<ElectrodeType> getByRollerName(WindingRollerName rollerName) {
        for (ElectrodeType electrodeType : ElectrodeType.values()) {
            if (rollerName.getName() == electrodeType.rollerName)
                return Optional.of(electrodeType);
        }

        return Optional.empty();
    }

}
