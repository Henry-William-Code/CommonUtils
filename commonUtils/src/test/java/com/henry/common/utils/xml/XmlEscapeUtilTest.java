package com.henry.common.utils.xml;

import com.henry.common.utils.html.HtmlEscapeUtil;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class XmlEscapeUtilTest {

    @Test
    public void escape_replacesFiveXmlEntitiesAndKeepsOtherCharacters() {
        assertEquals("", XmlEscapeUtil.escape(null));
        assertEquals("", XmlEscapeUtil.escape(""));
        String plain = "中文😀";
        assertTrue(XmlEscapeUtil.escape(plain) == plain);
        assertEquals("&amp;&lt;&gt;&quot;&apos;", XmlEscapeUtil.escape("&<>\"'"));
        assertEquals("a&lt;b&gt;&amp;&quot;&apos;", XmlEscapeUtil.escape("a<b>&\"'"));
        assertEquals("&amp;lt;", XmlEscapeUtil.escape("&lt;"));
        assertEquals("&#39;", HtmlEscapeUtil.escape("'"));
        assertEquals("&apos;", XmlEscapeUtil.escape("'"));
    }

    @Test
    public void unescape_restoresNamedAndNumericEntities() {
        assertEquals("", XmlEscapeUtil.unescape(null));
        assertEquals("", XmlEscapeUtil.unescape(""));
        String plain = "中文";
        assertTrue(XmlEscapeUtil.unescape(plain) == plain);
        assertEquals("&<>\"'", XmlEscapeUtil.unescape("&amp;&lt;&gt;&quot;&apos;"));
        assertEquals("&lt;", XmlEscapeUtil.unescape("&amp;lt;"));
        assertEquals("&amp;", XmlEscapeUtil.unescape("&amp;amp;"));
        assertEquals("AA'", XmlEscapeUtil.unescape("&#65;&#x41;&#39;"));
        assertEquals("A", XmlEscapeUtil.unescape("&#00065;"));
        assertEquals("A", XmlEscapeUtil.unescape("&#x000041;"));
        assertEquals("J", XmlEscapeUtil.unescape("&#x4a;"));
        assertEquals("J", XmlEscapeUtil.unescape("&#x4A;"));
        assertEquals("\u0000", XmlEscapeUtil.unescape("&#0;"));
        assertEquals("\t", XmlEscapeUtil.unescape("&#x9;"));
        assertEquals("<A>", XmlEscapeUtil.unescape("&lt;&#65;&gt;"));
        String emoji = "\uD83D\uDE00";
        assertEquals(emoji, XmlEscapeUtil.unescape("&#x1F600;"));
        assertEquals(emoji, XmlEscapeUtil.unescape("&#128512;"));
        assertEquals("\uFFFE", XmlEscapeUtil.unescape("&#xFFFE;"));
        String max = XmlEscapeUtil.unescape("&#x10FFFF;");
        assertEquals(0x10FFFF, max.codePointAt(0));
        assertEquals(2, max.length());
        assertEquals("\uD7FF", XmlEscapeUtil.unescape("&#55295;"));
        assertEquals("\uE000", XmlEscapeUtil.unescape("&#57344;"));
        assertEquals("<", XmlEscapeUtil.unescape("&#60;"));
        assertEquals("&#60;", HtmlEscapeUtil.unescape("&#60;"));
        assertEquals("&nbsp;", HtmlEscapeUtil.unescape("&nbsp;"));
    }

    @Test
    public void escapeThenUnescape_roundTrips() {
        assertEquals("", XmlEscapeUtil.unescape(XmlEscapeUtil.escape(null)));
        String[] samples = new String[] {
                "",
                "plain",
                "中文",
                "a&b<c>d\"e'f",
                "already &lt;",
                "\uD83D\uDE00",
                "\u0000\n\t"
        };
        for (int i = 0; i < samples.length; i++) {
            assertEquals(samples[i], XmlEscapeUtil.unescape(XmlEscapeUtil.escape(samples[i])));
        }
    }

    @Test
    public void unescape_rejectsIncompleteUnknownAndIllegalCodePoints() {
        assertRejected("&");
        assertRejected("&amp");
        assertRejected("OK&amp");
        assertRejected("&;");
        assertRejected("&nbsp;");
        assertRejected("&AMP;");
        assertRejected("&Lt;");
        assertRejected("&#;");
        assertRejected("&#x;");
        assertRejected("&#xGG;");
        assertRejected("&#12a;");
        assertRejected("&#X41;");
        assertRejected("&#+65;");
        assertRejected("&#xD800;");
        assertRejected("&#xDFFF;");
        assertRejected("&#55296;");
        assertRejected("&#57343;");
        assertRejected("&#x110000;");
        assertRejected("&#1114112;");
        assertRejected("&#999999999999999999999;");
        assertRejected("&#65;&nbsp;");
    }

    private static void assertRejected(String text) {
        try {
            String result = XmlEscapeUtil.unescape(text);
            fail("expected IllegalArgumentException, returned " + result);
        } catch (IllegalArgumentException expected) {
            // 非法、不完整或未知实体应整段拒绝。
        }
    }
}
