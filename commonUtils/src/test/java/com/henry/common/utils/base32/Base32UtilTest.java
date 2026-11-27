package com.henry.common.utils.base32;

import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.fail;

public class Base32UtilTest {

    @Test
    public void encode_usesRfc4648UppercaseAndPadding() {
        assertEquals("", Base32Util.encode(null));
        assertEquals("", Base32Util.encode(new byte[0]));
        assertEquals("MY======", Base32Util.encode(new byte[] {'f'}));
        assertEquals("MZXQ====", Base32Util.encode(new byte[] {'f', 'o'}));
        assertEquals("MZXW6===", Base32Util.encode(new byte[] {'f', 'o', 'o'}));
        assertEquals("MZXW6YQ=", Base32Util.encode(new byte[] {'f', 'o', 'o', 'b'}));
        assertEquals("MZXW6YTB", Base32Util.encode(new byte[] {'f', 'o', 'o', 'b', 'a'}));
        assertEquals("MZXW6YTBOI======", Base32Util.encode(new byte[] {'f', 'o', 'o', 'b', 'a', 'r'}));
    }

    @Test
    public void decode_isCaseInsensitiveAndReturnsANewEmptyArray() {
        assertArrayEquals(new byte[0], Base32Util.decode(null));
        assertArrayEquals(new byte[0], Base32Util.decode(""));
        assertArrayEquals(new byte[0], Base32Util.decode(" "));
        assertArrayEquals(new byte[0], Base32Util.decode("\t"));
        assertArrayEquals(new byte[0], Base32Util.decode("\n"));
        assertArrayEquals(new byte[0], Base32Util.decode("\r\n"));
        assertArrayEquals(new byte[0], Base32Util.decode(" \t\n "));
        assertNotSame(Base32Util.decode(null), Base32Util.decode(""));
        assertArrayEquals(new byte[] {'f'}, Base32Util.decode("my======"));
        assertArrayEquals(new byte[] {'f'}, Base32Util.decode("My======"));
        assertArrayEquals(new byte[] {'f', 'o', 'o', 'b', 'a', 'r'}, Base32Util.decode("mzxw6ytboi======"));
        assertArrayEquals(new byte[] {'f', 'o', 'o', 'b', 'a', 'r'}, Base32Util.decode("MZXW6YTBOI======"));
        assertArrayEquals(new byte[] {'f', 'o', 'o', 'b', 'a'}, Base32Util.decode("Mzxw6YTB"));
    }

    @Test
    public void decode_roundTripsEncodeForEveryLength() {
        byte[] data = new byte[40];
        for (int i = 0; i < data.length; i++) {
            data[i] = (byte) (i * 7 + 3);
        }
        data[1] = (byte) 0xff;
        data[2] = (byte) -1;
        for (int length = 0; length <= data.length; length++) {
            byte[] slice = new byte[length];
            System.arraycopy(data, 0, slice, 0, length);
            byte[] decoded = Base32Util.decode(Base32Util.encode(slice));
            assertArrayEquals(slice, decoded);
        }
        assertArrayEquals(new byte[0], Base32Util.decode(Base32Util.encode(null)));
    }

    @Test
    public void decode_rejectsWhitespaceAlphabetPaddingAndUnusedBits() {
        assertInvalid("MY");
        assertInvalid("MY=====");
        assertInvalid("MY=====A");
        assertInvalid("M=======");
        assertInvalid("========");
        assertInvalid("MY====== ");
        assertInvalid(" MY======");
        assertInvalid("M Y======");
        assertInvalid("MY= =====");
        assertInvalid("MY======\n");
        assertInvalid("\u00A0");
        assertInvalid("0");
        assertInvalid("1");
        assertInvalid("8");
        assertInvalid("9");
        assertInvalid("MZ======");
        assertInvalid("MZXW6Y=Q");
        assertInvalid("A");
        assertInvalid("MZXW6YTBOI=====");
        assertInvalid("AAAAAA==");
        assertInvalid("AAA=====");
        assertEquals("MY======", Base32Util.encode(new byte[] {'f'}));
    }

    private static void assertInvalid(String text) {
        try {
            Base32Util.decode(text);
            fail("expected IllegalArgumentException for " + text);
        } catch (IllegalArgumentException expected) {
            // 不返回截断结果。
        }
    }
}
