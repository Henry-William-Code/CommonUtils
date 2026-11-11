package com.henry.common.utils.calculate;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MathematicsUtilTest {

    @Test
    public void power_keepsPublishedBehavior() {
        assertEquals(0, MathematicsUtil.power(2, 0));
        assertEquals(2, MathematicsUtil.power(2, 1));
        assertEquals(1024, MathematicsUtil.power(2, 10));
        assertEquals(0, MathematicsUtil.power(5, -1));
    }

    @Test
    public void clamp_insideAndEdges() {
        assertEquals(5, MathematicsUtil.clamp(5, 0, 10));
        assertEquals(0, MathematicsUtil.clamp(-1, 0, 10));
        assertEquals(10, MathematicsUtil.clamp(11, 0, 10));
        assertEquals(0, MathematicsUtil.clamp(0, 0, 10));
        assertEquals(10, MathematicsUtil.clamp(10, 0, 10));
        assertEquals(Integer.MIN_VALUE, MathematicsUtil.clamp(Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MAX_VALUE));
        assertEquals(Integer.MAX_VALUE, MathematicsUtil.clamp(Integer.MAX_VALUE, 0, Integer.MAX_VALUE));
    }

    @Test(expected = IllegalArgumentException.class)
    public void clamp_rejectsReversedBounds() {
        MathematicsUtil.clamp(1, 5, 3);
    }

    @Test
    public void clampLong_insideAndEdges() {
        assertEquals(5L, MathematicsUtil.clamp(5L, 0L, 10L));
        assertEquals(0L, MathematicsUtil.clamp(-1L, 0L, 10L));
        assertEquals(10L, MathematicsUtil.clamp(11L, 0L, 10L));
        assertEquals(0L, MathematicsUtil.clamp(0L, 0L, 10L));
        assertEquals(10L, MathematicsUtil.clamp(10L, 0L, 10L));
        assertEquals(Long.MIN_VALUE, MathematicsUtil.clamp(Long.MIN_VALUE, Long.MIN_VALUE, Long.MAX_VALUE));
        assertEquals(Long.MAX_VALUE, MathematicsUtil.clamp(Long.MAX_VALUE, 0L, Long.MAX_VALUE));
    }

    @Test(expected = IllegalArgumentException.class)
    public void clampLong_rejectsReversedBounds() {
        MathematicsUtil.clamp(1L, 5L, 3L);
    }

    @Test
    public void gcd_signsAndZero() {
        assertEquals(4, MathematicsUtil.gcd(12, 8));
        assertEquals(4, MathematicsUtil.gcd(-12, 8));
        assertEquals(4, MathematicsUtil.gcd(12, -8));
        assertEquals(4, MathematicsUtil.gcd(-12, -8));
        assertEquals(5, MathematicsUtil.gcd(0, 5));
        assertEquals(5, MathematicsUtil.gcd(5, 0));
        assertEquals(0, MathematicsUtil.gcd(0, 0));
        assertEquals(1, MathematicsUtil.gcd(17, 13));
    }

    @Test(expected = IllegalArgumentException.class)
    public void gcd_rejectsMinValue() {
        MathematicsUtil.gcd(Integer.MIN_VALUE, 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void gcd_rejectsMinValueOnSecondArgument() {
        MathematicsUtil.gcd(1, Integer.MIN_VALUE);
    }

    @Test
    public void lcm_signsAndZero() {
        assertEquals(12, MathematicsUtil.lcm(4, 6));
        assertEquals(12, MathematicsUtil.lcm(-4, 6));
        assertEquals(12, MathematicsUtil.lcm(4, -6));
        assertEquals(0, MathematicsUtil.lcm(0, 5));
        assertEquals(0, MathematicsUtil.lcm(5, 0));
        assertEquals(0, MathematicsUtil.lcm(0, 0));
        assertEquals(15, MathematicsUtil.lcm(3, 5));
    }

    @Test(expected = IllegalArgumentException.class)
    public void lcm_rejectsOverflow() {
        MathematicsUtil.lcm(Integer.MAX_VALUE, 2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void lcm_rejectsMinValueEvenWhenOtherIsZero() {
        MathematicsUtil.lcm(0, Integer.MIN_VALUE);
    }
}
