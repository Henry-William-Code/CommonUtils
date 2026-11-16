package com.henry.common.utils.compress;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * gzip 压缩和解压。
 * 已发布的 1.0.5 没有这个类。只使用 JDK 的 {@code java.util.zip}，不使用 Android API。
 */
public class GzipUtil {

    private GzipUtil() {
    }

    /**
     * 把字节数组压成 gzip。
     * data 为 null 时返回长度为 0 的数组，不做压缩。
     * 空数组会压成合法的 gzip 字节，解压后仍是空数组。
     * 不修改 data 的内容。
     *
     * @param data 原始字节，允许为 null
     * @return gzip 字节；data 为 null 时返回空数组
     */
    public static byte[] compress(byte[] data) {
        if (data == null) {
            return new byte[0];
        }
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try {
            GZIPOutputStream gzip = new GZIPOutputStream(output);
            try {
                gzip.write(data);
            } finally {
                gzip.close();
            }
            return output.toByteArray();
        } catch (IOException ex) {
            throw new IllegalStateException("gzip compress failed", ex);
        }
    }

    /**
     * 解压 gzip 字节，结果不超过 maxBytes。
     * data 为 null，或 maxBytes 小于 0 时抛出 {@link IllegalArgumentException}。
     * data 长度为 0，或不是合法 gzip 时抛出 {@link IllegalArgumentException}，原因放在 cause 里。
     * 解压后字节数大于 maxBytes 时抛出 {@link IllegalArgumentException}，不返回截断内容。
     * 解压结果可以是空数组。maxBytes 为 0 时，只有解压结果也为空才允许。
     *
     * @param data     gzip 字节，不能为 null
     * @param maxBytes 允许解压出的最大字节数，包含 0
     * @return 解压后的全部字节
     */
    public static byte[] decompress(byte[] data, int maxBytes) {
        if (data == null) {
            throw new IllegalArgumentException("data is null");
        }
        if (maxBytes < 0) {
            throw new IllegalArgumentException("maxBytes is negative");
        }
        if (data.length == 0) {
            throw new IllegalArgumentException("data is empty");
        }
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try {
            GZIPInputStream gzip = new GZIPInputStream(new ByteArrayInputStream(data));
            try {
                byte[] buffer = new byte[4096];
                int remaining = maxBytes;
                while (remaining > 0) {
                    int count = gzip.read(buffer, 0, Math.min(buffer.length, remaining));
                    if (count < 0) {
                        return output.toByteArray();
                    }
                    if (count == 0) {
                        throw new IOException("gzip read returned zero");
                    }
                    output.write(buffer, 0, count);
                    remaining -= count;
                }
                if (gzip.read() >= 0) {
                    throw new IllegalArgumentException("gzip exceeds maxBytes");
                }
                return output.toByteArray();
            } finally {
                gzip.close();
            }
        } catch (IllegalArgumentException ex) {
            throw ex;
        } catch (IOException ex) {
            throw new IllegalArgumentException("data is not valid gzip", ex);
        }
    }
}
