package com.henry.common.utils.calculate;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class DecimalUtilTest {

    @Test
    public void toBinaryString_keepsThirtyTwoBitPadding() {
        assertEquals("00000000000000000000000000000001", DecimalUtil.toBinaryString(1));
        assertEquals("11111111111111111111111111111111", DecimalUtil.toBinaryString(-1));
    }

    @Test
    public void toHexString_padsUnsigned32Bits() {
        assertEquals("00000000", DecimalUtil.toHexString(0));
        assertEquals("0000000f", DecimalUtil.toHexString(15));
        assertEquals("000000ff", DecimalUtil.toHexString(255));
        assertEquals("00000abc", DecimalUtil.toHexString(0xABC));
        assertEquals("00000010", DecimalUtil.toHexString(0x10));
        assertEquals("7fffffff", DecimalUtil.toHexString(Integer.MAX_VALUE));
        assertEquals("80000000", DecimalUtil.toHexString(Integer.MIN_VALUE));
        assertEquals("ffffffff", DecimalUtil.toHexString(-1));
    }

    @Test
    public void parseHex_acceptsPrefixCaseAndUnsignedBits() {
        assertEquals(0, DecimalUtil.parseHex("0"));
        assertEquals(0, DecimalUtil.parseHex("0x0"));
        assertEquals(0, DecimalUtil.parseHex("0X00"));
        assertEquals(0xABC, DecimalUtil.parseHex("abc"));
        assertEquals(0xABC, DecimalUtil.parseHex("0xABC"));
        assertEquals(0xABC, DecimalUtil.parseHex("00000abc"));
        assertEquals(-1, DecimalUtil.parseHex("ffffffff"));
        assertEquals(Integer.MIN_VALUE, DecimalUtil.parseHex("80000000"));
        assertEquals(Integer.MAX_VALUE, DecimalUtil.parseHex("7fffffff"));
    }

    @Test
    public void parseHex_roundTripsToHexString() {
        int[] samples = new int[] {0, 1, 15, 0xABC, -1, Integer.MIN_VALUE, Integer.MAX_VALUE};
        for (int i = 0; i < samples.length; i++) {
            assertEquals(samples[i], DecimalUtil.parseHex(DecimalUtil.toHexString(samples[i])));
        }
    }

    @Test
    public void parseHex_rejectsBlankLengthAndInvalidDigits() {
        assertParseHexRejected(null);
        assertParseHexRejected("");
        assertParseHexRejected(" \t");
        assertParseHexRejected("0x");
        assertParseHexRejected("0X");
        assertParseHexRejected("000000000");
        assertParseHexRejected("0x000000000");
        assertParseHexRejected("g");
        assertParseHexRejected("0xgg");
        assertParseHexRejected("-1");
        assertParseHexRejected("+1");
        assertParseHexRejected(" ff");
        assertParseHexRejected("ff ");
    }

    private static void assertParseHexRejected(String hex) {
        try {
            DecimalUtil.parseHex(hex);
            fail("expected IllegalArgumentException for " + hex);
        } catch (IllegalArgumentException expected) {
            // 空白、长度和非法字符都应拒绝。
        }
    }
}
