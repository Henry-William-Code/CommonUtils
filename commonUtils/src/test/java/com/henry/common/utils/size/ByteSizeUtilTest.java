package com.henry.common.utils.size;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ByteSizeUtilTest {

    @Test
    public void format_usesExactIntegerOrOneDecimal() {
        assertEquals("0 B", ByteSizeUtil.format(0L));
        assertEquals("1023 B", ByteSizeUtil.format(1023L));
        assertEquals("1 KB", ByteSizeUtil.format(1024L));
        assertEquals("1.5 KB", ByteSizeUtil.format(1536L));
        assertEquals("1537 B", ByteSizeUtil.format(1537L));
        assertEquals("1 MB", ByteSizeUtil.format(1048576L));
        assertEquals("-1.5 KB", ByteSizeUtil.format(-1536L));
        assertEquals("-1023 B", ByteSizeUtil.format(-1023L));
        assertEquals(Long.MAX_VALUE, ByteSizeUtil.parse(ByteSizeUtil.format(Long.MAX_VALUE)));
        assertEquals(-1L, ByteSizeUtil.parse(ByteSizeUtil.format(-1L)));
    }

    @Test
    public void parse_acceptsFormatOutputAndOptionalSpace() {
        assertEquals(0L, ByteSizeUtil.parse("0 B"));
        assertEquals(1536L, ByteSizeUtil.parse("1.5 KB"));
        assertEquals(1536L, ByteSizeUtil.parse("1.5KB"));
        assertEquals(1536L, ByteSizeUtil.parse("  +1.5 kb  "));
        assertEquals(-1536L, ByteSizeUtil.parse("-1.5 KB"));
        assertEquals(1536L, ByteSizeUtil.parse("1536"));
        assertEquals(1024L, ByteSizeUtil.parse("1.0 KB"));
        assertEquals(1048576L, ByteSizeUtil.parse("1 MB"));
        long[] samples = new long[] {0L, 1L, 1023L, 1024L, 1536L, 1537L, 1048576L, -1536L, Long.MAX_VALUE};
        for (int i = 0; i < samples.length; i++) {
            assertEquals(samples[i], ByteSizeUtil.parse(ByteSizeUtil.format(samples[i])));
        }
    }

    @Test(expected = ArithmeticException.class)
    public void format_rejectsLongMinValue() {
        ByteSizeUtil.format(Long.MIN_VALUE);
    }

    @Test(expected = IllegalArgumentException.class)
    public void parse_rejectsNull() {
        ByteSizeUtil.parse(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void parse_rejectsBareDecimal() {
        ByteSizeUtil.parse("1.5");
    }

    @Test(expected = IllegalArgumentException.class)
    public void parse_rejectsFractionalBytes() {
        ByteSizeUtil.parse("1.1 KB");
    }

    @Test(expected = IllegalArgumentException.class)
    public void parse_rejectsUnknownUnit() {
        ByteSizeUtil.parse("1 XB");
    }

    @Test(expected = IllegalArgumentException.class)
    public void parse_rejectsOverflow() {
        ByteSizeUtil.parse("8 EB");
    }
}
