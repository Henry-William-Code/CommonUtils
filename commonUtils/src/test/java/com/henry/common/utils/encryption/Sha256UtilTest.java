package com.henry.common.utils.encryption;

import org.junit.Test;

import java.nio.charset.StandardCharsets;

import static org.junit.Assert.assertEquals;

public class Sha256UtilTest {

    @Test
    public void digest_knownUtf8Vectors() {
        assertEquals(
                "e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855",
                Sha256Util.digest(""));
        assertEquals(
                "ba7816bf8f01cfea414140de5dae2223b00361a396177a9cb410ff61f20015ad",
                Sha256Util.digest("abc"));
        assertEquals(
                Sha256Util.digest(new byte[] {(byte) 0xC3, (byte) 0xA9}),
                Sha256Util.digest("é"));
        assertEquals(
                Sha256Util.digest("你好".getBytes(StandardCharsets.UTF_8)),
                Sha256Util.digest("你好"));
    }

    @Test
    public void digest_nullIsEmptyStringAndEmptyBytesAreHashed() {
        assertEquals("", Sha256Util.digest((String) null));
        assertEquals("", Sha256Util.digest((byte[]) null));
        assertEquals(Sha256Util.digest(""), Sha256Util.digest(new byte[0]));
        assertEquals(64, Sha256Util.digest(new byte[0]).length());
    }
}