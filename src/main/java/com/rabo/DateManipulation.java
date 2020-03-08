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

    /**
     * Count the number of instances of similar digits between given time
     * @param startTime
     * @param endTime
     * @return
     */
    public int count(String startTime, String endTime) throws InvalidDateException {
        LocalTime startingTime;
        LocalTime endingTime;
        int count;
        try {
            validateInputTime(startTime, endTime);
            startingTime = LocalTime.parse(startTime);
            endingTime = LocalTime.parse(endTime);
            isStartTimeAfterEndTime(startingTime, endingTime);
            //get time between the given time in seconds and filter out the similar digits & count the occurrence
            count = (int)Stream.iterate(startingTime, time -> time.plusSeconds(1))
                                .limit(ChronoUnit.SECONDS.between(startingTime, endingTime)+1)
                                .filter(timeWithSimilarDigits())
                                //.peek(System.out::println) //uncomment to print the resulting time
                                .count();
        } catch (DateTimeParseException e) {
            throw new InvalidDateException("Unable to parse date. Please enter in HH:MM:SS format"+e.getMessage());
        }
        LOG.info("Start time : " + startTime);
        LOG.info("End time: " + endTime);
        LOG.info("Count of similar digits that occurs between the given time is " + count);

        return count;
    }

    /**
     * Validate given time is blank or null
     * @param startTime
     * @param endTime
     * @throws InvalidDateException
     */
    private static void validateInputTime(String startTime, String endTime) throws InvalidDateException {
        if (isBlank(startTime) || isBlank(endTime)) {
            LOG.error("Please enter time in format(HH:MM:SS), Start time or End time is empty or null!!");
            throw new InvalidDateException("Please enter time in format(HH:MM:SS), Start time or End time is empty or null!!");
        }
    }

    /**
     * Validate if start time is after end time
     * @param startingTime
     * @param endingTime
     * @throws InvalidDateException
     */
    private void isStartTimeAfterEndTime(LocalTime startingTime, LocalTime endingTime) throws InvalidDateException {
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
            String hour = String.valueOf(givenTime.getHour() < 10 ? "0" + givenTime.getHour() : givenTime.getHour());
            Character firstChar = hour.charAt(0);
            Character secChar = hour.charAt(1);
            return containsChar(String.valueOf(givenTime.getMinute() < 10 ? "0" + givenTime.getMinute() : givenTime.getMinute()), firstChar, secChar)
                    && containsChar(String.valueOf(givenTime.getSecond() < 10 ? "0" + givenTime.getSecond() : givenTime.getSecond()), firstChar, secChar);
        };
    }


    private boolean containsChar(String time, Character firstChar, Character secChar) {
        if(firstChar.equals(secChar)) {
            if(time.contains(firstChar.toString()+secChar.toString())){
             return true;
            }
            return false;
        }
        return String.valueOf(time).contains(firstChar.toString())
                && String.valueOf(time).contains(secChar.toString());
    }

    /**
     * Validate for null and empty
     * @param time
     * @return
     */
    private static boolean isBlank(String time) {
        return time == null || time.isEmpty();
    }
}
