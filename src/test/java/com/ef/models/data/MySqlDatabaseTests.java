package com.ef.models.data;

import com.ef.models.Duration;
import com.ef.models.data.dtos.BlockedIpDto;
import org.junit.Before;
import org.junit.Test;

import java.sql.*;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MySqlDatabaseTests {

    MySqlDatabase cut;
    Connection connection;
    Statement statement;
    PreparedStatement preparedStatement;
    ConnectionFactory connectionFactory;
    BlockedIpDto blockedIpDto = new BlockedIpDto("192.168.1.1",
            "Blocked due to exceeding the hourly threshold of 500 with 502 total requests");

    @Before
    public void beforeEach() {

        connection = mock(Connection.class);
        statement = mock(Statement.class);
        preparedStatement = mock(PreparedStatement.class);
        connectionFactory = mock(ConnectionFactory.class);
    }

    @Test
    public void executeSqlShouldCallStatementExecuteSql() throws SQLException,
            ClassNotFoundException, IllegalAccessException, InstantiationException {
        String sql = "select * from table";

        when(connectionFactory.createConnection(anyString(), anyString(), anyString())).thenReturn(connection);
        when(connection.createStatement()).thenReturn(statement);
        doAnswer(invocation -> null).when(statement).execute(sql);

        cut = new MySqlDatabase(connectionFactory);
        cut.execute(sql);
    }

    @Test
    public void executeUpdateSqlShouldCallStatementExecuteUpdateSql() throws SQLException,
            ClassNotFoundException, IllegalAccessException, InstantiationException {
        String sql = "select * from table";

        when(connectionFactory.createConnection(anyString(), anyString(), anyString())).thenReturn(connection);
        when(connection.createStatement()).thenReturn(statement);
        doAnswer(invocation -> null).when(statement).execute(sql);

        cut = new MySqlDatabase(connectionFactory);
        cut.executeUpdate(sql);
    }

    @Test
    public void executeUpdateForPrepardedStatementShouldCallPreparedStatementExecuteUpdate() throws SQLException,
            ClassNotFoundException, IllegalAccessException, InstantiationException {

        String insertStatement = String.format("INSERT INTO %s.%s (%s, %s) VALUES (?, ?);",
                "wallet_hub",
                Database.Tables.BlockedIps.TABLE_NAME,
                Database.Tables.BlockedIps.IP,
                Database.Tables.BlockedIps.MESSAGE);

        when(connectionFactory.createConnection(anyString(), anyString(), anyString())).thenReturn(connection);
        when(connection.prepareStatement(insertStatement)).thenReturn(preparedStatement);

        cut = new MySqlDatabase(connectionFactory);
        cut.executeUpdate(insertStatement, blockedIpDto.getIp(), blockedIpDto.getMessage());
    }

    @Test
    public void executeQueryForPrepardedStatementShouldCallPreparedStatementExecuteQuery() throws SQLException,
            ClassNotFoundException, IllegalAccessException, InstantiationException {

        String selectStatement =
                String.format("select %s, count(*) as totalRequests from %s " +
                                "where %s between ? and ? " +
                                "group by ip having count(*) > ? order by count(*);",
                        Database.Tables.HttpLogs.IP,
                        Database.Tables.HttpLogs.TABLE_NAME,
                        Database.Tables.HttpLogs.CREATED_ON);

        Instant startDate = Instant.now();
        Instant endDate = startDate.plus(1, ChronoUnit.HOURS);
        int threshold = 500;

        when(connectionFactory.createConnection(anyString(), anyString(), anyString())).thenReturn(connection);
        when(connection.prepareStatement(selectStatement)).thenReturn(preparedStatement);

        cut = new MySqlDatabase(connectionFactory);
        cut.executeQuery(selectStatement,
                String.valueOf(startDate),
                String.valueOf(endDate),
                String.valueOf(threshold));
    }
}
