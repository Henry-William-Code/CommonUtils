package com.henry.common.utils.json;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class JsonEscapeUtilTest {

    @Test
    public void escape_quotesControlsAndLeavesSlash() {
        assertEquals("", JsonEscapeUtil.escape(null));
        assertEquals("", JsonEscapeUtil.escape(""));
        String plain = "中文/a";
        assertTrue(JsonEscapeUtil.escape(plain) == plain);
        assertEquals("a\\\"b\\\\c", JsonEscapeUtil.escape("a\"b\\c"));
        assertEquals("\\b\\f\\n\\r\\t", JsonEscapeUtil.escape("\b\f\n\r\t"));
        assertEquals("\\u0001\\u001f", JsonEscapeUtil.escape("\u0001\u001f"));
        assertEquals("a/b", JsonEscapeUtil.escape("a/b"));
    }

    @Test
    public void unescape_reversesEscapesAndAcceptsUnicode() {
        assertEquals("", JsonEscapeUtil.unescape(null));
        assertEquals("", JsonEscapeUtil.unescape(""));
        String plain = "中文";
        assertTrue(JsonEscapeUtil.unescape(plain) == plain);
        assertEquals("a\"b\\c", JsonEscapeUtil.unescape("a\\\"b\\\\c"));
        assertEquals("\b\f\n\r\t", JsonEscapeUtil.unescape("\\b\\f\\n\\r\\t"));
        assertEquals("/", JsonEscapeUtil.unescape("\\/"));
        assertEquals("A", JsonEscapeUtil.unescape("\\u0041"));
        assertEquals("J", JsonEscapeUtil.unescape("\\u004a"));
        assertEquals("J0", JsonEscapeUtil.unescape("\\u004A0"));
        assertEquals("\u0001", JsonEscapeUtil.unescape("\\u0001"));
    }

    @Test
    public void escapeThenUnescape_roundTrips() {
        String original = "a\"b\\c\b\f\n\r\t\u0001/中文";
        assertEquals(original, JsonEscapeUtil.unescape(JsonEscapeUtil.escape(original)));
    }

    @Test(expected = IllegalArgumentException.class)
    public void unescape_rejectsTrailingSlash() {
        JsonEscapeUtil.unescape("\\");
    }

    @Test(expected = IllegalArgumentException.class)
    public void unescape_rejectsUnknownEscape() {
        JsonEscapeUtil.unescape("\\a");
    }

    @Test(expected = IllegalArgumentException.class)
    public void unescape_rejectsIncompleteUnicode() {
        JsonEscapeUtil.unescape("\\u12");
    }

    @Test(expected = IllegalArgumentException.class)
    public void unescape_rejectsIllegalHex() {
        JsonEscapeUtil.unescape("\\u12GG");
    }
}
