package com.henry.common.utils.ip;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class IpUtilTest {

    @Test
    public void isIpv4_acceptsFourDecimalOctetsOnly() {
        assertTrue(IpUtil.isIpv4("192.168.1.1"));
        assertTrue(IpUtil.isIpv4("127.0.0.1"));
        assertTrue(IpUtil.isIpv4("0.0.0.0"));
        assertTrue(IpUtil.isIpv4("255.255.255.255"));
        assertFalse(IpUtil.isIpv4(null));
        assertFalse(IpUtil.isIpv4(""));
        assertFalse(IpUtil.isIpv4(" \t"));
        assertFalse(IpUtil.isIpv4(" 192.168.1.1"));
        assertFalse(IpUtil.isIpv4("192.168.1.1 "));
        assertFalse(IpUtil.isIpv4("192..168.1.1"));
        assertFalse(IpUtil.isIpv4(".192.168.1.1"));
        assertFalse(IpUtil.isIpv4("192.168.1.1."));
        assertFalse(IpUtil.isIpv4("01.2.3.4"));
        assertFalse(IpUtil.isIpv4("0.0.0.00"));
        assertFalse(IpUtil.isIpv4("256.0.0.1"));
        assertFalse(IpUtil.isIpv4("192.168.1"));
        assertFalse(IpUtil.isIpv4("192.168.1.1.1"));
        assertFalse(IpUtil.isIpv4("::1"));
        assertFalse(IpUtil.isIpv4("192.168.1.1/24"));
        assertFalse(IpUtil.isIpv4("192.168.1.1:80"));
        assertFalse(IpUtil.isIpv4("192.168.a.1"));
    }

    @Test
    public void toLong_returnsUnsigned32Bits() {
        assertEquals(0L, IpUtil.toLong("0.0.0.0"));
        assertEquals(4294967295L, IpUtil.toLong("255.255.255.255"));
        assertEquals(2130706433L, IpUtil.toLong("127.0.0.1"));
        assertEquals(3232235777L, IpUtil.toLong("192.168.1.1"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void toLong_rejectsInvalidText() {
        IpUtil.toLong("01.2.3.4");
    }
}
