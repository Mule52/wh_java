package com.ef.models;


import org.junit.Test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

import static org.junit.Assert.assertEquals;

public class ApplicationArgumentsTests {

    String[] args;

    @Test
    public void validArgumentsShouldAllBeAccessible(){
        args = new String[]{"--accessLog=/path/to/file.log", "--startDate=2017-01-01.13:00:00", "--duration=hourly", "--threshold=100"};

        String expectedAccessLog = "/path/to/file.log";

        String testDate = "2017-01-01.13:00:00";
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd.HH:mm:ss");
        Date date = new Date();
        try {
            date = formatter.parse(testDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Instant expectedStartDate = date.toInstant();
        Duration expectedDuration = Duration.HOURLY;
        int expectedThreshold = 100;
        try {
            ApplicationArguments cut = new ApplicationArguments(args);
            assertEquals(expectedAccessLog, cut.getAccessLog());
            assertEquals(expectedStartDate, cut.getStartDate());
            assertEquals(expectedDuration, cut.getDuration());
            assertEquals(expectedThreshold, cut.getThreshold());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test(expected = Exception.class)
    public void invalidStartDatePatternShouldThrowException() {
        args = new String[]{"--startDate=2017-01-01 13:00:00", "--duration=hourly", "--threshold=100"};
        new ApplicationArguments(args);
    }

    @Test(expected = Exception.class)
    public void invalidDurationValueShouldThrowException() {
        args = new String[]{"--startDate=2017-01-01.13:00:00", "--duration=monthly", "--threshold=100"};
        new ApplicationArguments(args);
    }

    @Test(expected = Exception.class)
    public void invalidThresholdValueShouldThrowException() {
        args = new String[]{"--startDate=2017-01-01.13:00:00", "--duration=hourly", "--threshold=test"};
        new ApplicationArguments(args);
    }

    @Test(expected = Exception.class)
    public void invalidAccessLogArgumentShouldThrowException() {
        args = new String[]{"--accessLogd=/path/to/file.log", "--startDate=2017-01-01.13:00:00", "--duration=hourly", "--threshold=test"};
        new ApplicationArguments(args);
    }

    @Test(expected = Exception.class)
    public void unexpectedArgumentShouldThrowException() {
        args = new String[]{"--startDate=2017-01-01.13:00:00", "--duration=hourly", "--threshold=test"};
        new ApplicationArguments(args);
    }

    @Test(expected = IllegalArgumentException.class)
    public void unexpectedArgumentShouldThrowIllegalArgumentException() {
        args = new String[]{"--accessLog=/path/to/file.log", "--startDate=2017-01-01.13:00:00", "--duration=hourly", "--threshold=test", "--unexpected=arg"};
        new ApplicationArguments(args);
    }
}
