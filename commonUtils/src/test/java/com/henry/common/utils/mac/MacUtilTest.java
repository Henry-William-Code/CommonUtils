package com.henry.common.utils.mac;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class MacUtilTest {

    @Test
    public void isValid_acceptsColonDashOrPlainHex() {
        assertTrue(MacUtil.isValid("aa:bb:cc:dd:ee:ff"));
        assertTrue(MacUtil.isValid("AA-BB-CC-DD-EE-FF"));
        assertTrue(MacUtil.isValid("aabbccddeeff"));
        assertTrue(MacUtil.isValid("Aa:Bb:Cc:Dd:Ee:Ff"));
        assertFalse(MacUtil.isValid(null));
        assertFalse(MacUtil.isValid(""));
        assertFalse(MacUtil.isValid(" \t"));
        assertFalse(MacUtil.isValid(" aa:bb:cc:dd:ee:ff"));
        assertFalse(MacUtil.isValid("aa:bb:cc:dd:ee:ff "));
        assertFalse(MacUtil.isValid("aa:bb:cc:dd:ee"));
        assertFalse(MacUtil.isValid("aa:bb:cc:dd:ee:ff:11"));
        assertFalse(MacUtil.isValid("aa::bb:cc:dd:ee:ff"));
        assertFalse(MacUtil.isValid("aa:bb-cc:dd:ee:ff"));
        assertFalse(MacUtil.isValid("aa:bb:cc:dd:ee:fg"));
        assertFalse(MacUtil.isValid("aabbccddee"));
        assertFalse(MacUtil.isValid("aabbccddeeff00"));
    }

    @Test
    public void normalize_usesLowercaseColons() {
        assertEquals("aa:bb:cc:dd:ee:ff", MacUtil.normalize("AA-BB-CC-DD-EE-FF"));
        assertEquals("aa:bb:cc:dd:ee:ff", MacUtil.normalize("aabbccddeeff"));
        assertEquals("aa:bb:cc:dd:ee:ff", MacUtil.normalize("aa:bb:cc:dd:ee:ff"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void normalize_rejectsInvalidText() {
        MacUtil.normalize("aa:bb:cc:dd:ee:ff ");
    }
}
