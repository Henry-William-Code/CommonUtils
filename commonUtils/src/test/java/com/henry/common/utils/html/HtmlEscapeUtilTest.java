package com.henry.common.utils.html;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class HtmlEscapeUtilTest {

    @Test
    public void escape_replacesFiveCharactersAndKeepsOthers() {
        assertEquals("", HtmlEscapeUtil.escape(null));
        assertEquals("", HtmlEscapeUtil.escape(""));
        String plain = "中文";
        assertTrue(HtmlEscapeUtil.escape(plain) == plain);
        assertEquals("&amp;&lt;&gt;&quot;&#39;", HtmlEscapeUtil.escape("&<>\"'"));
        assertEquals("a&amp;b", HtmlEscapeUtil.escape("a&b"));
        assertEquals("&amp;amp;", HtmlEscapeUtil.escape("&amp;"));
    }

    @Test
    public void unescape_restoresEscapedEntitiesOnly() {
        assertEquals("", HtmlEscapeUtil.unescape(null));
        assertEquals("", HtmlEscapeUtil.unescape(""));
        String plain = "中文";
        assertTrue(HtmlEscapeUtil.unescape(plain) == plain);
        assertEquals("&<>\"'", HtmlEscapeUtil.unescape("&amp;&lt;&gt;&quot;&#39;"));
        assertEquals("'", HtmlEscapeUtil.unescape("&apos;"));
        assertEquals("&lt;", HtmlEscapeUtil.unescape("&amp;lt;"));
        assertEquals("&nbsp;", HtmlEscapeUtil.unescape("&nbsp;"));
        assertEquals("&#60;", HtmlEscapeUtil.unescape("&#60;"));
        assertEquals("a&", HtmlEscapeUtil.unescape("a&"));
        assertEquals("a&lt", HtmlEscapeUtil.unescape("a&lt"));
        assertEquals("<tag>", HtmlEscapeUtil.unescape("&lt;tag&gt;"));
    }

    @Test
    public void escapeThenUnescape_roundTripsTheFiveCharacters() {
        String original = "a&b<c>d\"e'f 中文";
        assertEquals(original, HtmlEscapeUtil.unescape(HtmlEscapeUtil.escape(original)));
    }
}