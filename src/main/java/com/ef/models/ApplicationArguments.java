package com.ef.models;

import joptsimple.OptionParser;
import joptsimple.OptionSet;

import java.time.*;
import java.util.Date;

import static joptsimple.util.DateConverter.datePattern;
import static joptsimple.util.RegexMatcher.regex;

public class ApplicationArguments {

    private String accessLog;
    private Instant startDate;
    private Duration duration;
    private int threshold;
    private String dateFormatPattern = "yyyy-MM-dd.HH:mm:ss";

    public ApplicationArguments(String[] args) {

        // TODO: used only for local debugging
//        args = new String[]{"--startDate=2017-01-01.13:00:00", "--duration=hourly", "--threshold=100"};
//        args = new String[]{"--startDate=2017-01-01.13:00:00", "--duration=daily", "--threshold=250"};
//        args = new String[]{"--startDate=2017-01-01.15:00:00", "--duration=hourly", "--threshold=200"};
//        args = new String[]{"--startDate=2017-01-01.00:00:00", "--duration=daily", "--threshold=500"};

        if (args.length < 3 || args.length > 4){
            throw new IllegalArgumentException("Valid program arguments are --accessLog=/path/to/file.log " +
                    "--startDate=" + dateFormatPattern + " --duration=hourly|monthly --threshold=integer");
        }

        OptionParser parser = new OptionParser();
        parser.accepts("accessLog")
                .withOptionalArg()
                .ofType(String.class)
                .defaultsTo("/access.log");
        parser.accepts( "startDate" )
                .withRequiredArg()
                .ofType(String.class)
                .withValuesConvertedBy( datePattern(dateFormatPattern))
                .required();
        parser.accepts( "duration" )
                .withRequiredArg()
                .ofType(String.class)
                .withValuesConvertedBy(regex("(^daily$){1}|(^hourly$){1}"))
                .required();
        parser.accepts( "threshold" )
                .withRequiredArg()
                .ofType(Integer.class)
                .required();

        OptionSet options = parser.parse(args);

        startDate = ((Date)options.valueOf("startDate")).toInstant();
        duration = Duration.valueOf(options.valueOf("duration").toString().toUpperCase());
        threshold = (Integer) options.valueOf("threshold");
        accessLog = options.has("accessLog") ? options.valueOf("accessLog").toString() : null;
    }

    public String getAccessLog() {
        return accessLog;
    }

    public Instant getStartDate() {
        return startDate;
    }

    public Duration getDuration() {
        return duration;
    }

    public int getThreshold() {
        return threshold;
    }

    public boolean hasAccessLog(){
        return accessLog != null;
    }

}
