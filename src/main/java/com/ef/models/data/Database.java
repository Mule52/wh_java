package com.ef.models.data;

import com.ef.models.data.tables.Table;

import java.sql.ResultSet;

public abstract class Database {

    public static final Table Tables = Table.TABLE;

    public abstract String getDbName();
    public abstract void execute(String sql);
    public abstract void executeUpdate(String sql);
    public abstract void executeUpdate(String sql, String... sqlValues);
    public abstract ResultSet executeQuery(String sql, String... sqlValues);
}
