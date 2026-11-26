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

    @Test
    public void contains_usesByteEqualityAndDoesNotChangeIndexOf() {
        assertFalse(BytesUtil.contains(null, (byte) 1));
        assertFalse(BytesUtil.contains(new byte[0], (byte) 0));
        byte[] data = new byte[] {1, 2, 1};
        assertTrue(BytesUtil.contains(data, (byte) 2));
        assertFalse(BytesUtil.contains(data, (byte) 3));
        assertTrue(BytesUtil.contains(new byte[] {(byte) 0xff}, (byte) -1));
        assertTrue(BytesUtil.contains(new byte[] {(byte) -1}, (byte) 0xff));
        assertEquals(1, BytesUtil.indexOf(data, (byte) 2));
        assertEquals(2, BytesUtil.lastIndexOf(data, (byte) 1));
        assertArrayEquals(new byte[] {1, 2, 1}, data);
    }

    @Test
    public void startsWith_nullEmptyAndByteEquality() {
        assertFalse(BytesUtil.startsWith(null, new byte[] {1}));
        assertFalse(BytesUtil.startsWith(new byte[] {1}, null));
        assertFalse(BytesUtil.startsWith(null, null));
        assertTrue(BytesUtil.startsWith(new byte[0], new byte[0]));
        assertTrue(BytesUtil.startsWith(new byte[] {1}, new byte[0]));
        byte[] data = new byte[] {1, 2, 3};
        assertTrue(BytesUtil.startsWith(data, new byte[] {1, 2}));
        assertFalse(BytesUtil.startsWith(data, new byte[] {1, 3}));
        assertFalse(BytesUtil.startsWith(data, new byte[] {1, 2, 3, 4}));
        assertTrue(BytesUtil.startsWith(new byte[] {(byte) 0xff}, new byte[] {(byte) -1}));
        assertArrayEquals(new byte[] {1, 2, 3}, data);
    }

    @Test
    public void endsWith_nullEmptyAndByteEquality() {
        assertFalse(BytesUtil.endsWith(null, new byte[] {1}));
        assertFalse(BytesUtil.endsWith(new byte[] {1}, null));
        assertFalse(BytesUtil.endsWith(null, null));
        assertTrue(BytesUtil.endsWith(new byte[0], new byte[0]));
        assertTrue(BytesUtil.endsWith(new byte[] {1}, new byte[0]));
        byte[] data = new byte[] {1, 2, 3};
        byte[] suffix = new byte[] {2, 3};
        assertTrue(BytesUtil.endsWith(data, suffix));
        assertFalse(BytesUtil.endsWith(data, new byte[] {1, 2}));
        assertFalse(BytesUtil.endsWith(data, new byte[] {1, 2, 3, 4}));
        assertTrue(BytesUtil.endsWith(new byte[] {(byte) 0xff}, new byte[] {(byte) -1}));
        assertTrue(BytesUtil.endsWith(new byte[] {(byte) -1}, new byte[] {(byte) 0xff}));
        suffix[0] = 9;
        assertArrayEquals(new byte[] {1, 2, 3}, data);
        assertTrue(BytesUtil.startsWith(data, new byte[] {1, 2}));
        assertFalse(BytesUtil.endsWith(data, new byte[] {9, 3}));
    }
}
