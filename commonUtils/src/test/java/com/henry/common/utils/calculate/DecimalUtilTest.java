package com.henry.common.utils.calculate;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

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
}
