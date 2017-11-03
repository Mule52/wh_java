package com.ef.models.data;

import java.sql.Connection;

public interface ConnectionFactory {

    Connection createConnection(String url, String user, String password);
}
