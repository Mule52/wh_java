package com.ef.models.data;

public class AccessLogDatabaseLoader {

    private Database database;

    public AccessLogDatabaseLoader(Database database){
        this.database = database;
    }

    public void execute(String filePath) {

        if (filePath != null && !filePath.trim().equals("")) {
            String truncateSql = String.format("TRUNCATE %s", Database.Tables.HttpLogs.TABLE_NAME);
            database.executeUpdate(truncateSql);

            String loadQuery = String.format("LOAD DATA LOCAL INFILE '%s' INTO TABLE %s FIELDS TERMINATED BY '|'" +
                            " LINES TERMINATED BY '\n' (%s, %s, %s, %s, %s)",
                    filePath,
                    Database.Tables.HttpLogs.TABLE_NAME,
                    Database.Tables.HttpLogs.CREATED_ON,
                    Database.Tables.HttpLogs.IP,
                    Database.Tables.HttpLogs.HTTP_METHOD,
                    Database.Tables.HttpLogs.HTTP_STATUS_CODE,
                    Database.Tables.HttpLogs.USER_AGENT);
            database.execute(loadQuery);
        }
    }
}
