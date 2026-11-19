package com.henry.common.utils.object;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class ObjectUtilTest {

    @Test
    public void defaultIfNull_returnsValueOrDefault() {
        assertEquals("a", ObjectUtil.defaultIfNull("a", "b"));
        assertEquals("b", ObjectUtil.defaultIfNull(null, "b"));
        assertNull(ObjectUtil.defaultIfNull(null, null));
        assertEquals("", ObjectUtil.defaultIfNull("", "b"));
    }

    @Test
    public void equals_isNullSafeAndUsesObjectEquals() {
        assertTrue(ObjectUtil.equals(null, null));
        assertFalse(ObjectUtil.equals(null, "a"));
        assertFalse(ObjectUtil.equals("a", null));
        assertTrue(ObjectUtil.equals("a", new String("a")));
        assertFalse(ObjectUtil.equals("a", "A"));
        assertFalse(ObjectUtil.equals(new String[]{"a"}, new String[]{"a"}));
        String[] same = new String[]{"a"};
        assertTrue(ObjectUtil.equals(same, same));
    }

    @Test
    public void firstNonNull_skipsNullsAndAcceptsEmptyInput() {
        assertNull(ObjectUtil.firstNonNull());
        assertNull(ObjectUtil.firstNonNull((Object[]) null));
        assertNull(ObjectUtil.firstNonNull(null, null));
        assertEquals("b", ObjectUtil.firstNonNull(null, "b", "c"));
        assertEquals("a", ObjectUtil.firstNonNull("a", null));
        assertEquals("", ObjectUtil.firstNonNull("", "b"));
    }

    @Test
    public void toStringOrEmpty_returnsEmptyForNullAndDoesNotFormatArrayContents() {
        assertEquals("", ObjectUtil.toStringOrEmpty(null));
        assertEquals("12", ObjectUtil.toStringOrEmpty(Integer.valueOf(12)));
        assertEquals("a", ObjectUtil.toStringOrEmpty("a"));
        String text = ObjectUtil.toStringOrEmpty(new int[] {1, 2});
        assertTrue(text.startsWith("[I@"));
    }

    @Test
    public void hashCode_nullIsZeroAndUsesObjectHashCode() {
        assertEquals(0, ObjectUtil.hashCode(null));
        assertEquals("a".hashCode(), ObjectUtil.hashCode("a"));
        int[] data = new int[] {1, 2};
        assertEquals(data.hashCode(), ObjectUtil.hashCode(data));
    }

    @Test
    public void countNonNull_countsOnlyPresentValues() {
        assertEquals(0, ObjectUtil.countNonNull((Object[]) null));
        assertEquals(0, ObjectUtil.countNonNull());
        assertEquals(0, ObjectUtil.countNonNull(null, null));
        assertEquals(1, ObjectUtil.countNonNull(null, "a", null));
        assertEquals(2, ObjectUtil.countNonNull("a", Integer.valueOf(1)));
    }
}
