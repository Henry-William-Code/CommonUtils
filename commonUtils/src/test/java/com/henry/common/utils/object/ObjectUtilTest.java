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
}
