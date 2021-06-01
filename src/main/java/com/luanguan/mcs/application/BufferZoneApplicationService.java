package com.luanguan.mcs.application;

import com.luanguan.mcs.domain.shared_kernel.BufferZoneId;
import com.luanguan.mcs.domain.shared_kernel.BufferZonePosition;
import com.luanguan.mcs.domain.shared_kernel.MaterialType;

public interface BufferZoneApplicationService extends ApplicationService {

    void resetBufferZone(BufferZoneId bufferZone, Integer numEmptyBuckets, Integer numFullBuckets);

    BufferZonePosition bookBufferZonePositionToUnload(MaterialType materialType); // To unload a full bucket on this position of a buffer zone

    BufferZonePosition bookBufferZonePositionToLoad(MaterialType materialType); // To load a empty bucket to this position of a buffer zone

    void bufferZonePositionUnloaded(BufferZonePosition bufferZonePosition);

    void bufferZonePositionLoaed(BufferZonePosition bufferZonePosition);

    void cancelBufferZonePositionBook(BufferZonePosition bufferZonePosition);

    BufferZoneId bookBufferZoneToUnload(BufferZoneId bufferZone); // To unload a tray on this buffer zone

    BufferZoneId bookBufferZoneToLoad(BufferZoneId bufferZone); // To load a tray to this buffer zone

    void bufferZoneUnloaed(BufferZoneId bufferZone);

    void bufferZoneLoaded(BufferZoneId bufferZone);

    void cancelBufferZoneBook(BufferZoneId bufferZone);

}
