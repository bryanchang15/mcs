package com.luanguan.mcs.domain.mission.aggregates;

public abstract class Mission {

    private String id;

    private Integer state; // CREATED,PENDING,COMPLETED

    private Integer pendingReason;

    public abstract void pended(Integer pendingReason);

    public abstract void completed();

}
