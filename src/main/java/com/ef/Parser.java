package com.ef;

import com.ef.logging.Loggable;
import com.ef.models.AccessLogParser;
import com.ef.models.ApplicationArguments;
import com.ef.models.data.AccessLogDatabaseLoader;
import com.ef.models.data.BlockedIpRepository;
import com.ef.models.data.Database;
import com.ef.models.data.HttpLogRepository;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;

public class Parser {

    @Inject
    public Loggable logger;

    @Inject
    Database database;

    @Inject
    public HttpLogRepository httpLogRepository;

    @Inject
    BlockedIpRepository blockedIpRepository;

    public static void main(String[] args) throws Exception {

        Injector injector = Guice.createInjector(new InjectorModule());
        Parser parser = injector.getInstance(Parser.class);

        Loggable logger = parser.logger;

        // Init and validate parser arguments
        ApplicationArguments applicationArguments = new ApplicationArguments(args);

        // Load the log file data into the database if provided,
        // otherwise assume the data is already loaded in the database.
        if (applicationArguments.hasAccessLog()) {
            AccessLogDatabaseLoader loadAccessLogIntoDatabase = new AccessLogDatabaseLoader(parser.database);
            loadAccessLogIntoDatabase.execute(applicationArguments.getAccessLog());
        }

        // Print logs to console and save to database
        AccessLogParser accessLogParser = new AccessLogParser(logger, parser.httpLogRepository, parser.blockedIpRepository);
        accessLogParser.processLogs(
                applicationArguments.getStartDate(),
                applicationArguments.getDuration(),
                applicationArguments.getThreshold());
    }
}