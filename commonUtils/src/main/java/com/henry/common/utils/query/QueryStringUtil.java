package com.henry.common.utils.query;

import com.henry.common.utils.codec.UrlCodecUtil;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 解析和拼接 {@code application/x-www-form-urlencoded} 查询串。
 * 编解码使用 {@link UrlCodecUtil}，因此是 UTF-8，空格与 {@code +} 按表单规则处理。
 * 入参是查询串，不是完整 URL，不会从 {@code http://...} 里抽取查询部分。
 * 已发布的 1.0.8 没有这个类。不能实例化。不修改 {@code UrlCodecUtil} 或 {@code WebUtil}。
 */
public class QueryStringUtil {

    private QueryStringUtil() {
    }

    /**
     * 把查询串解析成键值。
     * query 为 null、空串或只含 {@code ?} 时返回新的空 {@link LinkedHashMap}。
     * 以 {@code ?} 开头时只去掉开头的一个 {@code ?}，中间的 {@code ?} 留在键或值里。
     * 按 {@code &} 分段，空段（例如 {@code &&} 或首尾 {@code &}）跳过。
     * 没有 {@code =} 的段，值是空串；{@code =value} 的键是空串。一段里有多个 {@code =} 时，只按第一个分开，其余属于值。
     * 键和值都用 {@link UrlCodecUtil#decode(String)} 解码，不去掉空白。
     * 重复键保留最后一次出现的值，插入顺序以键首次出现为准。
     * 返回的 Map 可以修改，每次调用都是新的 {@link LinkedHashMap}，不是内部共享实例。
     * 解码失败时把 {@link UrlCodecUtil#decode(String)} 的异常原样抛出，不返回已解析的部分。
     *
     * @param query 查询串，允许为 null；不是完整 URL
     * @return 新的可修改 LinkedHashMap
     */
    public static Map<String, String> parse(String query) {
        LinkedHashMap<String, String> out = new LinkedHashMap<String, String>();
        if (query == null || query.length() == 0) {
            return out;
        }
        String body = query.charAt(0) == '?' ? query.substring(1) : query;
        int start = 0;
        while (start < body.length()) {
            int amp = body.indexOf('&', start);
            int end = amp < 0 ? body.length() : amp;
            if (end > start) {
                putSegment(out, body.substring(start, end));
            }
            if (amp < 0) {
                break;
            }
            start = amp + 1;
        }
        return out;
    }

    /**
     * 按 Map 的迭代顺序拼接查询串。
     * params 为 null 或空 Map 时返回空串。始终写出 {@code key=value}，用 {@code &} 连接，不加前导 {@code ?}。
     * 跳过 null 键；null 值按空串。键和值都用 {@link UrlCodecUtil#encode(String)} 编码。
     * 不修改入参 Map。
     * 对不含 null 键、且值也不为 null 的 Map，{@code parse(build(params))} 得到相同的键和值；空 Map 得到空 Map。
     * null 值会变成空串，解析回来是空串而不是 null。
     *
     * @param params 查询参数，允许为 null
     * @return 查询串；没有可输出的条目时返回空串
     */
    public static String build(Map<String, String> params) {
        if (params == null || params.isEmpty()) {
            return "";
        }
        StringBuilder out = new StringBuilder();
        boolean any = false;
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (entry.getKey() == null) {
                continue;
            }
            if (any) {
                out.append('&');
            }
            any = true;
            out.append(UrlCodecUtil.encode(entry.getKey()));
            out.append('=');
            String value = entry.getValue();
            out.append(UrlCodecUtil.encode(value == null ? "" : value));
        }
        return out.toString();
    }

    private static void putSegment(Map<String, String> out, String segment) {
        int eq = segment.indexOf('=');
        String rawKey;
        String rawValue;
        if (eq < 0) {
            rawKey = segment;
            rawValue = "";
        } else {
            rawKey = segment.substring(0, eq);
            rawValue = segment.substring(eq + 1);
        }
        out.put(UrlCodecUtil.decode(rawKey), UrlCodecUtil.decode(rawValue));
    }
}
