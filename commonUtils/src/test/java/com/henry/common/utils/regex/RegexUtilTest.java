package com.henry.common.utils.regex;

import org.junit.Test;

import java.util.regex.PatternSyntaxException;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class RegexUtilTest {

    @Test
    public void matches_requiresWholeTextAndTreatsNullAsFalse() {
        assertFalse(RegexUtil.matches(null, "a"));
        assertFalse(RegexUtil.matches("a", null));
        assertFalse(RegexUtil.matches(null, null));
        assertTrue(RegexUtil.matches("", ""));
        assertFalse(RegexUtil.matches("a", ""));
        assertTrue(RegexUtil.matches("abc", "a.c"));
        assertFalse(RegexUtil.matches("abc", "a"));
        assertTrue(RegexUtil.matches("AbC", "(?i)abc"));
    }

    @Test
    public void findFirst_returnsFirstMatchOrNull() {
        assertNull(RegexUtil.findFirst(null, "a"));
        assertNull(RegexUtil.findFirst("a", null));
        assertNull(RegexUtil.findFirst("abc", "z"));
        assertEquals("12", RegexUtil.findFirst("a12b34", "\\d+"));
        assertEquals("", RegexUtil.findFirst("abc", ""));
    }

    @Test(expected = PatternSyntaxException.class)
    public void matches_rejectsIllegalPattern() {
        RegexUtil.matches("a", "(");
    }

    @Test(expected = PatternSyntaxException.class)
    public void findFirst_rejectsIllegalPattern() {
        RegexUtil.findFirst("a", "[");
    }

    @Test
    public void replaceFirst_replacesOnlyTheFirstMatch() {
        assertNull(RegexUtil.replaceFirst(null, "a", "b"));
        assertNull(RegexUtil.replaceFirst("a", null, "b"));
        assertNull(RegexUtil.replaceFirst("a", "a", null));
        assertEquals("abc", RegexUtil.replaceFirst("abc", "z", "x"));
        assertEquals("xbcabc", RegexUtil.replaceFirst("abcabc", "a", "x"));
        assertEquals("xab", RegexUtil.replaceFirst("ab", "", "x"));
        assertEquals("a1c", RegexUtil.replaceFirst("abc", "b", "1"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void replaceFirst_rejectsIllegalReplacement() {
        RegexUtil.replaceFirst("a", "a", "$");
    }

    @Test
    public void replaceAll_replacesEveryMatch() {
        assertNull(RegexUtil.replaceAll(null, "a", "b"));
        assertNull(RegexUtil.replaceAll("a", null, "b"));
        assertNull(RegexUtil.replaceAll("a", "a", null));
        assertEquals("abc", RegexUtil.replaceAll("abc", "z", "x"));
        assertEquals("xbcxbc", RegexUtil.replaceAll("abcabc", "a", "x"));
        assertEquals("xbcabc", RegexUtil.replaceFirst("abcabc", "a", "x"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void replaceAll_rejectsIllegalReplacement() {
        RegexUtil.replaceAll("a", "a", "$");
    }

    @Test
    public void split_keepsTrailingEmptyAndTreatsNullLikeOtherLookups() {
        assertNull(RegexUtil.split(null, ","));
        assertNull(RegexUtil.split("a", null));
        assertNull(RegexUtil.split(null, null));
        assertNull(RegexUtil.split(null, "["));
        assertArrayEquals(new String[] {""}, RegexUtil.split("", ","));
        assertArrayEquals(new String[] {""}, RegexUtil.split("", ""));
        assertArrayEquals(new String[] {"a", "", "b", ""}, RegexUtil.split("a,,b,", ","));
        assertArrayEquals(new String[] {"a", ""}, RegexUtil.split("a,", ","));
        assertArrayEquals(new String[] {"a"}, "a,".split(","));
        assertArrayEquals(new String[] {"", "a"}, RegexUtil.split(",a", ","));
        assertArrayEquals(new String[] {"", "", "", ""}, RegexUtil.split(",,,", ","));
        assertArrayEquals(new String[] {"abc"}, RegexUtil.split("abc", "z"));
        assertArrayEquals(new String[] {"a", "b", "c", ""}, RegexUtil.split("abc", ""));
        assertArrayEquals(new String[] {"", "a", ""}, RegexUtil.split("你a你", "你"));
        assertArrayEquals(new String[] {"", "", "", ""}, RegexUtil.split("aaa", "a"));
        assertArrayEquals("a1b2".split("\\d", -1), RegexUtil.split("a1b2", "\\d"));
        assertTrue(RegexUtil.matches("abc", "a.c"));
        assertEquals("", RegexUtil.findFirst("abc", ""));
        assertEquals("xbcxbc", RegexUtil.replaceAll("abcabc", "a", "x"));
    }

    @Test(expected = PatternSyntaxException.class)
    public void split_rejectsIllegalPattern() {
        RegexUtil.split("abc", "[");
    }
}
