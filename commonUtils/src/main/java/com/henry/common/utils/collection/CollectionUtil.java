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
     * 返回数组元素个数。
     * data 为 null 时返回 0。不提供新的 {@code isEmpty(Object[])}，已有方法保持不变。
     *
     * @param data 允许为 null
     * @return 元素个数；data 为 null 时返回 0
     */
    public static int size(Object[] data) {
        return data == null ? 0 : data.length;
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
     * 返回列表的第一个元素；列表为 null 或空时返回默认值。
     * 第一个元素本身是 null 时返回 null，不用 defaultValue 代替。
     * defaultValue 允许为 null。不修改 list，也不修改 {@link #firstOrNull(List)}。
     *
     * @param list         允许为 null
     * @param defaultValue 列表没有元素时返回的值，允许为 null
     * @param <T>          元素类型
     * @return 第一个元素或 defaultValue
     */
    public static <T> T firstOrDefault(List<T> list, T defaultValue) {
        if (list == null || list.isEmpty()) {
            return defaultValue;
        }
        return list.get(0);
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
     * 返回列表的最后一个元素；列表为 null 或空时返回默认值。
     * 规则与 {@link #firstOrDefault(List, Object)} 对称。
     * 最后一个元素本身是 null 时返回 null，不用 defaultValue 代替。
     * defaultValue 允许为 null。不修改 list，也不修改 {@link #lastOrNull(List)} 和 {@link #firstOrDefault(List, Object)}。
     *
     * @param list         允许为 null
     * @param defaultValue 列表没有元素时返回的值，允许为 null
     * @param <T>          元素类型
     * @return 最后一个元素或 defaultValue
     */
    public static <T> T lastOrDefault(List<T> list, T defaultValue) {
        if (list == null || list.isEmpty()) {
            return defaultValue;
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

    /**
     * 判断集合是否包含指定元素。
     * data 为 null 时返回 false，不抛异常。value 允许为 null。
     * 其余情况调用 {@code data.contains(value)}，不吞掉集合自己抛出的异常。
     *
     * @param data  集合，允许为 null
     * @param value 要查找的元素，允许为 null
     * @return 集合包含该元素时返回 true
     */
    public static boolean contains(Collection<?> data, Object value) {
        if (data == null) {
            return false;
        }
        return data.contains(value);
    }

    /**
     * 统计 value 在集合中出现的次数。
     * value 允许为 null，null 元素按引用为空计数，其他元素用 {@link Object#equals(Object)}。
     * data 为 null 时返回 0。不修改集合，也不吞掉集合迭代时抛出的异常。
     *
     * @param data  集合，允许为 null
     * @param value 要统计的元素，允许为 null
     * @return 出现次数；data 为 null 时返回 0
     */
    public static int frequency(Collection<?> data, Object value) {
        if (data == null) {
            return 0;
        }
        int count = 0;
        for (Object item : data) {
            if (value == null ? item == null : value.equals(item)) {
                count++;
            }
        }
        return count;
    }

    /**
     * 判断 Map 是否包含指定键。
     * map 为 null 时返回 false。不修改 map。
     * key 为 null 时交给 Map 自己判断：支持 null 键则返回真实结果；Map 因 null 键抛出 {@link NullPointerException} 时返回 false。
     * key 不是 null 时不吞掉 Map 抛出的异常。
     * 不修改 {@link #contains(java.util.Collection, Object)}。
     *
     * @param map 允许为 null
     * @param key 允许为 null
     * @return 包含该键时返回 true
     */
    public static boolean containsKey(Map<?, ?> map, Object key) {
        if (map == null) {
            return false;
        }
        try {
            return map.containsKey(key);
        } catch (NullPointerException ex) {
            if (key == null) {
                return false;
            }
            throw ex;
        }
    }

    /**
     * 判断 Map 是否包含指定值。
     * map 为 null 时返回 false。不修改 map。
     * value 为 null 时交给 Map 自己判断：支持 null 值则返回真实结果；Map 因 null 值抛出 {@link NullPointerException} 时返回 false。
     * value 不是 null 时不吞掉 Map 抛出的异常。value 为 null 时也只吞掉 {@link NullPointerException}，其他异常仍向外抛出。
     * 不修改 {@link #containsKey(Map, Object)}。
     *
     * @param map   允许为 null
     * @param value 允许为 null
     * @return 包含该值时返回 true
     */
    public static boolean containsValue(Map<?, ?> map, Object value) {
        if (map == null) {
            return false;
        }
        try {
            return map.containsValue(value);
        } catch (NullPointerException ex) {
            if (value == null) {
                return false;
            }
            throw ex;
        }
    }
}
