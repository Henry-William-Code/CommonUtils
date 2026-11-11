package com.henry.common.utils.collection;

import java.util.Collection;
import java.util.List;
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

    /**
     * 集合是否至少有一个元素。规则与 {@link #isEmpty(Collection)} 相反。
     *
     * @param data 允许为 null
     * @return null 或没有元素时返回 false
     */
    public static boolean isNotEmpty(Collection<?> data) {
        return !isEmpty(data);
    }

    /**
     * Map 是否至少有一个元素。规则与 {@link #isEmpty(Map)} 相反。
     *
     * @param data 允许为 null
     * @return null 或没有元素时返回 false
     */
    public static boolean isNotEmpty(Map<?, ?> data) {
        return !isEmpty(data);
    }

    /**
     * 数组是否至少有一个元素。规则与 {@link #isEmpty(Object[])} 相反。
     * 元素本身为 null 时，只要长度大于 0 就返回 true。
     *
     * @param data 允许为 null
     * @return null 或长度为 0 时返回 false
     */
    public static boolean isNotEmpty(Object[] data) {
        return !isEmpty(data);
    }

    /**
     * 按下标读取列表元素。
     * 列表为 null、下标小于 0，或下标大于等于列表长度时返回 null。
     * 该位置的元素本身是 null 时也返回 null，调用方无法从返回值区分这两种情况。
     *
     * @param list  允许为 null
     * @param index 下标，从 0 开始
     * @param <T>   元素类型
     * @return 下标合法时返回该元素，否则返回 null
     */
    public static <T> T getOrNull(List<T> list, int index) {
        if (list == null || index < 0 || index >= list.size()) {
            return null;
        }
        return list.get(index);
    }

    /**
     * 返回列表的第一个元素。
     * 列表为 null 或没有元素时返回 null。第一个元素本身是 null 时也返回 null，无法和空列表区分。
     *
     * @param list 允许为 null
     * @param <T>  元素类型
     * @return 第一个元素；没有元素时返回 null
     */
    public static <T> T firstOrNull(List<T> list) {
        return getOrNull(list, 0);
    }

    /**
     * 返回列表的最后一个元素。
     * 列表为 null 或没有元素时返回 null。最后一个元素本身是 null 时也返回 null，无法和空列表区分。
     *
     * @param list 允许为 null
     * @param <T>  元素类型
     * @return 最后一个元素；没有元素时返回 null
     */
    public static <T> T lastOrNull(List<T> list) {
        if (list == null || list.isEmpty()) {
            return null;
        }
        return list.get(list.size() - 1);
    }

    /**
     * 读取 Map 中的值；键不存在或 Map 为 null 时返回默认值。
     * 键存在且对应值为 null 时返回 null，不返回 defaultValue。
     * 判断键是否存在使用 {@link Map#containsKey(Object)}，不用 {@link Map#get(Object)} 单独判断。
     * defaultValue 本身可以是 null。此时键不存在也会返回 null，无法和“键存在但值为 null”区分。
     *
     * @param map          允许为 null
     * @param key          允许为 null
     * @param defaultValue 键不存在或 map 为 null 时返回的值，允许为 null
     * @param <V>          值类型
     * @return 键存在时返回映射值，否则返回 defaultValue
     */
    public static <V> V getOrDefault(Map<?, V> map, Object key, V defaultValue) {
        if (map == null || !map.containsKey(key)) {
            return defaultValue;
        }
        return map.get(key);
    }
}
