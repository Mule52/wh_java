package com.ef.models.data;

import com.ef.models.Duration;
import com.ef.models.data.dtos.BlockedIpDto;

import java.sql.Timestamp;
import java.util.List;

public interface HttpLogRepository {
    List<BlockedIpDto> find(Timestamp startDate, Duration duration, int threshold);
}
