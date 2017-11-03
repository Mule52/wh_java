package com.ef.models;

import com.ef.logging.Loggable;
import com.ef.models.data.BlockedIpRepository;
import com.ef.models.data.HttpLogRepository;
import com.ef.models.data.dtos.BlockedIpDto;
import org.junit.Test;
import org.mockito.Mockito;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

public class AccessLogParserTests {

    @Test
    public void verifyWhenProcessLogsIsCalledDependenciesAreExecuting() {

        Instant startDate = Instant.now();
        Duration duration = Duration.HOURLY;
        int threshold = 100;

        Loggable logger = mock(Loggable.class);
        HttpLogRepository httpLogRepository = mock(HttpLogRepository.class);
        BlockedIpRepository blockedIpRepository = mock(BlockedIpRepository.class);

        AccessLogParser cut = new AccessLogParser(logger, httpLogRepository, blockedIpRepository);

        List<BlockedIpDto> blockedIpDtos = new ArrayList<>();
        blockedIpDtos.add(new BlockedIpDto("192.168.1.1",
                "Blocked due to exceeding the daily threshold of 500 with 502 total requests"));

        when(httpLogRepository.find(startDate, duration, threshold)).thenReturn(blockedIpDtos);

        cut.processLogs(startDate, duration, threshold);

        verify(logger, Mockito.times(1)).print("192.168.1.1 Blocked due to exceeding the daily threshold of 500 with 502 total requests");
        verify(blockedIpRepository, Mockito.times(1)).save(Mockito.any(BlockedIpDto.class));
    }
}
