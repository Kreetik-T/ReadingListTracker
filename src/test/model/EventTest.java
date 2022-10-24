package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;


// Citation for this code:
//
// ***************************************************************************************
// *    Title:  AlarmSystem/src/test/ca/ubc/cpsc210/alarm/test/EventTest.java
// *    Author: Paul Carter
// *    Date: 21th October 2021
// *    Availability: https://github.students.cs.ubc.ca/CPSC210/AlarmSystem
// ***************************************************************************************/

/**
 * Unit tests for the Event class
 */
public class EventTest {
    private Event e;
    private Event testEvent;
    private Date d;

    //NOTE: these tests might fail if time at which line (2) below is executed
    //is different from time that line (1) is executed.  Lines (1) and (2) must
    //run in same millisecond for this test to make sense and pass.

    @BeforeEach
    public void runBefore() {
        d = Calendar.getInstance().getTime();
        e = new Event("Sensor open at door");
        testEvent = new Event("Sensor open at door");
    }

    @Test
    public void testEvent() {
        assertEquals("Sensor open at door", e.getDescription());
        assertEquals(d.toString(),e.getDate().toString());
    }

    @Test
    public void testToString() {
        assertEquals(d.toString() + "\n" + "Sensor open at door", e.toString());
    }

    @Test
    public void testEquals() {
        assertTrue(e.equals(testEvent));

        testEvent = new Event("*not the same event*");
        assertFalse(e.equals(testEvent));

        Event notAnEvent = null;
        assertFalse(e.equals(notAnEvent));

        String aString = "";
        assertFalse(e.equals(aString));
    }

    @Test
    public void testHashCode() {
        int testHash = 13 * e.getDate().hashCode() + "Sensor open at door".hashCode();
        assertEquals(testHash,e.hashCode());
    }
}

