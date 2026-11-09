package com.henry.common.utils.collection;

import java.util.Collection;
import java.util.Map;

/**
 * 集合、Map 和数组的空值判断。
 * 已发布的 1.0.2 没有这个类。
 */
public class CollectionUtil {

    private CollectionUtil() {
    }

    /**
     * 集合是否没有元素。
     *
     * @param data 允许为 null
     * @return data 为 null 或没有元素时返回 true
     */
    public static boolean isEmpty(Collection<?> data) {
        return data == null || data.isEmpty();
    }

    /**
     * Map 是否没有元素。
     *
     * @param data 允许为 null
     * @return data 为 null 或没有元素时返回 true
     */
    public static boolean isEmpty(Map<?, ?> data) {
        return data == null || data.isEmpty();
    }

    /**
     * 数组是否没有元素。数组里的元素本身为 null 时，只要长度大于 0 就返回 false。
     *
     * @param data 允许为 null
     * @return data 为 null 或长度为 0 时返回 true
     */
    public static boolean isEmpty(Object[] data) {
        return data == null || data.length == 0;
    }

    /**
     * 返回集合元素个数。
     * data 为 null 时返回 0。不捕获集合自身抛出的异常。
     *
     * @param data 允许为 null
     * @return 元素个数；data 为 null 时返回 0
     */
    public static int size(Collection<?> data) {
        return data == null ? 0 : data.size();
    }

    /**
     * 返回 Map 元素个数。
     * data 为 null 时返回 0。不捕获 Map 自身抛出的异常。
     *
     * @param data 允许为 null
     * @return 元素个数；data 为 null 时返回 0
     */
    public static int size(Map<?, ?> data) {
        return data == null ? 0 : data.size();
    }
}
