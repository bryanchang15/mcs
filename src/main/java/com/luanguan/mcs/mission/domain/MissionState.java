package com.luanguan.mcs.mission.domain;

import io.vavr.control.Try;
import lombok.*;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum MissionState {

    Created(0),
    Pending(1),
    Executing(2),
    Failed(3),
    Completed(4);

    private final Integer value;

    public Try<MissionState> getByValue(Integer value) {
        for (MissionState state : MissionState.values()) {
            if (value.equals(this.value)) {
                return Try.success(state);
            }
        }

        return Try.failure(new IllegalArgumentException("no such mission state"));
    }

}
