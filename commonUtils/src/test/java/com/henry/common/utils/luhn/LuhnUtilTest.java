package com.henry.common.utils.luhn;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class LuhnUtilTest {

    @Test
    public void isValid_rejectsNullEmptyShortAndNonDigits() {
        assertFalse(LuhnUtil.isValid(null));
        assertFalse(LuhnUtil.isValid(""));
        assertFalse(LuhnUtil.isValid("0"));
        assertFalse(LuhnUtil.isValid("5"));
        assertFalse(LuhnUtil.isValid(" "));
        assertFalse(LuhnUtil.isValid(" 0"));
        assertFalse(LuhnUtil.isValid("0 "));
        assertFalse(LuhnUtil.isValid("12 3"));
        assertFalse(LuhnUtil.isValid("12-3"));
        assertFalse(LuhnUtil.isValid("12a"));
        assertFalse(LuhnUtil.isValid("１２"));
        assertFalse(LuhnUtil.isValid("00 "));
    }

    @Test
    public void isValid_acceptsKnownLuhnNumbers() {
        assertTrue(LuhnUtil.isValid("00"));
        assertTrue(LuhnUtil.isValid("18"));
        assertTrue(LuhnUtil.isValid("26"));
        assertTrue(LuhnUtil.isValid("000"));
        assertTrue(LuhnUtil.isValid("79927398713"));
        assertFalse(LuhnUtil.isValid("10"));
        assertFalse(LuhnUtil.isValid("79927398710"));
        assertFalse(LuhnUtil.isValid("79927398714"));
        assertFalse(LuhnUtil.isValid("7992739871"));
    }

    @Test
    public void checkDigit_completesPayloadAndRejectsInvalidText() {
        assertEquals(3, LuhnUtil.checkDigit("7992739871"));
        assertEquals(8, LuhnUtil.checkDigit("1"));
        assertEquals(0, LuhnUtil.checkDigit("0"));
        assertEquals(0, LuhnUtil.checkDigit("00"));
        String[] payloads = {
                "0", "1", "7", "9", "00", "42", "7992739871", "424242424242424"
        };
        for (int i = 0; i < payloads.length; i++) {
            int digit = LuhnUtil.checkDigit(payloads[i]);
            assertTrue(digit >= 0 && digit <= 9);
            String full = payloads[i] + digit;
            assertTrue(LuhnUtil.isValid(full));
            assertTrue(LuhnUtil.isValid(payloads[i] + String.valueOf(digit)));
            for (int other = 0; other <= 9; other++) {
                if (other != digit) {
                    assertFalse(LuhnUtil.isValid(payloads[i] + other));
                }
            }
        }
        assertInvalidPayload(null);
        assertInvalidPayload("");
        assertInvalidPayload(" ");
        assertInvalidPayload(" 1");
        assertInvalidPayload("1 ");
        assertInvalidPayload("12a");
        assertInvalidPayload("1-2");
        assertInvalidPayload("1.2");
        assertInvalidPayload("１２");
        assertFalse(LuhnUtil.isValid(null));
        assertFalse(LuhnUtil.isValid(""));
        assertFalse(LuhnUtil.isValid(" "));
    }

    private static void assertInvalidPayload(String payload) {
        try {
            LuhnUtil.checkDigit(payload);
            fail("expected IllegalArgumentException");
        } catch (IllegalArgumentException expected) {
            // 非数字、空串和 null 都拒绝。
        }
    }
}
