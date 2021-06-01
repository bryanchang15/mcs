package com.luanguan.mcs.application;

import com.luanguan.mcs.domain.shared_kernel.MaterialType;
import com.luanguan.mcs.domain.shared_kernel.WorkstationId;

public interface WorkstationApplicationService extends ApplicationService {

    void resetWorkstation(WorkstationId workstation, MaterialType materialType, Boolean withBucket, Boolean isEmptyBucket);

    WorkstationId bookWorkstationToUnload(WorkstationId workstation, MaterialType materialType); // To unload empty bucket on this workstation

    WorkstationId bookWorkstationToLoad(WorkstationId workstation, MaterialType materialType); // To load full bucket to this workstation

    void workstationUnloaded(WorkstationId workstation, Boolean waitReloading);

    void workstationLoaded(WorkstationId workstation);

    void cancelWorkstationBook(WorkstationId workstation);

}
