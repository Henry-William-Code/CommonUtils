package com.henry.common.utils.csv;

import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class CsvUtilTest {

    @Test
    public void joinLine_quotesOnlySpecialFields() {
        assertEquals("", CsvUtil.joinLine());
        assertEquals("", CsvUtil.joinLine((String[]) null));
        assertEquals("", CsvUtil.joinLine(""));
        assertEquals(",", CsvUtil.joinLine(null, null));
        assertEquals("a,b", CsvUtil.joinLine("a", "b"));
        assertEquals(" a ,b", CsvUtil.joinLine(" a ", "b"));
        assertEquals("\"a,b\"", CsvUtil.joinLine("a,b"));
        assertEquals("\"say \"\"hi\"\"\"", CsvUtil.joinLine("say \"hi\""));
        assertEquals("\"a\nb\"", CsvUtil.joinLine("a\nb"));
        assertEquals("\"a\rb\"", CsvUtil.joinLine("a\rb"));
    }

    @Test
    public void splitLine_keepsSpacesAndRecordSeparators() {
        assertEquals(0, CsvUtil.splitLine(null).length);
        assertArrayEquals(new String[] {""}, CsvUtil.splitLine(""));
        assertArrayEquals(new String[] {""}, CsvUtil.splitLine("\n"));
        assertArrayEquals(new String[] {""}, CsvUtil.splitLine("\r\n"));
        assertArrayEquals(new String[] {"a", "b"}, CsvUtil.splitLine("a,b\r\n"));
        assertArrayEquals(new String[] {" a ", "b"}, CsvUtil.splitLine(" a ,b"));
        assertArrayEquals(new String[] {"a,b"}, CsvUtil.splitLine("\"a,b\""));
        assertArrayEquals(new String[] {"say \"hi\""}, CsvUtil.splitLine("\"say \"\"hi\"\"\""));
        assertArrayEquals(new String[] {"a\nb"}, CsvUtil.splitLine("\"a\nb\"\n"));
        assertArrayEquals(new String[] {"", ""}, CsvUtil.splitLine(","));
        assertArrayEquals(new String[] {"a", ""}, CsvUtil.splitLine("a,"));
    }

    @Test
    public void joinThenSplit_roundTripsNonNullFields() {
        String[] fields = new String[] {"a", "b,c", "say \"hi\"", "a\nb", " x "};
        assertArrayEquals(fields, CsvUtil.splitLine(CsvUtil.joinLine(fields)));
        assertEquals("", CsvUtil.joinLine(""));
        assertArrayEquals(new String[] {""}, CsvUtil.splitLine(CsvUtil.joinLine("")));
        assertArrayEquals(new String[] {""}, CsvUtil.splitLine(CsvUtil.joinLine()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void splitLine_rejectsUnclosedQuote() {
        CsvUtil.splitLine("\"abc");
    }

    @Test(expected = IllegalArgumentException.class)
    public void splitLine_rejectsQuoteInsideUnquotedField() {
        CsvUtil.splitLine("a\"b");
    }

    @Test(expected = IllegalArgumentException.class)
    public void splitLine_rejectsTextAfterClosingQuote() {
        CsvUtil.splitLine("\"ab\"c");
    }
}
