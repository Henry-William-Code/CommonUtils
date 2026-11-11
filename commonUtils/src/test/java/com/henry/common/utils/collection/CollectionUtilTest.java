package com.henry.common.utils.collection;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class CollectionUtilTest {

    @Test
    public void isEmpty_collection() {
        assertTrue(CollectionUtil.isEmpty((Collection<?>) null));
        assertTrue(CollectionUtil.isEmpty(Collections.emptyList()));
        assertFalse(CollectionUtil.isEmpty(Collections.singletonList("a")));
        assertFalse(CollectionUtil.isEmpty(Collections.singletonList(null)));
    }

    @Test
    public void isEmpty_map() {
        assertTrue(CollectionUtil.isEmpty((Map<?, ?>) null));
        assertTrue(CollectionUtil.isEmpty(Collections.emptyMap()));
        Map<String, String> data = new HashMap<String, String>();
        data.put("k", null);
        assertFalse(CollectionUtil.isEmpty(data));
    }

    @Test
    public void isEmpty_array() {
        assertTrue(CollectionUtil.isEmpty((Object[]) null));
        assertTrue(CollectionUtil.isEmpty(new Object[0]));
        assertFalse(CollectionUtil.isEmpty(new Object[]{null}));
        assertFalse(CollectionUtil.isEmpty(new String[]{"a"}));
    }

    @Test
    public void size_nullIsZeroAndDoesNotTreatNullElementAsMissing() {
        assertEquals(0, CollectionUtil.size((Collection<?>) null));
        assertEquals(2, CollectionUtil.size(Arrays.asList("a", null)));
        assertEquals(0, CollectionUtil.size((Map<?, ?>) null));
        Map<String, String> data = new HashMap<String, String>();
        data.put("k", "v");
        assertEquals(1, CollectionUtil.size(data));
        assertEquals(0, CollectionUtil.size(Collections.emptyMap()));
    }

    @Test
    public void isNotEmpty_matchesOppositeOfIsEmpty() {
        assertFalse(CollectionUtil.isNotEmpty((Collection<?>) null));
        assertFalse(CollectionUtil.isNotEmpty(Collections.emptyList()));
        assertTrue(CollectionUtil.isNotEmpty(Collections.singletonList("a")));
        assertTrue(CollectionUtil.isNotEmpty(Collections.singletonList(null)));

        assertFalse(CollectionUtil.isNotEmpty((Map<?, ?>) null));
        assertFalse(CollectionUtil.isNotEmpty(Collections.emptyMap()));
        Map<String, String> data = new HashMap<String, String>();
        data.put("k", null);
        assertTrue(CollectionUtil.isNotEmpty(data));

        assertFalse(CollectionUtil.isNotEmpty((Object[]) null));
        assertFalse(CollectionUtil.isNotEmpty(new Object[0]));
        assertTrue(CollectionUtil.isNotEmpty(new Object[]{null}));
    }

    @Test
    public void getOrNull_rejectsNullAndOutOfRangeButReturnsNullElement() {
        assertNull(CollectionUtil.getOrNull(null, 0));
        assertNull(CollectionUtil.getOrNull(Collections.emptyList(), 0));
        assertNull(CollectionUtil.getOrNull(Collections.singletonList("a"), -1));
        assertNull(CollectionUtil.getOrNull(Collections.singletonList("a"), 1));
        assertEquals("a", CollectionUtil.getOrNull(Arrays.asList("a", "b"), 0));
        assertEquals("b", CollectionUtil.getOrNull(Arrays.asList("a", "b"), 1));
        assertNull(CollectionUtil.getOrNull(Arrays.asList("a", null), 1));
    }

    @Test
    public void firstOrNullAndLastOrNull_handleEmptyAndNullElements() {
        assertNull(CollectionUtil.firstOrNull(null));
        assertNull(CollectionUtil.lastOrNull(null));
        assertNull(CollectionUtil.firstOrNull(Collections.emptyList()));
        assertNull(CollectionUtil.lastOrNull(Collections.emptyList()));
        assertEquals("a", CollectionUtil.firstOrNull(Arrays.asList("a", "b", "c")));
        assertEquals("c", CollectionUtil.lastOrNull(Arrays.asList("a", "b", "c")));
        assertEquals("only", CollectionUtil.firstOrNull(Collections.singletonList("only")));
        assertEquals("only", CollectionUtil.lastOrNull(Collections.singletonList("only")));
        assertNull(CollectionUtil.firstOrNull(Collections.singletonList(null)));
        assertNull(CollectionUtil.lastOrNull(Arrays.asList("a", null)));
    }

    @Test
    public void getOrDefault_usesContainsKeyNotNullValue() {
        assertEquals("fallback", CollectionUtil.getOrDefault(null, "k", "fallback"));
        assertNull(CollectionUtil.getOrDefault(null, "k", null));

        Map<String, String> data = new HashMap<String, String>();
        assertEquals("fallback", CollectionUtil.getOrDefault(data, "missing", "fallback"));
        data.put("present", null);
        assertNull(CollectionUtil.getOrDefault(data, "present", "fallback"));
        data.put("value", "kept");
        assertEquals("kept", CollectionUtil.getOrDefault(data, "value", "fallback"));
        data.put(null, "null-key");
        assertEquals("null-key", CollectionUtil.getOrDefault(data, null, "fallback"));
    }
}
