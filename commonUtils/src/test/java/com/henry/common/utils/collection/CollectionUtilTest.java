package com.henry.common.utils.collection;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
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
}
