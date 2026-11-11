package com.henry.common.utils.time;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TimeUtilTest {

    @Test
    public void daysBetween_wholeDaysTruncateTowardZero() {
        assertEquals(0L, TimeUtil.daysBetween(0L, 0L));
        assertEquals(0L, TimeUtil.daysBetween(1000L, 1000L));
        assertEquals(0L, TimeUtil.daysBetween(0L, 86399999L));
        assertEquals(1L, TimeUtil.daysBetween(0L, 86400000L));
        assertEquals(2L, TimeUtil.daysBetween(0L, 86400000L * 2 + 10));
        assertEquals(0L, TimeUtil.daysBetween(0L, -1L));
        assertEquals(0L, TimeUtil.daysBetween(100L, 100L - 86399999L));
        assertEquals(-1L, TimeUtil.daysBetween(86400000L, 0L));
        assertEquals(-1L, TimeUtil.daysBetween(0L, -86400000L));
    }

    @Test(expected = ArithmeticException.class)
    public void daysBetween_rejectsLongOverflow() {
        TimeUtil.daysBetween(-1L, Long.MAX_VALUE);
    }

    @Test
    public void addDays_movesByExactUtcDayLength() {
        assertEquals(86400000L, TimeUtil.addDays(0L, 1));
        assertEquals(-86400000L, TimeUtil.addDays(0L, -1));
        assertEquals(1000L, TimeUtil.addDays(1000L, 0));
        assertEquals(0L, TimeUtil.addDays(86400000L, -1));
        assertEquals(Long.MAX_VALUE, TimeUtil.addDays(Long.MAX_VALUE, 0));
        assertEquals(Long.MIN_VALUE, TimeUtil.addDays(Long.MIN_VALUE, 0));
        assertEquals(0L, TimeUtil.addDays(Integer.MAX_VALUE * 86400000L, -Integer.MAX_VALUE));
    }

    @Test(expected = ArithmeticException.class)
    public void addDays_rejectsPositiveOverflow() {
        TimeUtil.addDays(Long.MAX_VALUE, 1);
    }

    @Test(expected = ArithmeticException.class)
    public void addDays_rejectsNegativeOverflow() {
        TimeUtil.addDays(Long.MIN_VALUE, -1);
    }

    @Test
    public void isSameDay_usesFlooredUtcDayNotLocalZone() {
        assertTrue(TimeUtil.isSameDay(0L, 0L));
        assertTrue(TimeUtil.isSameDay(0L, 86399999L));
        assertFalse(TimeUtil.isSameDay(0L, 86400000L));
        assertFalse(TimeUtil.isSameDay(-1L, 0L));
        assertTrue(TimeUtil.isSameDay(-1L, -86400000L));
        assertFalse(TimeUtil.isSameDay(-86400001L, -86400000L));
        assertTrue(TimeUtil.isSameDay(86400000L * 2, 86400000L * 2 + 86399999L));
        assertTrue(TimeUtil.isSameDay(Long.MIN_VALUE, Long.MIN_VALUE));
        assertTrue(TimeUtil.isSameDay(Long.MAX_VALUE, Long.MAX_VALUE));
    }

    @Test
    public void startOfUtcDay_usesSameFloorBoundaryAsIsSameDay() {
        assertEquals(0L, TimeUtil.startOfUtcDay(0L));
        assertEquals(0L, TimeUtil.startOfUtcDay(86399999L));
        assertEquals(86400000L, TimeUtil.startOfUtcDay(86400000L));
        assertEquals(-86400000L, TimeUtil.startOfUtcDay(-1L));
        assertEquals(-86400000L, TimeUtil.startOfUtcDay(-86400000L));
        assertEquals(-172800000L, TimeUtil.startOfUtcDay(-86400001L));
        assertUtcDayStart(Long.MAX_VALUE);
    }

    @Test(expected = ArithmeticException.class)
    public void startOfUtcDay_rejectsMinValueBecauseStartDoesNotFit() {
        TimeUtil.startOfUtcDay(Long.MIN_VALUE);
    }

    @Test
    public void endOfUtcDay_isLastMillisecondOfTheSameUtcDay() {
        assertEquals(86399999L, TimeUtil.endOfUtcDay(0L));
        assertEquals(86399999L, TimeUtil.endOfUtcDay(86399999L));
        assertEquals(172799999L, TimeUtil.endOfUtcDay(86400000L));
        assertEquals(-1L, TimeUtil.endOfUtcDay(-1L));
        assertEquals(-1L, TimeUtil.endOfUtcDay(-86400000L));
        assertEquals(-86400001L, TimeUtil.endOfUtcDay(-86400001L));
        long sample = 86400000L * 3 + 20L;
        long end = TimeUtil.endOfUtcDay(sample);
        assertTrue(end >= sample);
        assertTrue(TimeUtil.isSameDay(sample, end));
        assertEquals(TimeUtil.startOfUtcDay(sample), TimeUtil.startOfUtcDay(end));
        assertEquals(TimeUtil.startOfUtcDay(sample) + 86399999L, end);
    }

    @Test(expected = ArithmeticException.class)
    public void endOfUtcDay_rejectsMaxValueBecauseEndDoesNotFit() {
        TimeUtil.endOfUtcDay(Long.MAX_VALUE);
    }

    @Test(expected = ArithmeticException.class)
    public void endOfUtcDay_rejectsMinValueBecauseStartDoesNotFit() {
        TimeUtil.endOfUtcDay(Long.MIN_VALUE);
    }

    private static void assertUtcDayStart(long millis) {
        long start = TimeUtil.startOfUtcDay(millis);
        assertEquals(0L, start % 86400000L);
        assertTrue(start <= millis);
        assertTrue(TimeUtil.isSameDay(start, millis));
    }
}
