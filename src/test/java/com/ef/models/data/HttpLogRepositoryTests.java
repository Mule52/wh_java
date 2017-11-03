package com.ef.models.data;

import com.ef.models.Duration;
import com.ef.models.data.dtos.BlockedIpDto;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class HttpLogRepositoryTests {

    @Test
    public void findShouldReturnListOfBlockedIpDto() throws SQLException {
        Database database = mock(Database.class);
        HttpLogRepositoryImpl cut = new HttpLogRepositoryImpl(database);

        Instant startDate = Instant.now();
        Instant endDate = startDate.plus(1, ChronoUnit.HOURS);
        Duration duration = Duration.HOURLY;
        int threshold = 500;

        List<BlockedIpDto> expectedResults = new ArrayList<>();
        expectedResults.add(new BlockedIpDto("192.168.1.1",
                "Blocked due to exceeding the hourly threshold of 500 with 502 total requests"));

        String selectStatement =
                String.format("select %s, count(*) as totalRequests from %s " +
                                "where %s between ? and ? " +
                                "group by ip having count(*) > ? order by count(*);",
                        Database.Tables.HttpLogs.IP,
                        Database.Tables.HttpLogs.TABLE_NAME,
                        Database.Tables.HttpLogs.CREATED_ON);

        ResultSet resultSet = mock(ResultSet.class);

        when(database.executeQuery(selectStatement,
                String.valueOf(startDate),
                String.valueOf(endDate),
                String.valueOf(threshold)))
                .thenReturn(resultSet);

        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(resultSet.getString("ip")).thenReturn("192.168.1.1");
        when(resultSet.getInt("totalRequests")).thenReturn(502);

        List<BlockedIpDto> actualResults = cut.find(startDate, duration, threshold);

        for (int i=0; i<actualResults.size(); i++){
            assertEquals(expectedResults.get(i).toString(), actualResults.get(i).toString());
        }
    }
}
