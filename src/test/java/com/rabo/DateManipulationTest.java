package com.rabo;

import com.rabo.exception.InvalidDateException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DateManipulationTest {

    private DateManipulation dateManipulation = new DateManipulation();

    @Test
    public void testInputStartTimeEndTimeAsEmpty() {
        String startDate = "";
        String endDate = "";
        assertThrows(InvalidDateException.class, () -> dateManipulation.count(startDate, endDate));
    }

    @Test
    public void testInputStartTimeEndTimeAsNull() {
        String startDate = null;
        String endDate = null;
        assertThrows(InvalidDateException.class, () -> dateManipulation.count(startDate, endDate));
    }

    @Test
    public void testInvalidStartTimeAndEndTime() {
        String startDate = "25:00:00";
        String endDate = "13:00:00";
        assertThrows(InvalidDateException.class, () -> dateManipulation.count(startDate, endDate));
    }

    @Test
    public void testStartTimeAfterEndTime() {
        String startDate = "18:00:00";
        String endDate = "17:00:00";
        assertThrows(InvalidDateException.class, () -> dateManipulation.count(startDate, endDate));
    }

    @Test
    public void testInvalidStartAndEndTime() {
        String startDate = "abc";
        String endDate = "abc";
        assertThrows(InvalidDateException.class, () -> dateManipulation.count(startDate, endDate));
    }

    @Test
    public void testForValidDateAndValidateCount() throws InvalidDateException {
        String startDate = "12:00:00";
        String endDate = "13:00:00";
        assertEquals(4, dateManipulation.count(startDate, endDate));
    }

    @Test
    public void testValidateCountForSimilarHours() throws InvalidDateException {
        String startDate = "11:00:00";
        String endDate = "12:00:00";
        assertEquals(1, dateManipulation.count(startDate, endDate));
        endDate = "14:00:00";
        assertEquals(9, dateManipulation.count(startDate, endDate));
    }

}
