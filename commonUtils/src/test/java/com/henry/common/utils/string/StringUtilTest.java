package com.henry.common.utils.string;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
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
    public void equalsIgnoreCase_foldsAsciiOnlyAndDoesNotTrim() {
        assertTrue(StringUtil.equalsIgnoreCase(null, null));
        assertFalse(StringUtil.equalsIgnoreCase(null, "a"));
        assertFalse(StringUtil.equalsIgnoreCase("A", null));
        assertTrue(StringUtil.equalsIgnoreCase("AbC", "aBc"));
        assertFalse(StringUtil.equalsIgnoreCase("a", "a "));
        assertFalse(StringUtil.equalsIgnoreCase("a", "ab"));
        assertTrue(StringUtil.equals("A", "A"));
        assertFalse(StringUtil.equals("A", "a"));
    }

    @Test
    public void defaultIfBlank_usesIsBlankAndKeepsNonBlankText() {
        assertEquals("fallback", StringUtil.defaultIfBlank(null, "fallback"));
        assertEquals("fallback", StringUtil.defaultIfBlank("", "fallback"));
        assertEquals("fallback", StringUtil.defaultIfBlank(" \t", "fallback"));
        assertNull(StringUtil.defaultIfBlank(" ", null));
        assertEquals(" a ", StringUtil.defaultIfBlank(" a ", "fallback"));
        assertEquals("\u00A0", StringUtil.defaultIfBlank("\u00A0", "fallback"));
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

    @Test
    public void trimToEmpty_nullAndWhitespaceEdges() {
        assertEquals("", StringUtil.trimToEmpty(null));
        assertEquals("", StringUtil.trimToEmpty(""));
        assertEquals("", StringUtil.trimToEmpty(" \t\n\r\f"));
        assertEquals("", StringUtil.trimToEmpty("\u3000"));
        assertEquals("\u00A0", StringUtil.trimToEmpty("\u00A0"));
        assertEquals("a b", StringUtil.trimToEmpty(" \u3000a b\t"));
        String exact = "a";
        assertTrue(StringUtil.trimToEmpty(exact) == exact);
    }

    @Test
    public void startsWith_nullAndEmptyPrefix() {
        assertFalse(StringUtil.startsWith(null, "a"));
        assertFalse(StringUtil.startsWith("a", null));
        assertFalse(StringUtil.startsWith(null, null));
        assertTrue(StringUtil.startsWith("", ""));
        assertTrue(StringUtil.startsWith("abc", ""));
        assertTrue(StringUtil.startsWith("abc", "ab"));
        assertFalse(StringUtil.startsWith("abc", "ab "));
        assertFalse(StringUtil.startsWith(" abc", "ab"));
        assertFalse(StringUtil.startsWith("Abc", "ab"));
    }

    @Test
    public void endsWith_nullAndEmptySuffix() {
        assertFalse(StringUtil.endsWith(null, "a"));
        assertFalse(StringUtil.endsWith("a", null));
        assertFalse(StringUtil.endsWith(null, null));
        assertTrue(StringUtil.endsWith("", ""));
        assertTrue(StringUtil.endsWith("abc", ""));
        assertTrue(StringUtil.endsWith("abc", "bc"));
        assertFalse(StringUtil.endsWith("abc", " bc"));
        assertFalse(StringUtil.endsWith("abc ", "bc"));
        assertFalse(StringUtil.endsWith("abC", "bc"));
    }

    @Test
    public void contains_nullAndEmptySearch() {
        assertFalse(StringUtil.contains(null, "a"));
        assertFalse(StringUtil.contains("a", null));
        assertFalse(StringUtil.contains(null, null));
        assertTrue(StringUtil.contains("", ""));
        assertTrue(StringUtil.contains("abc", ""));
        assertTrue(StringUtil.contains("abc", "b"));
        assertFalse(StringUtil.contains("abc", "B"));
        assertFalse(StringUtil.contains("abc", " b"));
        assertFalse(StringUtil.contains("ab c", "bc"));
    }

    @Test
    public void containsIgnoreCase_onlyFoldsAsciiAndDoesNotTrim() {
        assertFalse(StringUtil.containsIgnoreCase(null, "a"));
        assertFalse(StringUtil.containsIgnoreCase("a", null));
        assertFalse(StringUtil.containsIgnoreCase(null, null));
        assertFalse(StringUtil.containsIgnoreCase(null, ""));
        assertTrue(StringUtil.containsIgnoreCase("", ""));
        assertTrue(StringUtil.containsIgnoreCase("abc", ""));
        assertTrue(StringUtil.containsIgnoreCase("AbC", "bc"));
        assertTrue(StringUtil.containsIgnoreCase("abc", "B"));
        assertTrue(StringUtil.containsIgnoreCase("aBcD", "Bc"));
        assertFalse(StringUtil.containsIgnoreCase("aBcD", "BD"));
        assertFalse(StringUtil.containsIgnoreCase("abc", " b"));
        assertFalse(StringUtil.containsIgnoreCase("a", " A"));
        assertTrue(StringUtil.containsIgnoreCase(" abc", "ABC"));
        assertFalse(StringUtil.containsIgnoreCase("abc", "ABCD"));
        assertFalse(StringUtil.containsIgnoreCase("İ", "i"));
        assertFalse(StringUtil.containsIgnoreCase("i", "İ"));
        assertFalse(StringUtil.containsIgnoreCase("Ω", "ω"));
        assertFalse(StringUtil.containsIgnoreCase("ß", "ss"));
        assertFalse(StringUtil.contains("abc", "B"));
    }

    @Test
    public void startsWithIgnoreCase_onlyFoldsAsciiAndDoesNotTrim() {
        assertFalse(StringUtil.startsWithIgnoreCase(null, "a"));
        assertFalse(StringUtil.startsWithIgnoreCase("a", null));
        assertFalse(StringUtil.startsWithIgnoreCase(null, ""));
        assertTrue(StringUtil.startsWithIgnoreCase("", ""));
        assertTrue(StringUtil.startsWithIgnoreCase("abc", ""));
        assertTrue(StringUtil.startsWithIgnoreCase("AbC", "ab"));
        assertTrue(StringUtil.startsWithIgnoreCase("abc", "ABC"));
        assertFalse(StringUtil.startsWithIgnoreCase("abc", "ab "));
        assertFalse(StringUtil.startsWithIgnoreCase(" abc", "ab"));
        assertFalse(StringUtil.startsWithIgnoreCase("abc", "abcd"));
        assertFalse(StringUtil.startsWithIgnoreCase("İstanbul", "i"));
        assertFalse(StringUtil.startsWith("Abc", "ab"));
    }
}
