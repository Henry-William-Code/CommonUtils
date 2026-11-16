package com.henry.common.utils.compress;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class GzipUtilTest {

    @Test
    public void compress_nullIsEmptyAndEmptyInputIsValidGzip() {
        assertArrayEquals(new byte[0], GzipUtil.compress(null));
        byte[] original = new byte[] {1, 2, 3};
        byte[] copy = Arrays.copyOf(original, original.length);
        byte[] gzip = GzipUtil.compress(original);
        assertArrayEquals(copy, original);
        assertTrue(gzip.length > 0);
        assertArrayEquals(original, GzipUtil.decompress(gzip, 3));
        assertArrayEquals(new byte[0], GzipUtil.decompress(GzipUtil.compress(new byte[0]), 0));
        assertArrayEquals(new byte[0], GzipUtil.decompress(GzipUtil.compress(new byte[0]), 4));
    }

    @Test
    public void decompress_rejectsNullNegativeEmptyAndInvalid() {
        assertIllegal(new Call() {
            public void run() {
                GzipUtil.decompress(null, 1);
            }
        });
        assertIllegal(new Call() {
            public void run() {
                GzipUtil.decompress(new byte[] {1}, -1);
            }
        });
        assertIllegal(new Call() {
            public void run() {
                GzipUtil.decompress(new byte[0], 1);
            }
        });
        assertIllegal(new Call() {
            public void run() {
                GzipUtil.decompress(new byte[] {1, 2, 3}, 8);
            }
        });
    }

    @Test
    public void decompress_rejectsOverflowWithoutReturningPartialBytes() {
        final byte[] gzip = GzipUtil.compress(new byte[] {1, 2, 3, 4});
        assertIllegal(new Call() {
            public void run() {
                GzipUtil.decompress(gzip, 3);
            }
        });
        assertIllegal(new Call() {
            public void run() {
                GzipUtil.decompress(gzip, 0);
            }
        });
    }

    private static void assertIllegal(Call call) {
        try {
            call.run();
            fail("expected IllegalArgumentException");
        } catch (IllegalArgumentException expected) {
            // 空数据、非法 gzip 和超限都应拒绝。
        }
    }

    private interface Call {
        void run();
    }
}
