package com.henry.common.utils.bytes;

import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
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
}
