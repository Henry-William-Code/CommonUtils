package com.henry.common.utils.checksum;

import java.nio.charset.StandardCharsets;
import java.util.zip.CRC32;

/**
 * CRC-32 校验。
 * 已发布的 1.0.5 没有这个类。只使用 JDK 的 {@link CRC32}，不使用 Android API。
 */
public class Crc32Util {

    private Crc32Util() {
    }

    /**
     * 计算字节数组的 CRC-32。
     * data 为 null 或空数组时返回 0。因此无法从返回值区分 null 和空数组。
     * 结果是无符号 32 位值，放在 long 的低 32 位，范围是 0 到 4294967295。
     * 不修改 data。
     *
     * @param data 原始字节，允许为 null
     * @return CRC-32
     */
    public static long checksum(byte[] data) {
        if (data == null || data.length == 0) {
            return 0L;
        }
        CRC32 crc32 = new CRC32();
        crc32.update(data);
        return crc32.getValue();
    }

    /**
     * 按 UTF-8 计算文本的 CRC-32。
     * text 为 null 或空串时返回 0。规则与 {@link #checksum(byte[])} 相同。
     *
     * @param text 文本，允许为 null
     * @return CRC-32
     */
    public static long checksumUtf8(String text) {
        if (text == null || text.length() == 0) {
            return 0L;
        }
        return checksum(text.getBytes(StandardCharsets.UTF_8));
    }
}
