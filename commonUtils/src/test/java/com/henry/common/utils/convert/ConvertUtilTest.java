package com.henry.common.utils.convert;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ConvertUtilTest {

    @Test
    public void parseInt_parsesTrimmedDecimalAndKeepsSign() {
        assertEquals(12, ConvertUtil.parseInt("12", -1));
        assertEquals(12, ConvertUtil.parseInt("  12\t", -1));
        assertEquals(12, ConvertUtil.parseInt("+12", -1));
        assertEquals(-12, ConvertUtil.parseInt("-12", 0));
        assertEquals(7, ConvertUtil.parseInt("007", -1));
        assertEquals(0, ConvertUtil.parseInt("-0", -1));
        assertEquals(Integer.MAX_VALUE, ConvertUtil.parseInt("2147483647", 0));
        assertEquals(Integer.MIN_VALUE, ConvertUtil.parseInt("-2147483648", 0));
    }

    @Test
    public void parseInt_returnsDefaultForBlankFormatAndOverflow() {
        assertEquals(9, ConvertUtil.parseInt(null, 9));
        assertEquals(9, ConvertUtil.parseInt("", 9));
        assertEquals(9, ConvertUtil.parseInt(" \t\n", 9));
        assertEquals(9, ConvertUtil.parseInt("\u3000", 9));
        assertEquals(9, ConvertUtil.parseInt("\u00A0", 9));
        assertEquals(9, ConvertUtil.parseInt("12.0", 9));
        assertEquals(9, ConvertUtil.parseInt("1 2", 9));
        assertEquals(9, ConvertUtil.parseInt("0x10", 9));
        assertEquals(9, ConvertUtil.parseInt("+", 9));
        assertEquals(9, ConvertUtil.parseInt("-", 9));
        assertEquals(9, ConvertUtil.parseInt("2147483648", 9));
        assertEquals(9, ConvertUtil.parseInt("-2147483649", 9));
        assertEquals(9, ConvertUtil.parseInt("１２", 9));
    }

    @Test
    public void parseLong_coversRangeAndSameFailures() {
        assertEquals(12L, ConvertUtil.parseLong("  +12 ", -1L));
        assertEquals(Long.MAX_VALUE, ConvertUtil.parseLong("9223372036854775807", 0L));
        assertEquals(Long.MIN_VALUE, ConvertUtil.parseLong("-9223372036854775808", 0L));
        assertEquals(3L, ConvertUtil.parseLong(null, 3L));
        assertEquals(3L, ConvertUtil.parseLong("  ", 3L));
        assertEquals(3L, ConvertUtil.parseLong("9223372036854775808", 3L));
        assertEquals(3L, ConvertUtil.parseLong("-9223372036854775809", 3L));
        assertEquals(3L, ConvertUtil.parseLong("12a", 3L));
        assertEquals(0L, ConvertUtil.parseLong("0", 3L));
    }
}