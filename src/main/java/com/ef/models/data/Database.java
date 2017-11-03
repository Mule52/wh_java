package com.ef.models.data;

import com.ef.models.data.tables.Table;

import java.sql.ResultSet;

public abstract class Database {

    // TODO: replace this with db conf file
    public static final String NAME = "wallet_hub";
    public static final String SERVER_URL = String.format("jdbc:mysql://localhost:3306/%s?useSSL=false", Database.NAME);
    public static final String USERNAME = "fake";
    public static final String PASSWORD = "fake";
    public static final Table Tables = Table.TABLE;

    public abstract void execute(String sql);
    public abstract void executeUpdate(String sql);
    public abstract void executeUpdate(String sql, String... sqlValues);
    public abstract ResultSet executeQuery(String sql, String... sqlValues);
}
