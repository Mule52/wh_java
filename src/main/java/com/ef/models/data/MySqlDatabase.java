package com.ef.models.data;

import com.google.inject.Inject;

import java.sql.*;

public class MySqlDatabase extends Database {

    private String mysqlUrl = Database.SERVER_URL;
    private String mysqlUser = Database.USERNAME;
    private String mysqlPassword = Database.PASSWORD;

    public Connection connection;
    private Statement statement;
    public static MySqlDatabase mySqlDatabase;

    @Inject
    public MySqlDatabase(ConnectionFactory connectionFactory) {
        connection = connectionFactory.createConnection(mysqlUrl, mysqlUser, mysqlPassword);
    }


    @Override
    public void executeUpdate(String sql){
        execute(sql, true);
    }

    @Override
    public void execute(String sql){
        execute(sql, false);
    }

    private void execute(String sql, boolean isUpdate){
        try {
            statement = connection.createStatement();
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
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            int idx = 1;
            for (String sqlValue : sqlValues){
                preparedStatement.setString(idx++, sqlValue);
            }
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ResultSet executeQuery(String sql, String... sqlValues) {
        ResultSet resultSet = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            int idx = 1;
            for (String sqlValue : sqlValues){
                preparedStatement.setString(idx++, sqlValue);
            }
            resultSet = preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }
}
