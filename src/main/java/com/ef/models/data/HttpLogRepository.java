package com.ef.models.data;

import com.ef.models.Duration;
import com.ef.models.data.dtos.BlockedIpDto;

import java.time.Instant;
import java.util.List;

public interface HttpLogRepository {
    List<BlockedIpDto> find(Instant startDate, Duration duration, int threshold);
}
