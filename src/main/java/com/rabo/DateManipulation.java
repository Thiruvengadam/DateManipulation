package com.rabo;

import com.rabo.exception.InvalidDateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class DateManipulation {

    private static final Logger LOG = LoggerFactory.getLogger(DateManipulation.class);
    private static LocalTime startingTime;
    private static LocalTime endingTime;

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
                                .limit(ChronoUnit.SECONDS.between(startingTime, endingTime)+1)
                                .filter(timeWithSimilarDigits())
                                //.peek(System.out::println) //uncomment to print the resulting times
                                .count();

        } catch (DateTimeParseException e) {
            throw new InvalidDateException("Unable to parse date. Please pass time in HH:MM:SS format"+e.getMessage());
        }
        LOG.info("Start time: " + startTime);
        LOG.info("End time: " + endTime);
        LOG.info("Count of similar digits that occurs between the given time is " + count);

        return count;
    }

    private void validateAndParseGivenTime(String startTime, String endTime) throws InvalidDateException {
        if (isBlank(startTime) || isBlank(endTime)) {
            LOG.error("Please enter time in format(HH:MM:SS), Start time or End time is empty or null!!");
            throw new InvalidDateException("Please enter time in format(HH:MM:SS), Start time or End time is empty or null!!");
        }
        startingTime = LocalTime.parse(startTime);
        endingTime = LocalTime.parse(endTime);
        if(startingTime.isAfter(endingTime)) {
            LOG.error("Start time is after end time. Ensure start time is before end time");
            throw new InvalidDateException("Start time is after end time. Ensure start time is before end time");
        }
    }

    /**
     * Filter matching digits of hour and minutes
     *        ex: 12:12:12 && 12:12:00
     * @return
     */
    private Predicate<LocalTime> timeWithSimilarDigits() {
        return givenTime -> {
            String hour = addZeroBefore(givenTime.getHour());
            String firstDigit = String.valueOf(hour.charAt(0));
            String secondDigit = String.valueOf(hour.charAt(1));
            return containsChar(addZeroBefore(givenTime.getMinute()), firstDigit, secondDigit)
                    && containsChar(addZeroBefore(givenTime.getSecond()), firstDigit, secondDigit);
        };
    }

    private String addZeroBefore(int time) {
        return (time < 10 ? "0" : "") + time;
    }

    private boolean containsChar(String time, String firstChar, String secChar) {
        if(firstChar.equals(secChar)) {
            if(time.contains(firstChar+secChar)){
             return true;
            }
            return false;
        }
        return time.contains(firstChar) && time.contains(secChar);
    }

    private static boolean isBlank(String time) {
        return time == null || time.isEmpty();
    }
}
