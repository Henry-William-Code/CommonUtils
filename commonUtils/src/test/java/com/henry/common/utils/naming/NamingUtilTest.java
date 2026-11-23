package com.henry.common.utils.naming;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class NamingUtilTest {

    @Test
    public void toSnakeCase_splitsOnCaseDigitAndSeparators() {
        assertEquals("", NamingUtil.toSnakeCase(null));
        assertEquals("", NamingUtil.toSnakeCase(""));
        assertEquals("", NamingUtil.toSnakeCase("---"));
        assertEquals("", NamingUtil.toSnakeCase(" _- "));
        assertEquals("hello_world", NamingUtil.toSnakeCase("HelloWorld"));
        assertEquals("hello_world", NamingUtil.toSnakeCase("helloWorld"));
        assertEquals("hello_world", NamingUtil.toSnakeCase("hello_world"));
        assertEquals("hello_world", NamingUtil.toSnakeCase("HELLO_WORLD"));
        assertEquals("hello_world", NamingUtil.toSnakeCase("hello-world"));
        assertEquals("hello_world", NamingUtil.toSnakeCase("hello world"));
        assertEquals("hello_world", NamingUtil.toSnakeCase("__hello__world__"));
        assertEquals("hello_world", NamingUtil.toSnakeCase("  Hello  World  "));
        assertEquals("xmlparser", NamingUtil.toSnakeCase("XMLParser"));
        assertEquals("get_httpresponse", NamingUtil.toSnakeCase("getHTTPResponse"));
        assertEquals("hello2_world", NamingUtil.toSnakeCase("hello2World"));
        assertEquals("a1_b", NamingUtil.toSnakeCase("A1B"));
    }

    @Test
    public void toCamelCase_usesTheSameSegments() {
        assertEquals("", NamingUtil.toCamelCase(null));
        assertEquals("", NamingUtil.toCamelCase(""));
        assertEquals("", NamingUtil.toCamelCase("___"));
        assertEquals("helloWorld", NamingUtil.toCamelCase("hello_world"));
        assertEquals("helloWorld", NamingUtil.toCamelCase("hello-world"));
        assertEquals("helloWorld", NamingUtil.toCamelCase("HelloWorld"));
        assertEquals("helloWorld", NamingUtil.toCamelCase("helloWorld"));
        assertEquals("helloWorldXml", NamingUtil.toCamelCase("hello_world_xml"));
        assertEquals("xmlparser", NamingUtil.toCamelCase("XMLParser"));
        assertEquals("getHttpresponse", NamingUtil.toCamelCase("getHTTPResponse"));
        assertEquals("hello", NamingUtil.toCamelCase("_Hello_"));
        assertEquals("hello2world", NamingUtil.toCamelCase("hello_2world"));
    }
}
