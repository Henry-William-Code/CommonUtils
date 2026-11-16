package com.henry.common.utils.codec;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class UrlCodecUtilTest {

    @Test
    public void encode_usesFormEncodingAndKeepsEmptyBlank() {
        assertEquals("", UrlCodecUtil.encode(null));
        assertEquals("", UrlCodecUtil.encode(""));
        assertEquals("+", UrlCodecUtil.encode(" "));
        assertEquals("a+b", UrlCodecUtil.encode("a b"));
        assertEquals("%E4%BD%A0", UrlCodecUtil.encode("你"));
    }

    @Test
    public void decode_reversesEncodeAndTurnsPlusIntoSpace() {
        assertEquals("", UrlCodecUtil.decode(null));
        assertEquals("", UrlCodecUtil.decode(""));
        assertEquals(" ", UrlCodecUtil.decode("+"));
        assertEquals("a b", UrlCodecUtil.decode("a+b"));
        assertEquals("你", UrlCodecUtil.decode("%E4%BD%A0"));
        assertEquals("a b 你", UrlCodecUtil.decode(UrlCodecUtil.encode("a b 你")));
        assertEquals("a/b", UrlCodecUtil.decode(UrlCodecUtil.encode("a/b")));
    }

    @Test
    public void decode_rejectsIllegalPercentSequences() {
        assertRejected("%");
        assertRejected("%A");
        assertRejected("%GG");
        assertRejected("a%");
        assertRejected("%E4%");
    }

    private static void assertRejected(String text) {
        try {
            UrlCodecUtil.decode(text);
            fail("expected IllegalArgumentException for " + text);
        } catch (IllegalArgumentException expected) {
            // 不完整或非法的百分号序列应整段拒绝。
        }
    }
}
