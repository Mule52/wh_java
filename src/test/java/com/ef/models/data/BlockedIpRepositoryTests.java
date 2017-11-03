package com.ef.models.data;

import com.ef.models.data.dtos.BlockedIpDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.mockito.Mockito.*;

public class BlockedIpRepositoryTests {

    Database database;
    BlockedIpRepositoryImpl cut;
    String insertStatement;
    BlockedIpDto blockedIpDto;

    @BeforeEach
    public void beforeEach() {
        database = mock(Database.class);
        cut = new BlockedIpRepositoryImpl(database);

        insertStatement = String.format("INSERT INTO %s.%s (%s, %s) VALUES (?, ?);",
                Database.NAME,
                Database.Tables.BlockedIps.TABLE_NAME,
                Database.Tables.BlockedIps.IP,
                Database.Tables.BlockedIps.MESSAGE);

        blockedIpDto = new BlockedIpDto("192.168.1.1",
                "Blocked due to exceeding the hourly threshold of 500 with 502 total requests");
    }

    @Test
    public void saveShouldCallDatabaseExecuteUpdate() throws SQLException {

        doAnswer(invocation -> null)
                .when(database)
                .executeUpdate(insertStatement, blockedIpDto.getIp(), blockedIpDto.getMessage());

        cut.save(blockedIpDto);
    }

    @Test
    public void saveShouldNotCallDatabaseExecuteUpdateWhenInputIsNull() throws SQLException {

        verify(database, never()).executeUpdate(insertStatement, blockedIpDto.getIp(), blockedIpDto.getMessage());

        cut.save(null);
    }
}
