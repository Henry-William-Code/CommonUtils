package com.henry.common.utils.hex;

import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class HexUtilTest {

    @Test
    public void encode_usesLowercasePairsAndTreatsEmptyAsBlank() {
        assertEquals("", HexUtil.encode(null));
        assertEquals("", HexUtil.encode(new byte[0]));
        assertEquals("00", HexUtil.encode(new byte[] {0}));
        assertEquals("0fff", HexUtil.encode(new byte[] {0x0f, (byte) 0xff}));
        assertEquals("80", HexUtil.encode(new byte[] {(byte) 0x80}));
    }

    @Test
    public void decode_acceptsBlankAndEitherCase() {
        assertArrayEquals(new byte[0], HexUtil.decode(null));
        assertArrayEquals(new byte[0], HexUtil.decode(""));
        assertArrayEquals(new byte[0], HexUtil.decode(" \t\n"));
        assertArrayEquals(new byte[] {(byte) 0xab}, HexUtil.decode("AB"));
        assertArrayEquals(new byte[] {(byte) 0xab}, HexUtil.decode("ab"));
        assertArrayEquals(new byte[] {0x0f, (byte) 0xff}, HexUtil.decode("0FfF"));
    }

    @Test
    public void decode_roundTripsEncode() {
        byte[] data = new byte[] {0, 1, 15, (byte) 0x80, (byte) 0xff};
        assertArrayEquals(data, HexUtil.decode(HexUtil.encode(data)));
        assertEquals("", HexUtil.encode(HexUtil.decode(null)));
    }

    @Test
    public void decode_rejectsOddLengthPrefixAndEmbeddedWhitespace() {
        assertRejected("a");
        assertRejected("abc");
        assertRejected("0x10");
        assertRejected("0Xab");
        assertRejected("ab ");
        assertRejected("a b");
        assertRejected("ab\n");
        assertRejected("gg");
        assertRejected("0g");
    }

    private static void assertRejected(String hex) {
        try {
            HexUtil.decode(hex);
            fail("expected IllegalArgumentException for " + hex);
        } catch (IllegalArgumentException expected) {
            // 奇数长度、前缀和非法字符都应拒绝。
        }
    }
}
