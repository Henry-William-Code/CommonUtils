package com.henry.common.utils.io;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * 流的关闭和有限读取。
 * 已发布的 1.0.3 没有这个类。方法不使用 Android API。
 */
public class IoUtil {

    private IoUtil() {
    }

    /**
     * 关闭可关闭对象，并吞掉 {@link IOException}。
     * closeable 为 null 时直接返回。只捕获 {@link IOException}，其他运行时异常仍向外抛出。
     * 不判断对象是否已经关闭。
     *
     * @param closeable 允许为 null
     */
    public static void closeQuietly(Closeable closeable) {
        if (closeable == null) {
            return;
        }
        try {
            closeable.close();
        } catch (IOException ex) {
            // 调用方选择安静关闭，不再把关闭失败传出去。
        }
    }

    /**
     * 读取输入流的全部字节，但不超过 maxBytes。
     * input 为 null，或 maxBytes 小于 0 时抛出 {@link IllegalArgumentException}。
     * 实际字节数大于 maxBytes 时抛出 {@link IllegalArgumentException}，不返回截断结果。
     * 流立即结束时返回长度为 0 的数组，包括 maxBytes 为 0 且没有字节的情况。
     * 不关闭 input。读取过程中的 {@link IOException} 原样抛出。
     *
     * @param input    输入流，不能为 null
     * @param maxBytes 允许读取的最大字节数，包含 0
     * @return 读到的全部字节
     * @throws IOException 读取失败，或底层 read 在请求长度大于 0 时返回 0
     */
    public static byte[] readBytes(InputStream input, int maxBytes) throws IOException {
        if (input == null) {
            throw new IllegalArgumentException("input is null");
        }
        if (maxBytes < 0) {
            throw new IllegalArgumentException("maxBytes is negative");
        }
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        byte[] buffer = new byte[4096];
        int remaining = maxBytes;
        while (remaining > 0) {
            int count = input.read(buffer, 0, Math.min(buffer.length, remaining));
            if (count < 0) {
                return output.toByteArray();
            }
            if (count == 0) {
                throw new IOException("input read returned zero");
            }
            output.write(buffer, 0, count);
            remaining -= count;
        }
        if (input.read() >= 0) {
            throw new IllegalArgumentException("input exceeds maxBytes");
        }
        return output.toByteArray();
    }

    /**
     * 按 UTF-8 读取输入流中的文本，字节上限与 {@link #readBytes(InputStream, int)} 相同。
     * 非法 UTF-8 字节序列替换为 {@code U+FFFD}，不因此抛异常。
     * 不关闭 input。
     *
     * @param input    输入流，不能为 null
     * @param maxBytes 允许读取的最大字节数，包含 0
     * @return UTF-8 文本
     * @throws IOException 读取失败，或底层 read 在请求长度大于 0 时返回 0
     */
    public static String readUtf8(InputStream input, int maxBytes) throws IOException {
        return new String(readBytes(input, maxBytes), StandardCharsets.UTF_8);
    }
}
