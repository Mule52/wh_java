package com.ef.models.data;

import com.ef.models.Duration;
import com.ef.models.data.dtos.BlockedIpDto;
import com.google.inject.Inject;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HttpLogRepositoryImpl implements HttpLogRepository {

    private Database database;

    @Inject
    public HttpLogRepositoryImpl(Database database){
        this.database = database;
    }

    @Override
    public List<BlockedIpDto> find(Timestamp startDate, Duration duration, int threshold){
        List<BlockedIpDto> blockedIpDtos = new ArrayList();

        Timestamp endDate = getEndDateFromStartDate(startDate, duration);

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
                BlockedIpDto blockedIpDto = new BlockedIpDto();
                blockedIpDto.setIp(results.getString("ip"));
                blockedIpDto.setMessage(getMessageFor(duration, threshold, results.getInt("totalRequests")));
                blockedIpDtos.add(blockedIpDto);
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

    private Timestamp getEndDateFromStartDate(Timestamp startDate, Duration duration) {
        Timestamp endDate = Timestamp.valueOf(startDate.toLocalDateTime());
        long a = startDate.getTime() + getCalulatedDuration(duration);
        endDate.setTime(a);
        return endDate;
    }

    private long getCalulatedDuration(Duration duration) {
        return duration == Duration.HOURLY ? (long) (60 * 60 * 1000) : (long) (60 * 60 * 1000) * 24;
    }
}
