package com.henry.common.utils.time;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

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
}
