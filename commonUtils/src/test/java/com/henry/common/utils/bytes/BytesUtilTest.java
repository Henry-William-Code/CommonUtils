package com.henry.common.utils.bytes;

import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class BytesUtilTest {

    @Test
    public void isEmpty_nullAndZeroLength() {
        assertTrue(BytesUtil.isEmpty(null));
        assertTrue(BytesUtil.isEmpty(new byte[0]));
        assertFalse(BytesUtil.isEmpty(new byte[] {0}));
    }

    @Test
    public void concat_treatsNullAsEmptyAndDoesNotMutateInputs() {
        assertArrayEquals(new byte[0], BytesUtil.concat(null, null));
        byte[] left = new byte[] {1, 2};
        byte[] right = new byte[] {3};
        byte[] joined = BytesUtil.concat(left, right);
        assertArrayEquals(new byte[] {1, 2, 3}, joined);
        assertArrayEquals(new byte[] {1, 2}, left);
        assertArrayEquals(new byte[] {3}, right);
        joined[0] = 9;
        assertArrayEquals(new byte[] {1, 2}, left);
        assertArrayEquals(new byte[] {1}, BytesUtil.concat(new byte[] {1}, null));
        assertArrayEquals(new byte[] {2}, BytesUtil.concat(null, new byte[] {2}));
        assertArrayEquals(new byte[0], BytesUtil.concat(new byte[0], new byte[0]));
    }

    @Test
    public void indexOf_returnsFirstByteAndDoesNotMutate() {
        assertEquals(-1, BytesUtil.indexOf(null, (byte) 1));
        assertEquals(-1, BytesUtil.indexOf(new byte[0], (byte) 0));
        byte[] data = new byte[] {1, 2, 1};
        assertEquals(0, BytesUtil.indexOf(data, (byte) 1));
        assertEquals(1, BytesUtil.indexOf(data, (byte) 2));
        assertEquals(-1, BytesUtil.indexOf(data, (byte) 3));
        assertEquals(0, BytesUtil.indexOf(new byte[] {(byte) 0xff}, (byte) -1));
        assertArrayEquals(new byte[] {1, 2, 1}, data);
    }

    @Test
    public void lastIndexOf_returnsLastByteAndDoesNotMutate() {
        assertEquals(-1, BytesUtil.lastIndexOf(null, (byte) 1));
        assertEquals(-1, BytesUtil.lastIndexOf(new byte[0], (byte) 0));
        byte[] data = new byte[] {1, 2, 1};
        assertEquals(2, BytesUtil.lastIndexOf(data, (byte) 1));
        assertEquals(0, BytesUtil.indexOf(data, (byte) 1));
        assertEquals(-1, BytesUtil.lastIndexOf(data, (byte) 3));
        assertEquals(0, BytesUtil.lastIndexOf(new byte[] {(byte) 0xff}, (byte) -1));
        assertArrayEquals(new byte[] {1, 2, 1}, data);
    }
}
