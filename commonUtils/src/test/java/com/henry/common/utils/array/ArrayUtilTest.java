package com.henry.common.utils.array;

import com.henry.common.utils.collection.CollectionUtil;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ArrayUtilTest {

    @Test
    public void isEmpty_intArray() {
        assertTrue(ArrayUtil.isEmpty((int[]) null));
        assertTrue(ArrayUtil.isEmpty(new int[0]));
        assertFalse(ArrayUtil.isEmpty(new int[] {0}));
    }

    @Test
    public void isEmpty_objectArray() {
        assertTrue(ArrayUtil.isEmpty((Object[]) null));
        assertTrue(ArrayUtil.isEmpty(new Object[0]));
        assertFalse(ArrayUtil.isEmpty(new Object[] {null}));
        assertFalse(ArrayUtil.isEmpty(new String[] {"a"}));
        assertTrue(CollectionUtil.isEmpty((Object[]) null));
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

    @Test
    public void lastIndexOf_returnsLastMatch() {
        assertEquals(-1, ArrayUtil.lastIndexOf(null, 1));
        assertEquals(-1, ArrayUtil.lastIndexOf(new int[0], 0));
        assertEquals(2, ArrayUtil.lastIndexOf(new int[] {1, 3, 1}, 1));
        assertEquals(0, ArrayUtil.indexOf(new int[] {1, 3, 1}, 1));
        assertEquals(-1, ArrayUtil.lastIndexOf(new int[] {2, 3}, 1));
    }

    @Test
    public void lastIndexOf_objectArrayReturnsLastMatch() {
        assertEquals(-1, ArrayUtil.lastIndexOf((Object[]) null, "a"));
        assertEquals(-1, ArrayUtil.lastIndexOf(new Object[0], null));
        assertEquals(2, ArrayUtil.lastIndexOf(new Object[] {"b", null, "b"}, "b"));
        assertEquals(1, ArrayUtil.lastIndexOf(new Object[] {"a", null, "b"}, null));
        assertEquals(-1, ArrayUtil.lastIndexOf(new String[] {"a"}, "A"));
        Object[] nested = new Object[] {new int[] {1}, new int[] {1}};
        assertEquals(0, ArrayUtil.lastIndexOf(nested, nested[0]));
        assertEquals(-1, ArrayUtil.lastIndexOf(nested, new int[] {1}));
    }

    @Test
    public void concat_intArrayTreatsNullAsEmptyAndDoesNotMutate() {
        assertEquals(0, ArrayUtil.concat((int[]) null, (int[]) null).length);
        int[] left = new int[] {1, 2};
        int[] right = new int[] {3};
        int[] joined = ArrayUtil.concat(left, right);
        assertEquals(3, joined.length);
        assertEquals(1, joined[0]);
        assertEquals(2, joined[1]);
        assertEquals(3, joined[2]);
        joined[0] = 9;
        assertEquals(1, left[0]);
        assertEquals(3, right[0]);
        assertEquals(1, ArrayUtil.concat(new int[] {1}, null)[0]);
        assertEquals(2, ArrayUtil.concat(null, new int[] {2})[0]);
    }

    @Test
    public void concat_objectArrayAlwaysReturnsObjectArray() {
        assertEquals(0, ArrayUtil.concat((Object[]) null, (Object[]) null).length);
        assertEquals(Object[].class, ArrayUtil.concat((Object[]) null, (Object[]) null).getClass());
        Object[] empty = new Object[0];
        Object[] joinedEmpty = ArrayUtil.concat(empty, null);
        assertTrue(joinedEmpty != empty);
        assertEquals(0, joinedEmpty.length);
        String[] left = new String[] {"a", null};
        Integer[] right = new Integer[] {1};
        Object[] joined = ArrayUtil.concat(left, right);
        assertEquals(Object[].class, joined.getClass());
        assertEquals("a", joined[0]);
        assertEquals(null, joined[1]);
        assertEquals(1, joined[2]);
        joined[0] = "changed";
        assertEquals("a", left[0]);
        assertEquals(Integer.valueOf(1), right[0]);
        assertEquals(3, ArrayUtil.concat(new int[] {1, 2}, new int[] {3}).length);
        assertEquals(int[].class, ArrayUtil.concat(new int[] {1}, null).getClass());
    }
}
