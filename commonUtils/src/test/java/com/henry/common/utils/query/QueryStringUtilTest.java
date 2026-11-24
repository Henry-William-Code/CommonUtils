package com.henry.common.utils.query;

import com.henry.common.utils.codec.UrlCodecUtil;

import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class QueryStringUtilTest {

    @Test
    public void parse_emptyAndQuestionMarkReturnFreshMaps() {
        Map<String, String> first = QueryStringUtil.parse(null);
        Map<String, String> second = QueryStringUtil.parse("");
        Map<String, String> third = QueryStringUtil.parse("?");
        assertTrue(first.isEmpty());
        assertTrue(second.isEmpty());
        assertTrue(third.isEmpty());
        assertNotSame(first, second);
        assertTrue(first instanceof LinkedHashMap);
        first.put("a", "b");
        assertTrue(second.isEmpty());
        assertTrue(third.isEmpty());
    }

    @Test
    public void parse_splitsSegmentsAndKeepsFirstKeyOrder() {
        Map<String, String> parsed = QueryStringUtil.parse("?b=1&a=2&b=3");
        assertEquals("3", parsed.get("b"));
        assertEquals("2", parsed.get("a"));
        assertEquals("b", new ArrayList<String>(parsed.keySet()).get(0));
        assertEquals("a", new ArrayList<String>(parsed.keySet()).get(1));
        assertEquals("", QueryStringUtil.parse("a").get("a"));
        assertEquals("", QueryStringUtil.parse("a=").get("a"));
        assertEquals("b", QueryStringUtil.parse("=b").get(""));
        assertEquals("", QueryStringUtil.parse("=").get(""));
        assertEquals("1=2", QueryStringUtil.parse("a=1=2").get("a"));
        assertTrue(QueryStringUtil.parse("&&").isEmpty());
        assertTrue(QueryStringUtil.parse("&").isEmpty());
        assertTrue(QueryStringUtil.parse("?&").isEmpty());
        assertEquals("1", QueryStringUtil.parse("&&a=1&").get("a"));
        assertEquals("", QueryStringUtil.parse("a&b=1").get("a"));
        assertEquals("1", QueryStringUtil.parse("a&b=1").get("b"));
        assertEquals("?a", QueryStringUtil.parse("??a=1").keySet().iterator().next());
        assertEquals("1", QueryStringUtil.parse("??a=1").get("?a"));
        assertEquals("1?b=2", QueryStringUtil.parse("a=1?b=2").get("a"));
        assertEquals(" b", QueryStringUtil.parse("a= b").get("a"));
        assertEquals("a b", QueryStringUtil.parse("a+b=c+d").keySet().iterator().next());
        assertEquals("c d", QueryStringUtil.parse("a+b=c+d").get("a b"));
        assertEquals("你", QueryStringUtil.parse("q=%E4%BD%A0").get("q"));
        Map<String, String> literal = QueryStringUtil.parse("http://example.com?a=1");
        assertEquals("1", literal.get("http://example.com?a"));
        assertFalse(literal.containsKey("a"));
        parsed.put("extra", "ok");
        assertEquals("ok", parsed.get("extra"));
    }

    @Test
    public void parse_propagatesDecodeFailure() {
        assertSameDecodeFailure("%ZZ", "%ZZ");
        assertSameDecodeFailure("a=%", "%");
        assertSameDecodeFailure("a=%GG", "%GG");
    }

    @Test
    public void build_encodesInIterationOrderAndSkipsNullKeys() {
        assertEquals("", QueryStringUtil.build(null));
        assertEquals("", QueryStringUtil.build(new LinkedHashMap<String, String>()));
        Map<String, String> params = new LinkedHashMap<String, String>();
        params.put("a b", "c&d");
        params.put("q", "你");
        assertEquals("a+b=c%26d&q=%E4%BD%A0", QueryStringUtil.build(params));
        assertEquals("c&d", params.get("a b"));
        Map<String, String> withNulls = new LinkedHashMap<String, String>();
        withNulls.put(null, "x");
        withNulls.put("a", null);
        withNulls.put("b", "c d");
        assertEquals("a=&b=c+d", QueryStringUtil.build(withNulls));
        assertEquals("x", withNulls.get(null));
        assertEquals(null, withNulls.get("a"));
        Map<String, String> onlyNullKey = new LinkedHashMap<String, String>();
        onlyNullKey.put(null, "x");
        assertEquals("", QueryStringUtil.build(onlyNullKey));
        Map<String, String> emptyKey = new LinkedHashMap<String, String>();
        emptyKey.put("", "v");
        assertEquals("=v", QueryStringUtil.build(emptyKey));
        assertFalse(QueryStringUtil.build(params).startsWith("?"));
    }

    @Test
    public void parseBuild_roundTripsNonNullEntries() {
        Map<String, String> params = new LinkedHashMap<String, String>();
        params.put("a b", "c&d=e");
        params.put("q", "你");
        params.put("empty", "");
        params.put("", "v");
        assertEquals(params, QueryStringUtil.parse(QueryStringUtil.build(params)));
        assertEquals(new LinkedHashMap<String, String>(), QueryStringUtil.parse(QueryStringUtil.build(new LinkedHashMap<String, String>())));
        Map<String, String> nullValue = new LinkedHashMap<String, String>();
        nullValue.put("a", null);
        Map<String, String> parsed = QueryStringUtil.parse(QueryStringUtil.build(nullValue));
        assertEquals("", parsed.get("a"));
        assertFalse(parsed.containsValue(null));
    }

    private static void assertSameDecodeFailure(String query, String raw) {
        IllegalArgumentException direct = null;
        try {
            UrlCodecUtil.decode(raw);
            fail("decode should fail for " + raw);
        } catch (IllegalArgumentException ex) {
            direct = ex;
        }
        try {
            QueryStringUtil.parse(query);
            fail("parse should fail for " + query);
        } catch (IllegalArgumentException ex) {
            assertEquals(direct.getClass(), ex.getClass());
            assertEquals(direct.getMessage(), ex.getMessage());
        }
    }
}
