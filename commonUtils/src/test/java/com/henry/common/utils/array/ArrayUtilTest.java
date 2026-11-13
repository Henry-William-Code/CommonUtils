package com.henry.common.utils.array;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ArrayUtilTest {

    @Test
    public void isEmpty_intArray() {
        assertTrue(ArrayUtil.isEmpty(null));
        assertTrue(ArrayUtil.isEmpty(new int[0]));
        assertFalse(ArrayUtil.isEmpty(new int[] {0}));
    }

    @Test
    public void indexOfAndContains_objectArrayUsesNullSafeEquals() {
        assertEquals(-1, ArrayUtil.indexOf(null, "a"));
        assertFalse(ArrayUtil.contains(null, "a"));
        assertEquals(-1, ArrayUtil.indexOf(new Object[0], null));
        assertEquals(1, ArrayUtil.indexOf(new Object[] {null, "b", "b"}, "b"));
        assertTrue(ArrayUtil.contains(new Object[] {null, "b"}, null));
        assertFalse(ArrayUtil.contains(new String[] {"a"}, "A"));
        Object[] left = new Object[] {new String[] {"a"}};
        Object[] right = new Object[] {new String[] {"a"}};
        assertEquals(-1, ArrayUtil.indexOf(left, right[0]));
        assertEquals(0, ArrayUtil.indexOf(left, left[0]));
    }

    @Test
    public void indexOfAndContains_intArray() {
        assertEquals(-1, ArrayUtil.indexOf((int[]) null, 1));
        assertFalse(ArrayUtil.contains((int[]) null, 1));
        assertEquals(-1, ArrayUtil.indexOf(new int[0], 0));
        assertEquals(2, ArrayUtil.indexOf(new int[] {1, 1, 3}, 3));
        assertTrue(ArrayUtil.contains(new int[] {0, -1}, -1));
        assertFalse(ArrayUtil.contains(new int[] {1, 2}, 3));
    }
}
