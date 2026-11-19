package com.henry.common.utils.regex;

import org.junit.Test;

import java.util.regex.PatternSyntaxException;

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
}
