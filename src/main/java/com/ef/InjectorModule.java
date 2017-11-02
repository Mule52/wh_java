package com.ef;

import com.ef.logging.Loggable;
import com.ef.models.data.BlockedIpRepositoryImpl;
import com.ef.models.data.BlockedIpRepository;
import com.ef.models.data.HttpLogRepository;
import com.ef.models.data.HttpLogRepositoryImpl;
import com.ef.models.data.Database;
import com.ef.models.data.MySqlDatabase;
import com.ef.logging.ConsoleLogger;
import com.google.inject.AbstractModule;

public class InjectorModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(Loggable.class).to(ConsoleLogger.class);
        bind(Database.class).to(MySqlDatabase.class);
        bind(HttpLogRepository.class).to(HttpLogRepositoryImpl.class);
        bind(BlockedIpRepository.class).to(BlockedIpRepositoryImpl.class);
    }
}
