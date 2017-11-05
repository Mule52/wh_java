package com.ef;

import com.ef.logging.Loggable;
import com.ef.models.data.*;
import com.ef.logging.ConsoleLogger;
import com.google.inject.AbstractModule;
import com.google.inject.name.Names;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class InjectorModule extends AbstractModule {
    @Override
    protected void configure() {

        // set default properties
        Properties defaults = new Properties();
        defaults.setProperty("db_server_url", "jdbc:mysql://localhost:3306/name?useSSL=false");
        defaults.setProperty("db_name", "name");
        defaults.setProperty("db_username", "user");
        defaults.setProperty("db_password", "password");

        try {
            // read in properties from db.properties and bind them
            Properties props = new Properties(defaults);
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("db.properties");
            props.load(inputStream);
            Names.bindProperties(binder(), props);
        } catch (IOException e) {
            new ConsoleLogger().print("Could not load config: " + e);
            System.exit(1);
        }

        // DI definitions
        bind(Loggable.class).to(ConsoleLogger.class);
        bind(ConnectionFactory.class).to(MySqlConnectionFactory.class);
        bind(Database.class).to(MySqlDatabase.class);
        bind(HttpLogRepository.class).to(HttpLogRepositoryImpl.class);
        bind(BlockedIpRepository.class).to(BlockedIpRepositoryImpl.class);
    }
}
