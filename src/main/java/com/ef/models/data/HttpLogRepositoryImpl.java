package com.ef.models.data;

import com.ef.models.Duration;
import com.ef.models.data.dtos.BlockedIpDto;
import com.google.inject.Inject;

import java.sql.*;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class HttpLogRepositoryImpl implements HttpLogRepository {

    private Database database;

    @Inject
    public HttpLogRepositoryImpl(Database database){
        this.database = database;
    }

    @Override
    public List<BlockedIpDto> find(Instant startDate, Duration duration, int threshold){
        List<BlockedIpDto> blockedIpDtos = new ArrayList();

        Instant endDate = getEndDateFromStartDate(startDate, duration);

        String selectStatement =
                String.format("select %s, count(*) as totalRequests from %s " +
                                "where %s between ? and ? " +
                                "group by ip having count(*) > ? order by count(*);",
                        Database.Tables.HttpLogs.IP,
                        Database.Tables.HttpLogs.TABLE_NAME,
                        Database.Tables.HttpLogs.CREATED_ON);

        ResultSet results = database.executeQuery(selectStatement,
                String.valueOf(startDate),
                String.valueOf(endDate),
                String.valueOf(threshold));

        try {
            while(results.next()){
                String ip = results.getString("ip");
                String msg = getMessageFor(duration, threshold, results.getInt("totalRequests"));
                blockedIpDtos.add(new BlockedIpDto(ip, msg));
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
        }

        return blockedIpDtos;
    }

    private String getMessageFor(Duration duration, int threshold, int totalRequests) {
        return String.format("Blocked due to exceeding the %s threshold of %d with %d total %s",
                duration == Duration.DAILY ? "daily" : "hourly",
                threshold,
                totalRequests,
                totalRequests == 1 ? "request" : "requests");
    }

    private Instant getEndDateFromStartDate(Instant startDate, Duration duration) {
        Instant endDate;
        if (duration == Duration.HOURLY){
            endDate = startDate.plus(1, ChronoUnit.HOURS);
        } else {
            endDate = startDate.plus(1, ChronoUnit.DAYS);
        }
        return endDate;
    }
}
