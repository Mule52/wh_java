package com.ef.models.data;

import java.sql.*;

public class MySqlDatabase extends Database {

    private String mysqlUrl = Database.SERVER_URL;
    private String mysqlUser = Database.USERNAME;
    private String mysqlPassword = Database.PASSWORD;

    public Connection connection;
    private Statement statement;
    public static MySqlDatabase mySqlDatabase;


    public MySqlDatabase() {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            connection = DriverManager.getConnection(mysqlUrl, mysqlUser, mysqlPassword);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void executeUpdate(String sql){
        execute(sql, true);
    }

    public void execute(String sql){
        execute(sql, false);
    }

    private void execute(String sql, boolean isUpdate){
        try {
            statement = connection.prepareStatement(sql);
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
