package com.henry.common.utils.uuid;

import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class UuidUtilTest {

    @Test
    public void random_usesLowercaseHyphenatedText() {
        Set<String> seen = new HashSet<String>();
        for (int i = 0; i < 20; i++) {
            String value = UuidUtil.random();
            assertTrue(UuidUtil.isValid(value));
            assertTrue(value.equals(value.toLowerCase()));
            assertTrue(value.length() == 36);
            assertTrue(seen.add(value));
        }
    }

    @Test
    public void isValid_requiresHyphensAndHex() {
        assertFalse(UuidUtil.isValid(null));
        assertFalse(UuidUtil.isValid(""));
        assertFalse(UuidUtil.isValid("550e8400e29b41d4a716446655440000"));
        assertFalse(UuidUtil.isValid("550e8400-e29b-41d4-a716-44665544000"));
        assertFalse(UuidUtil.isValid("550e8400-e29b-41d4-a716-446655440000-extra"));
        assertFalse(UuidUtil.isValid("550e8400-e29b-41d4-a716-44665544000g"));
        assertTrue(UuidUtil.isValid("550e8400-e29b-41d4-a716-446655440000"));
        assertTrue(UuidUtil.isValid("550E8400-E29B-41D4-A716-446655440000"));
    }
}
