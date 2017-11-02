package com.ef.models;

import com.ef.logging.Loggable;
import com.ef.models.data.dtos.BlockedIpDto;
import com.ef.models.data.BlockedIpRepository;
import com.ef.models.data.HttpLogRepository;

import java.sql.Timestamp;
import java.util.List;

public class AccessLogParser {

    private Loggable logger;
    private HttpLogRepository httpLogRepository;
    private BlockedIpRepository blockedIpRepository;

    public AccessLogParser(Loggable logger,
                           HttpLogRepository httpLogRepository,
                           BlockedIpRepository blockedIpRepository){
        this.logger = logger;
        this.httpLogRepository = httpLogRepository;
        this.blockedIpRepository = blockedIpRepository;
    }

    public void processLogs(Timestamp startDate, Duration duration, int threshold) {
        List<BlockedIpDto> blockedIpDtos = httpLogRepository.find(startDate, duration, threshold);

        for (BlockedIpDto blockedIpDto : blockedIpDtos){
            logger.print(blockedIpDto.getMessage());
            blockedIpRepository.save(blockedIpDto);
        }
    }
}
