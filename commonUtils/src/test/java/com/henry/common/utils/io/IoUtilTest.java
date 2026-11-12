package com.henry.common.utils.io;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class IoUtilTest {

    @Test
    public void closeQuietly_ignoresNullAndIoException() {
        IoUtil.closeQuietly(null);
        final boolean[] closed = new boolean[] {false};
        IoUtil.closeQuietly(new Closeable() {
            @Override
            public void close() throws IOException {
                closed[0] = true;
                throw new IOException("close failed");
            }
        });
        assertTrue(closed[0]);
    }

    @Test
    public void readBytes_readsUpToLimitAndLeavesStreamOpen() throws Exception {
        byte[] data = new byte[] {1, 2, 3};
        TrackingInputStream input = new TrackingInputStream(data);
        assertArrayEquals(data, IoUtil.readBytes(input, 3));
        assertFalse(input.closed);
        assertArrayEquals(new byte[0], IoUtil.readBytes(new ByteArrayInputStream(new byte[0]), 0));
        assertArrayEquals(new byte[] {1}, IoUtil.readBytes(new ByteArrayInputStream(new byte[] {1}), 4));
    }

    @Test
    public void readBytes_rejectsNullNegativeAndOverflow() throws Exception {
        assertIllegalArgument(new Call() {
            @Override
            public void run() throws IOException {
                IoUtil.readBytes(null, 1);
            }
        });
        assertIllegalArgument(new Call() {
            @Override
            public void run() throws IOException {
                IoUtil.readBytes(new ByteArrayInputStream(new byte[] {1}), -1);
            }
        });
        assertIllegalArgument(new Call() {
            @Override
            public void run() throws IOException {
                IoUtil.readBytes(new ByteArrayInputStream(new byte[] {1, 2}), 1);
            }
        });
        assertIllegalArgument(new Call() {
            @Override
            public void run() throws IOException {
                IoUtil.readBytes(new ByteArrayInputStream(new byte[] {1}), 0);
            }
        });
    }

    @Test
    public void readUtf8_decodesAndReplacesInvalidBytes() throws Exception {
        byte[] text = "你好".getBytes(StandardCharsets.UTF_8);
        assertEquals("你好", IoUtil.readUtf8(new ByteArrayInputStream(text), text.length));
        assertEquals("\uFFFD", IoUtil.readUtf8(new ByteArrayInputStream(new byte[] {(byte) 0xFF}), 1));
        TrackingInputStream input = new TrackingInputStream(new byte[] {'a'});
        assertEquals("a", IoUtil.readUtf8(input, 1));
        assertFalse(input.closed);
    }

    private static void assertIllegalArgument(Call call) throws Exception {
        try {
            call.run();
            fail("expected IllegalArgumentException");
        } catch (IllegalArgumentException expected) {
            // 空流、负数上限和超出上限都应拒绝。
        }
    }

    private interface Call {
        void run() throws IOException;
    }

    private static final class TrackingInputStream extends InputStream {
        private final ByteArrayInputStream delegate;
        private boolean closed;

        private TrackingInputStream(byte[] data) {
            delegate = new ByteArrayInputStream(data);
        }

        @Override
        public int read() {
            return delegate.read();
        }

        @Override
        public int read(byte[] buffer, int offset, int length) {
            return delegate.read(buffer, offset, length);
        }

        @Override
        public void close() {
            closed = true;
        }
    }
}
