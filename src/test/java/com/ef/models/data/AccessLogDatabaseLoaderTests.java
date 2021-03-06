package com.ef.models.data;

import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;

import static org.mockito.Mockito.*;

public class AccessLogDatabaseLoaderTests {

    Database database;
    AccessLogDatabaseLoader cut;
    String truncateSql = String.format("TRUNCATE %s", Database.Tables.HttpLogs.TABLE_NAME);
    String filePath = "/access.log";

    String loadQuery = String.format("LOAD DATA LOCAL INFILE '%s' INTO TABLE %s FIELDS TERMINATED BY '|'" +
                    " LINES TERMINATED BY '\n' (%s, %s, %s, %s, %s)",
            filePath,
            Database.Tables.HttpLogs.TABLE_NAME,
            Database.Tables.HttpLogs.CREATED_ON,
            Database.Tables.HttpLogs.IP,
            Database.Tables.HttpLogs.HTTP_METHOD,
            Database.Tables.HttpLogs.HTTP_STATUS_CODE,
            Database.Tables.HttpLogs.USER_AGENT);

    @Before
    public void beforeEach() {
        database = mock(Database.class);
        cut = new AccessLogDatabaseLoader(database);
    }

    @Test
    public void executeShouldCallDatabaseExecuteWithValidFilePath() throws SQLException {

        doAnswer(invocation -> null).when(database).executeUpdate(truncateSql);
        doAnswer(invocation -> null).when(database).execute(loadQuery);

        cut.execute(filePath);
    }

    @Test
    public void executeShouldNotCallDatabaseFunctionsWhenFilePathIsNull() throws SQLException {

        verify(database, never()).executeUpdate(truncateSql);
        verify(database, never()).execute(loadQuery);

        cut.execute(null);
    }
}
