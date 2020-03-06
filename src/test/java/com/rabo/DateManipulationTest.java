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
        String startDate = "16:00:00";
        String endDate = "17:00:00";
        assertEquals(2, dateManipulation.count(startDate, endDate));
        endDate = "18:00:00";
        assertEquals(4, dateManipulation.count(startDate, endDate));
    }

}
