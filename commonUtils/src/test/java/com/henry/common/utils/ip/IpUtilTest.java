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

    @Test
    public void fromLong_reversesToLongWithoutLeadingZeros() {
        assertEquals("0.0.0.0", IpUtil.fromLong(0L));
        assertEquals("255.255.255.255", IpUtil.fromLong(4294967295L));
        assertEquals("127.0.0.1", IpUtil.fromLong(2130706433L));
        assertEquals(3232235777L, IpUtil.toLong(IpUtil.fromLong(3232235777L)));
        assertTrue(IpUtil.isIpv4("127.0.0.1"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void fromLong_rejectsNegative() {
        IpUtil.fromLong(-1L);
    }

    @Test(expected = IllegalArgumentException.class)
    public void fromLong_rejectsAboveUnsigned32() {
        IpUtil.fromLong(4294967296L);
    }

    @Test
    public void isLoopback_requiresFirstOctet127UnderIpv4Rules() {
        assertTrue(IpUtil.isLoopback("127.0.0.1"));
        assertTrue(IpUtil.isLoopback("127.0.0.0"));
        assertTrue(IpUtil.isLoopback("127.255.255.255"));
        assertFalse(IpUtil.isLoopback("0.0.0.0"));
        assertFalse(IpUtil.isLoopback("126.0.0.1"));
        assertFalse(IpUtil.isLoopback("128.0.0.1"));
        assertFalse(IpUtil.isLoopback("::1"));
        assertFalse(IpUtil.isLoopback(null));
        assertFalse(IpUtil.isLoopback(""));
        assertFalse(IpUtil.isLoopback(" \t"));
        assertFalse(IpUtil.isLoopback(" 127.0.0.1"));
        assertFalse(IpUtil.isLoopback("127.00.0.1"));
        assertTrue(IpUtil.isIpv4("127.0.0.1"));
        assertEquals(2130706433L, IpUtil.toLong("127.0.0.1"));
    }

    @Test
    public void isPrivate_acceptsOnlyRfc1918() {
        assertTrue(IpUtil.isPrivate("10.0.0.0"));
        assertTrue(IpUtil.isPrivate("10.255.255.255"));
        assertTrue(IpUtil.isPrivate("10.1.2.3"));
        assertTrue(IpUtil.isPrivate("172.16.0.0"));
        assertTrue(IpUtil.isPrivate("172.16.0.1"));
        assertTrue(IpUtil.isPrivate("172.31.255.255"));
        assertTrue(IpUtil.isPrivate("172.24.1.1"));
        assertTrue(IpUtil.isPrivate("192.168.0.0"));
        assertTrue(IpUtil.isPrivate("192.168.1.1"));
        assertTrue(IpUtil.isPrivate("192.168.255.255"));
        assertFalse(IpUtil.isPrivate("9.255.255.255"));
        assertFalse(IpUtil.isPrivate("11.0.0.0"));
        assertFalse(IpUtil.isPrivate("172.15.255.255"));
        assertFalse(IpUtil.isPrivate("172.32.0.0"));
        assertFalse(IpUtil.isPrivate("192.167.255.255"));
        assertFalse(IpUtil.isPrivate("192.169.0.1"));
        assertFalse(IpUtil.isPrivate("127.0.0.1"));
        assertFalse(IpUtil.isPrivate("169.254.0.1"));
        assertFalse(IpUtil.isPrivate("0.0.0.0"));
        assertFalse(IpUtil.isPrivate("192.0.2.1"));
        assertFalse(IpUtil.isPrivate("8.8.8.8"));
        assertFalse(IpUtil.isPrivate("::1"));
        assertFalse(IpUtil.isPrivate(null));
        assertFalse(IpUtil.isPrivate(""));
        assertFalse(IpUtil.isPrivate(" \t"));
        assertFalse(IpUtil.isPrivate(" 10.0.0.1"));
        assertFalse(IpUtil.isPrivate("10.0.0.1 "));
        assertFalse(IpUtil.isPrivate("010.0.0.1"));
        assertFalse(IpUtil.isPrivate("10.00.0.1"));
        assertFalse(IpUtil.isPrivate("10.0.0.1/8"));
        assertTrue(IpUtil.isIpv4("10.0.0.1"));
        assertTrue(IpUtil.isLoopback("127.0.0.1"));
        assertFalse(IpUtil.isPrivate("127.0.0.1"));
        assertEquals(3232235777L, IpUtil.toLong("192.168.1.1"));
        assertEquals("10.0.0.1", IpUtil.fromLong(IpUtil.toLong("10.0.0.1")));
    }

    @Test
    public void isLinkLocal_acceptsOnly169254() {
        assertTrue(IpUtil.isLinkLocal("169.254.0.0"));
        assertTrue(IpUtil.isLinkLocal("169.254.0.1"));
        assertTrue(IpUtil.isLinkLocal("169.254.1.1"));
        assertTrue(IpUtil.isLinkLocal("169.254.255.255"));
        assertFalse(IpUtil.isLinkLocal("169.253.255.255"));
        assertFalse(IpUtil.isLinkLocal("169.253.0.1"));
        assertFalse(IpUtil.isLinkLocal("169.255.0.0"));
        assertFalse(IpUtil.isLinkLocal("168.254.0.1"));
        assertFalse(IpUtil.isLinkLocal("170.254.0.1"));
        assertFalse(IpUtil.isLinkLocal("127.0.0.1"));
        assertFalse(IpUtil.isLinkLocal("192.168.0.1"));
        assertFalse(IpUtil.isLinkLocal("10.0.0.1"));
        assertFalse(IpUtil.isLinkLocal("8.8.8.8"));
        assertFalse(IpUtil.isLinkLocal("::1"));
        assertFalse(IpUtil.isLinkLocal("fe80::1"));
        assertFalse(IpUtil.isLinkLocal(null));
        assertFalse(IpUtil.isLinkLocal(""));
        assertFalse(IpUtil.isLinkLocal(" \t"));
        assertFalse(IpUtil.isLinkLocal(" 169.254.0.1"));
        assertFalse(IpUtil.isLinkLocal("169.254.0.1 "));
        assertFalse(IpUtil.isLinkLocal("169.254.00.1"));
        assertFalse(IpUtil.isLinkLocal("0169.254.0.1"));
        assertTrue(IpUtil.isIpv4("169.254.0.1"));
        assertFalse(IpUtil.isPrivate("169.254.0.1"));
        assertTrue(IpUtil.isLoopback("127.0.0.1"));
        assertFalse(IpUtil.isLinkLocal("127.0.0.1"));
        assertTrue(IpUtil.isPrivate("192.168.0.1"));
        assertTrue(IpUtil.isPrivate("10.0.0.1"));
    }
}
