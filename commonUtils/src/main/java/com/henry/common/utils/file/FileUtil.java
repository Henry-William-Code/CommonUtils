package com.henry.common.utils.file;

import com.henry.common.utils.string.StringUtil;

import java.io.File;

/**
 * 本地文件路径和 {@link File} 的辅助方法。
 * 已发布的 1.0.2 没有这个类。不使用 Android API，可以在普通 JVM 上调用。
 */
public class FileUtil {

    private FileUtil() {
    }

    /**
     * 取路径或文件名的扩展名，不包含小数点。
     * null、空串或只含空白时返回空串。空白规则与 {@link StringUtil#isBlank(String)} 相同，其余文本不 trim。
     * 只看最后一个 {@code /} 或 {@code \} 之后的文件名。目录部分里的小数点不算扩展名。
     * 文件名没有小数点、小数点在末尾，或小数点是文件名第一个字符时，返回空串。
     * 因此 {@code .gitignore} 的扩展名是空串，{@code archive.tar.gz} 的扩展名是 {@code gz}。
     * 不改变扩展名的大小写。
     *
     * @param pathOrName 路径或文件名，允许为 null
     * @return 扩展名；没有扩展名时返回空串
     */
    public static String getExtension(String pathOrName) {
        String name = getName(pathOrName);
        int dot = extensionDot(name);
        if (dot < 0) {
            return "";
        }
        return name.substring(dot + 1);
    }

    /**
     * 取最后一个路径分隔符之后的文件名，并去掉扩展名。
     * null、空串或只含空白时返回空串。没有扩展名时返回文件名本身，包括 {@code .gitignore}、{@code file.} 和 {@code ..}。
     * 扩展名的判定与 {@link #getExtension(String)} 相同。路径以分隔符结尾时，文件名为空串。
     *
     * @param pathOrName 路径或文件名，允许为 null
     * @return 去掉扩展名后的文件名
     */
    public static String getNameWithoutExtension(String pathOrName) {
        String name = getName(pathOrName);
        int dot = extensionDot(name);
        if (dot < 0) {
            return name;
        }
        return name.substring(0, dot);
    }

    /**
     * 判断是不是空的普通文件。
     * null、路径不存在，或普通文件长度为 0 时返回 true。
     * 目录返回 false，即使目录里没有子项。已存在但既不是文件也不是目录的路径也返回 false。
     *
     * @param file 允许为 null
     * @return 没有内容的普通文件，或不存在的路径，返回 true
     */
    public static boolean isEmpty(File file) {
        return file == null || !file.exists() || (file.isFile() && file.length() == 0L);
    }

    /**
     * 返回普通文件的字节长度。
     * null、不存在的路径、目录，以及其他非普通文件都返回 0。不递归统计目录里的文件。
     *
     * @param file 允许为 null
     * @return 普通文件的长度；其余情况返回 0
     */
    public static long size(File file) {
        if (file == null || !file.isFile()) {
            return 0L;
        }
        return file.length();
    }

    /**
     * 判断路径是不是已存在的目录。
     * file 为 null、路径不存在，或存在但不是目录时返回 false。
     * 普通文件返回 false。这和 {@link #isEmpty(File)} 不同：空目录在这里返回 true，在 isEmpty 中返回 false。
     *
     * @param file 允许为 null
     * @return 已存在的目录返回 true
     */
    public static boolean isDirectory(File file) {
        return file != null && file.isDirectory();
    }

    /**
     * 判断路径是不是已存在的普通文件。
     * file 为 null、路径不存在，或存在但不是普通文件时返回 false。
     * 目录返回 false。这和 {@link #isDirectory(File)} 互补，也和 {@link #isEmpty(File)} 不同：
     * 不存在的路径在这里返回 false，在 isEmpty 中返回 true。
     *
     * @param file 允许为 null
     * @return 已存在的普通文件返回 true
     */
    public static boolean isFile(File file) {
        return file != null && file.isFile();
    }

    /**
     * 判断路径是否存在。
     * file 为 null 或不存在时返回 false。普通文件和目录存在时都返回 true。
     * 不读取文件内容，也不区分文件和目录。
     *
     * @param file 允许为 null
     * @return 路径存在时返回 true
     */
    public static boolean exists(File file) {
        return file != null && file.exists();
    }

    /**
     * 删除一个文件或空目录。
     * null 或不存在时返回 false。只调用 {@link File#delete()}，不递归删除目录内容。
     * 非空目录会删除失败并返回 false，目录仍然保留。
     * {@link SecurityException} 时返回 false，不向外抛出。
     *
     * @param file 允许为 null
     * @return 删除成功时返回 true
     */
    public static boolean deleteQuietly(File file) {
        if (file == null || !file.exists()) {
            return false;
        }
        try {
            return file.delete();
        } catch (SecurityException ex) {
            return false;
        }
    }

    /**
     * 取最后一个 {@code /} 或 {@code \} 之后的文件名，保留扩展名。
     * null、空串或只含空白时返回空串。空白规则与 {@link #getExtension(String)} 相同，其余文本不 trim。
     * 路径以分隔符结尾时返回空串。没有分隔符时返回原文本。
     *
     * @param pathOrName 路径或文件名，允许为 null
     * @return 文件名；没有文件名时返回空串
     */
    public static String getName(String pathOrName) {
        if (StringUtil.isBlank(pathOrName)) {
            return "";
        }
        int separator = Math.max(pathOrName.lastIndexOf('/'), pathOrName.lastIndexOf('\\'));
        if (separator < 0) {
            return pathOrName;
        }
        if (separator == pathOrName.length() - 1) {
            return "";
        }
        return pathOrName.substring(separator + 1);
    }

    /**
     * 取父路径，不访问磁盘，也不解析 {@code .} 和 {@code ..}。
     * null、空串或只含空白时返回空串。空白规则与 {@link #getExtension(String)} 相同，其余文本不 trim。
     * 按最后一个 {@code /} 或 {@code \} 分割。没有分隔符时返回空串。
     * 路径以分隔符结尾时，先把这一层目录去掉，再取上一层：{@code foo/bar/} 得到 {@code foo}。
     * 只有根分隔符 {@code /} 或 {@code \} 时返回空串。{@code /path/to/file.txt} 得到 {@code /path/to}。
     * 不修改 {@link #getName(String)}、{@link #getExtension(String)} 和 {@link #getNameWithoutExtension(String)}。
     *
     * @param pathOrName 路径或文件名，允许为 null
     * @return 父路径；没有父路径时返回空串
     */
    public static String getParent(String pathOrName) {
        if (StringUtil.isBlank(pathOrName)) {
            return "";
        }
        int end = pathOrName.length();
        char last = pathOrName.charAt(end - 1);
        if (last == '/' || last == '\\') {
            end--;
            if (end == 0) {
                return "";
            }
        }
        int separator = -1;
        for (int i = 0; i < end; i++) {
            char current = pathOrName.charAt(i);
            if (current == '/' || current == '\\') {
                separator = i;
            }
        }
        if (separator < 0) {
            return "";
        }
        return pathOrName.substring(0, separator);
    }

    /**
     * @return 扩展名小数点的下标；没有可去掉的扩展名时返回 -1
     */
    private static int extensionDot(String name) {
        if (name.length() == 0) {
            return -1;
        }
        int dot = name.lastIndexOf('.');
        if (dot <= 0 || dot == name.length() - 1) {
            return -1;
        }
        return dot;
    }
}
