package com.ef.models.data;

import com.google.inject.Inject;
import com.google.inject.name.Named;

import java.sql.*;

public class MySqlDatabase extends Database {

    private Connection connection;
    private ConnectionFactory connectionFactory;
    private Statement statement;

    @Inject
    @Named("db_server_url")
    public String dbUrl;

    @Inject
    @Named("db_name")
    public String dbName;

    @Inject
    @Named("db_username")
    public String dbUser;

    @Inject
    @Named("db_password")
    public String dbPassword;

    @Inject
    public MySqlDatabase(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    private Connection getConnection(){
        if (connection == null){
            connection = connectionFactory.createConnection(dbUrl, dbUser, dbPassword);
        }
        return connection;
    }

    @Override
    public void executeUpdate(String sql){
        execute(sql, true);
    }

    @Override
    public String getDbName() {
        return dbName;
    }

    @Override
    public void execute(String sql){
        execute(sql, false);
    }

    private void execute(String sql, boolean isUpdate){
        try {
            statement = getConnection().createStatement();
            if (isUpdate){
                statement.executeUpdate(sql);
            } else {
                statement.execute(sql);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void executeUpdate(String sql, String... sqlValues) {
        try {
            PreparedStatement preparedStatement = getPreparedStatement(sql, sqlValues);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ResultSet executeQuery(String sql, String... sqlValues) {
        ResultSet resultSet = null;
        try {
            PreparedStatement preparedStatement = getPreparedStatement(sql, sqlValues);
            resultSet = preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    private PreparedStatement getPreparedStatement(String sql, String[] sqlValues) throws SQLException {
        PreparedStatement preparedStatement = getConnection().prepareStatement(sql);
        int idx = 1;
        for (String sqlValue : sqlValues){
            preparedStatement.setString(idx++, sqlValue);
        }
        return preparedStatement;
    }
}
