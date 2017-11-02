package com.ef.models;

import joptsimple.OptionParser;
import joptsimple.OptionSet;

import java.net.URI;
import java.sql.Timestamp;
import java.util.Calendar;

import static joptsimple.util.DateConverter.datePattern;
import static joptsimple.util.RegexMatcher.regex;

public class ApplicationArguments {

    private String accessLog;
    private Timestamp startDate;
    private Duration duration;
    private int threshold;

    public ApplicationArguments(String[] args) throws Exception {
        OptionParser parser = new OptionParser();
        parser.accepts("accesslog")
                .withOptionalArg()
                .ofType(String.class)
                .defaultsTo("/access.log");
        parser.accepts( "startDate" )
                .withRequiredArg()
                .withValuesConvertedBy( datePattern( "yyyy-MM-dd.HH:mm:ss" ))
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

        OptionSet options = null;
        // TODO: used only for local debugging
        args = new String[]{"--startDate=2017-01-01.13:00:00", "--duration=hourly", "--threshold=100"};
//        args = new String[]{"--startDate=2017-01-01.13:00:00", "--duration=daily", "--threshold=250"};
//        args = new String[]{"--startDate=2017-01-01.15:00:00", "--duration=hourly", "--threshold=200"};
//        args = new String[]{"--startDate=2017-01-01.00:00:00", "--duration=daily", "--threshold=500"};

        if (args.length == 3 || args.length == 4){
            options = parser.parse(args);
        } else {
            throw new Exception("Valid program arguments are --accessLog=/path/to/file.log " +
                    "--startDate=yyyy-MM-dd.HH:mm:ss --duration=hourly|monthly --threshold=integer");
        }

        // startDate
        java.util.Date sd = (java.util.Date) options.valueOf("startDate");
        Calendar cal = Calendar.getInstance();
        cal.setTime(sd);
        cal.set(Calendar.MILLISECOND, 0);
        startDate = new java.sql.Timestamp(sd.getTime());

        duration = Duration.valueOf(options.valueOf("duration").toString().toUpperCase());
        threshold = (Integer) options.valueOf("threshold");

        accessLog = options.has("accessLog") ? options.valueOf("accesslog").toString() : "/access.log";
        URI uri = this.getClass().getResource(accessLog).toURI();
        accessLog = uri.getPath();
    }


    public String getAccessLog() {
        return accessLog;
    }

    public Timestamp getStartDate() {
        return startDate;
    }

    public Duration getDuration() {
        return duration;
    }

    public int getThreshold() {
        return threshold;
    }

}
