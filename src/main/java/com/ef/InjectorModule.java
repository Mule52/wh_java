package com.ef;

import com.ef.logging.Loggable;
import com.ef.models.data.*;
import com.ef.logging.ConsoleLogger;
import com.google.inject.AbstractModule;

public class InjectorModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(Loggable.class).to(ConsoleLogger.class);
        bind(ConnectionFactory.class).to(MySqlConnectionFactory.class);
        bind(Database.class).to(MySqlDatabase.class);
        bind(HttpLogRepository.class).to(HttpLogRepositoryImpl.class);
        bind(BlockedIpRepository.class).to(BlockedIpRepositoryImpl.class);
    }
}
