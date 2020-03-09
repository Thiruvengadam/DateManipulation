package com.rabo;

import com.rabo.exception.InvalidDateException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TimeManipulationTest {

    private TimeManipulation timeManipulation = new TimeManipulation();

    @Test
    public void testInputStartTimeEndTimeAsEmpty() {
        String startTime = "";
        String endTime = "";
        assertThrows(InvalidDateException.class, () -> timeManipulation.count(startTime, endTime));
    }

    @Test
    public void testInputStartTimeEndTimeAsNull() {
        String startTime = null;
        String endTime = null;
        assertThrows(InvalidDateException.class, () -> timeManipulation.count(startTime, endTime));
    }

    @Test
    public void testInvalidStartTimeAndEndTime() {
        String startTime = "25:00:00";
        String endTime = "13:00:00";
        assertThrows(InvalidDateException.class, () -> timeManipulation.count(startTime, endTime));
    }

    @Test
    public void testStartTimeAfterEndTime() {
        String startTime = "18:00:00";
        String endTime = "17:00:00";
        assertThrows(InvalidDateException.class, () -> timeManipulation.count(startTime, endTime));
    }

    @Test
    public void testInvalidStartAndEndTime() {
        String startTime = "abc";
        String endTime = "abc";
        assertThrows(InvalidDateException.class, () -> timeManipulation.count(startTime, endTime));
    }

    @Test
    public void testForValidDateAndValidateCount() throws InvalidDateException {
        String startTime = "16:15:00";
        String endTime = "17:00:00";
        assertEquals(2, timeManipulation.count(startTime, endTime));
    }

    @Test
    public void testValidateCountForSimilarHours() throws InvalidDateException {
        String startTime = "11:00:00";
        String endTime = "12:00:00";
        assertEquals(87, timeManipulation.count(startTime, endTime));
    }

    @Test
    public void testForForTimeBetweenDifferentMinutes() throws InvalidDateException {
        String startTime = "12:12:00";
        String endTime = "12:21:20";
        assertEquals(6, timeManipulation.count(startTime, endTime));
    }

    @Test
    public void testForForTimeBetweenDifferentSeconds() throws InvalidDateException {
        String startTime = "12:12:12";
        String endTime = "12:12:20";
        assertEquals(1, timeManipulation.count(startTime, endTime));
        endTime = "12:21:21";
        assertEquals(5, timeManipulation.count(startTime, endTime));
    }

}
