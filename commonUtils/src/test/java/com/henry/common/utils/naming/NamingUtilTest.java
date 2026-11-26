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

    @Test
    public void toKebabCase_usesSnakeSegmentsWithHyphen() {
        assertEquals("", NamingUtil.toKebabCase(null));
        assertEquals("", NamingUtil.toKebabCase(""));
        assertEquals("", NamingUtil.toKebabCase("---"));
        assertEquals("", NamingUtil.toKebabCase(" _- "));
        assertEquals("hello-world", NamingUtil.toKebabCase("HelloWorld"));
        assertEquals("hello-world", NamingUtil.toKebabCase("helloWorld"));
        assertEquals("hello-world", NamingUtil.toKebabCase("hello_world"));
        assertEquals("hello-world", NamingUtil.toKebabCase("hello-world"));
        assertEquals("hello-world", NamingUtil.toKebabCase("hello world"));
        assertEquals("hello-world", NamingUtil.toKebabCase("__hello__world__"));
        assertEquals("xmlparser", NamingUtil.toKebabCase("XMLParser"));
        assertEquals("get-httpresponse", NamingUtil.toKebabCase("getHTTPResponse"));
        assertEquals("hello2-world", NamingUtil.toKebabCase("hello2World"));
        assertEquals("a1-b", NamingUtil.toKebabCase("A1B"));
        String[] samples = new String[] {
                null, "", "HelloWorld", "helloWorld", "XMLParser", "getHTTPResponse",
                "hello-world", "__hello__world__", "  Hello  World  ", "A1B"
        };
        for (int i = 0; i < samples.length; i++) {
            String snake = NamingUtil.toSnakeCase(samples[i]);
            assertEquals(snake.replace('_', '-'), NamingUtil.toKebabCase(samples[i]));
        }
        assertEquals("hello_world", NamingUtil.toSnakeCase("HelloWorld"));
        assertEquals("helloWorld", NamingUtil.toCamelCase("hello-world"));
    }

    @Test
    public void toPascalCase_capitalizesEverySegment() {
        assertEquals("", NamingUtil.toPascalCase(null));
        assertEquals("", NamingUtil.toPascalCase(""));
        assertEquals("", NamingUtil.toPascalCase("---"));
        assertEquals("", NamingUtil.toPascalCase(" _- "));
        assertEquals("HelloWorld", NamingUtil.toPascalCase("hello_world"));
        assertEquals("HelloWorld", NamingUtil.toPascalCase("hello-world"));
        assertEquals("HelloWorld", NamingUtil.toPascalCase("HelloWorld"));
        assertEquals("HelloWorld", NamingUtil.toPascalCase("helloWorld"));
        assertEquals("HelloWorld", NamingUtil.toPascalCase("hello world"));
        assertEquals("Xmlparser", NamingUtil.toPascalCase("XMLParser"));
        assertEquals("GetHttpresponse", NamingUtil.toPascalCase("getHTTPResponse"));
        assertEquals("Hello", NamingUtil.toPascalCase("_Hello_"));
        assertEquals("Hello2world", NamingUtil.toPascalCase("hello_2world"));
        assertEquals("A1B", NamingUtil.toPascalCase("A1B"));
        String[] samples = new String[] {
                null, "", "HelloWorld", "helloWorld", "XMLParser", "getHTTPResponse",
                "hello-world", "hello_world", "__hello__world__", "  Hello  World  ", "A1B", "hello_2world"
        };
        for (int i = 0; i < samples.length; i++) {
            String camel = NamingUtil.toCamelCase(samples[i]);
            String pascal = NamingUtil.toPascalCase(samples[i]);
            if (camel.length() == 0) {
                assertEquals("", pascal);
                continue;
            }
            char first = camel.charAt(0);
            if (first >= 'a' && first <= 'z') {
                first = (char) (first - ('a' - 'A'));
            }
            assertEquals(first + camel.substring(1), pascal);
        }
        assertEquals("helloWorld", NamingUtil.toCamelCase("hello_world"));
        assertEquals("hello_world", NamingUtil.toSnakeCase("HelloWorld"));
        assertEquals("hello-world", NamingUtil.toKebabCase("HelloWorld"));
    }
}
