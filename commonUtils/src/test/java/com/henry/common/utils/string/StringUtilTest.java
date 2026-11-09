package com.henry.common.utils.string;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class StringUtilTest {

    @Test
    public void isEmpty_keepsPublishedBehavior() {
        assertTrue(StringUtil.isEmpty(null));
        assertTrue(StringUtil.isEmpty(""));
        assertFalse(StringUtil.isEmpty(" "));
        assertFalse(StringUtil.isEmpty("a"));
    }

    @Test
    public void isBlank_nullEmptyAndWhitespace() {
        assertTrue(StringUtil.isBlank(null));
        assertTrue(StringUtil.isBlank(""));
        assertTrue(StringUtil.isBlank(" "));
        assertTrue(StringUtil.isBlank(" \t\n\r\f"));
        assertTrue(StringUtil.isBlank("\u3000"));
        assertFalse(StringUtil.isBlank("\u00A0"));
        assertFalse(StringUtil.isBlank("a"));
        assertFalse(StringUtil.isBlank(" a "));
    }

    @Test
    public void equals_nullAndCase() {
        assertTrue(StringUtil.equals(null, null));
        assertFalse(StringUtil.equals(null, ""));
        assertFalse(StringUtil.equals("a", null));
        assertTrue(StringUtil.equals("", ""));
        assertTrue(StringUtil.equals("a", "a"));
        assertFalse(StringUtil.equals("a", "A"));
        assertFalse(StringUtil.equals("a", "a "));
        assertFalse(StringUtil.equals(" ", ""));
    }

    @Test
    public void repeat_boundaries() {
        assertEquals("", StringUtil.repeat(null, 3));
        assertEquals("", StringUtil.repeat("", 3));
        assertEquals("", StringUtil.repeat("ab", 0));
        assertEquals("", StringUtil.repeat("ab", -1));
        assertEquals("ab", StringUtil.repeat("ab", 1));
        assertEquals("ababab", StringUtil.repeat("ab", 3));
        assertEquals("你好你好", StringUtil.repeat("你好", 2));
    }

    @Test(expected = IllegalArgumentException.class)
    public void repeat_rejectsLengthOverflow() {
        StringUtil.repeat("aa", Integer.MAX_VALUE);
    }

    @Test
    public void join_nullDelimiterPartsAndElements() {
        assertEquals("", StringUtil.join(",", (String[]) null));
        assertEquals("", StringUtil.join(","));
        assertEquals("a", StringUtil.join(",", "a"));
        assertEquals("a,b,c", StringUtil.join(",", "a", "b", "c"));
        assertEquals("ab", StringUtil.join(null, "a", "b"));
        assertEquals("a,,b", StringUtil.join(",", "a", null, "b"));
        assertEquals(",", StringUtil.join(",", new String[]{null, null}));
        assertEquals("a-b", StringUtil.join(new StringBuilder("-"), "a", "b"));
    }
}
