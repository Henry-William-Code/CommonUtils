package com.henry.common.utils.convert;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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

    @Test
    public void parseBoolean_acceptsOnlyTrueAndFalse() {
        assertTrue(ConvertUtil.parseBoolean("true", false));
        assertTrue(ConvertUtil.parseBoolean(" TRUE ", false));
        assertFalse(ConvertUtil.parseBoolean("false", true));
        assertFalse(ConvertUtil.parseBoolean("FaLsE", true));
        assertTrue(ConvertUtil.parseBoolean(null, true));
        assertFalse(ConvertUtil.parseBoolean("", false));
        assertFalse(ConvertUtil.parseBoolean(" \t", false));
        assertTrue(ConvertUtil.parseBoolean("yes", true));
        assertFalse(ConvertUtil.parseBoolean("1", false));
        assertFalse(ConvertUtil.parseBoolean("0", false));
        assertTrue(ConvertUtil.parseBoolean("tr ue", true));
        assertTrue(ConvertUtil.parseBoolean("真", true));
    }

    @Test
    public void parseDouble_acceptsFiniteDecimalAndExponent() {
        assertEquals(12.5, ConvertUtil.parseDouble("12.5", -1), 0);
        assertEquals(1.25, ConvertUtil.parseDouble(" +1.25 ", -1), 0);
        assertEquals(-0.5, ConvertUtil.parseDouble("-.5", 9), 0);
        assertEquals(150, ConvertUtil.parseDouble("1.5e2", -1), 0);
        assertEquals(0.015, ConvertUtil.parseDouble("1.5E-2", -1), 0);
        assertEquals(0, ConvertUtil.parseDouble("1e-400", 9), 0);
    }

    @Test
    public void parseDouble_returnsDefaultForBlankIllegalAndNonFinite() {
        assertEquals(9, ConvertUtil.parseDouble(null, 9), 0);
        assertEquals(9, ConvertUtil.parseDouble("", 9), 0);
        assertEquals(9, ConvertUtil.parseDouble(" \t", 9), 0);
        assertEquals(9, ConvertUtil.parseDouble("1,5", 9), 0);
        assertEquals(9, ConvertUtil.parseDouble("1.2.3", 9), 0);
        assertEquals(9, ConvertUtil.parseDouble("0x1.0p1", 9), 0);
        assertEquals(9, ConvertUtil.parseDouble("NaN", 9), 0);
        assertEquals(9, ConvertUtil.parseDouble("+Infinity", 9), 0);
        assertEquals(9, ConvertUtil.parseDouble("-infinity", 9), 0);
        assertEquals(9, ConvertUtil.parseDouble("1e309", 9), 0);
        assertEquals(9, ConvertUtil.parseDouble("１.５", 9), 0);
        assertEquals(9, ConvertUtil.parseDouble("1 .5", 9), 0);
    }
}