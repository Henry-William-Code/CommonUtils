package com.henry.common.utils.version;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class VersionUtilTest {

    @Test
    public void compare_numericSegmentsAndMissingZeros() {
        assertEquals(0, VersionUtil.compare("1", "1.0"));
        assertEquals(0, VersionUtil.compare("1.0", "1.0.0"));
        assertEquals(0, VersionUtil.compare("01.02", "1.2"));
        assertTrue(VersionUtil.compare("1.0.10", "1.0.2") > 0);
        assertTrue(VersionUtil.compare("1.0.2", "1.0.10") < 0);
        assertTrue(VersionUtil.compare("1.2", "1.10") < 0);
        assertTrue(VersionUtil.compare("2", "1.9.9") > 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void compare_rejectsNull() {
        VersionUtil.compare(null, "1");
    }

    @Test(expected = IllegalArgumentException.class)
    public void compare_rejectsBlank() {
        VersionUtil.compare("1", " \t");
    }

    @Test(expected = IllegalArgumentException.class)
    public void compare_rejectsEmptySegment() {
        VersionUtil.compare("1.", "1");
    }

    @Test(expected = IllegalArgumentException.class)
    public void compare_rejectsNonDigits() {
        VersionUtil.compare("1.0.0-beta", "1");
    }

    @Test(expected = IllegalArgumentException.class)
    public void compare_rejectsLeadingSpace() {
        VersionUtil.compare(" 1", "1");
    }

    @Test(expected = IllegalArgumentException.class)
    public void compare_rejectsSegmentOverflow() {
        VersionUtil.compare("9223372036854775808", "1");
    }
}
