package com.luanguan.mcs.framework.domain;

import lombok.Value;

@Value
public class Version {

    int versionNum;

    public static Version zero() {
        return new Version(0);
    }

}
