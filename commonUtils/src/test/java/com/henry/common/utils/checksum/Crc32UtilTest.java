package com.henry.common.utils.checksum;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class Crc32UtilTest {

    @Test
    public void checksum_nullAndEmptyAreZeroAndKnownVectorMatches() {
        assertEquals(0L, Crc32Util.checksum(null));
        assertEquals(0L, Crc32Util.checksum(new byte[0]));
        byte[] original = new byte[] {1, 2, 3};
        byte[] copy = Arrays.copyOf(original, original.length);
        long value = Crc32Util.checksum(original);
        assertEquals(copy[0], original[0]);
        assertEquals(0xCBF43926L, Crc32Util.checksum("123456789".getBytes(java.nio.charset.StandardCharsets.US_ASCII)));
        assertEquals(value, Crc32Util.checksum(new byte[] {1, 2, 3}));
    }

    @Test
    public void checksumUtf8_matchesBytesAndTreatsBlankTextAsZeroOnlyWhenEmpty() {
        assertEquals(0L, Crc32Util.checksumUtf8(null));
        assertEquals(0L, Crc32Util.checksumUtf8(""));
        assertEquals(Crc32Util.checksum("你".getBytes(java.nio.charset.StandardCharsets.UTF_8)),
                Crc32Util.checksumUtf8("你"));
        assertEquals(0xCBF43926L, Crc32Util.checksumUtf8("123456789"));
    }
}
