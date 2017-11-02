package com.ef.models.data;

import com.ef.models.data.dtos.BlockedIpDto;

public interface BlockedIpRepository {

    void save(BlockedIpDto blockedIpDto);
}
