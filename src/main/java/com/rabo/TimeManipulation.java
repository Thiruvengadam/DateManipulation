package com.rabo;

import com.rabo.exception.InvalidDateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.stream.Stream;

public class TimeManipulation {

    private static final Logger LOG = LoggerFactory.getLogger(TimeManipulation.class);
    private static LocalTime startingTime;
    private static LocalTime endingTime;

    public static void main(String argsp[]) throws InvalidDateException {
        TimeManipulation timeManipulation = new TimeManipulation();
        System.out.println(timeManipulation.count("16:15:00", "17:00:00"));
    }

    /**
     * Count the number of instances of similar digits between given time
     * @param startTime
     * @param endTime
     * @return
     */
    public int count(String startTime, String endTime) throws InvalidDateException {
        int count;
        try {
            //validate the time
            validateAndParseGivenTime(startTime, endTime);

            //get time between the given time in seconds and filter out the similar digits & count the occurrence
            count = (int)Stream.iterate(startingTime, time -> time.plusSeconds(1))
                                .limit(ChronoUnit.SECONDS.between(startingTime, endingTime))
                                .filter(time -> {
                                    String timeWithSeconds = time.getSecond() == 0 ?
                                            time.toString() + ":00" : time.toString();
                                    return timeWithSeconds.chars().distinct().count() == 3;//including the ':' character
                                })
                                //.peek(System.out::println) //uncomment to print the resulting times
                                .count();

        } catch (DateTimeParseException e) {
            throw new InvalidDateException("Unable to parse date. Please pass time in HH:MM:SS format"+e.getMessage());
        }
        LOG.info("Start time: {}", startTime);
        LOG.info("End time: {}", endTime);
        LOG.info("Count of similar digits that occurs between the given time is {}", count);

        return count;
    }

    private void validateAndParseGivenTime(String startTime, String endTime) throws InvalidDateException {
        if (isBlank(startTime) || isBlank(endTime)) {
            LOG.error("Please enter time in format(HH:MM:SS), Start time or End time is empty or null!!");
            throw new InvalidDateException("Please enter time in format(HH:MM:SS), Start time or End time is empty or null!!");
        }
        startingTime = LocalTime.parse(startTime, DateTimeFormatter.ofPattern("HH:mm:ss"));
        endingTime = LocalTime.parse(endTime);
        if(startingTime.isAfter(endingTime)) {
            LOG.error("Start time is after end time. Ensure start time is before end time");
            throw new InvalidDateException("Start time is after end time. Ensure start time is before end time");
        }
    }

    private static boolean isBlank(String time) {
        return time == null || time.isEmpty();
    }
}
