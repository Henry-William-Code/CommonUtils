package com.henry.common.utils.wildcard;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class WildcardUtilTest {

    @Test
    public void matches_nullReturnsFalseWithoutThrowing() {
        assertFalse(WildcardUtil.matches(null, "a"));
        assertFalse(WildcardUtil.matches("a", null));
        assertFalse(WildcardUtil.matches(null, null));
        assertFalse(WildcardUtil.matches(null, "\\"));
        assertFalse(WildcardUtil.matches(null, "\\a"));
        assertFalse(WildcardUtil.matches(null, ""));
    }

    @Test
    public void matches_emptyPatternStarAndQuestion() {
        assertTrue(WildcardUtil.matches("", ""));
        assertFalse(WildcardUtil.matches("a", ""));
        assertFalse(WildcardUtil.matches(" ", ""));
        assertTrue(WildcardUtil.matches("", "*"));
        assertTrue(WildcardUtil.matches("a/b", "*"));
        assertTrue(WildcardUtil.matches(" ", "*"));
        assertTrue(WildcardUtil.matches("*", "*"));
        assertTrue(WildcardUtil.matches("a", "?"));
        assertTrue(WildcardUtil.matches("/", "?"));
        assertTrue(WildcardUtil.matches(" ", "?"));
        assertFalse(WildcardUtil.matches("", "?"));
        assertFalse(WildcardUtil.matches("ab", "?"));
        assertTrue(WildcardUtil.matches("ab", "??"));
        assertTrue(WildcardUtil.matches("a/b", "a?b"));
        assertFalse(WildcardUtil.matches("ac", "a?b"));
    }

    @Test
    public void matches_isCaseSensitiveAndDoesNotTrim() {
        assertFalse(WildcardUtil.matches("A", "a"));
        assertFalse(WildcardUtil.matches("a", "A"));
        assertTrue(WildcardUtil.matches("AbC", "AbC"));
        assertFalse(WildcardUtil.matches(" a", "a"));
        assertFalse(WildcardUtil.matches("a", " a"));
        assertFalse(WildcardUtil.matches("a ", "a"));
        assertTrue(WildcardUtil.matches("a ", "a "));
        assertTrue(WildcardUtil.matches("a b", "a b"));
        assertFalse(WildcardUtil.matches("ab", "a b"));
        assertTrue(WildcardUtil.matches("a\nb", "a*b"));
        assertTrue(WildcardUtil.matches("a\nb", "a?b"));
    }

    @Test
    public void matches_starMatchesSlashAndConsecutiveStarsAreLiteralSafe() {
        assertTrue(WildcardUtil.matches("a/b.txt", "*.txt"));
        assertTrue(WildcardUtil.matches(".txt", "*.txt"));
        assertTrue(WildcardUtil.matches("a.txt", "*.txt"));
        assertFalse(WildcardUtil.matches("a.txtx", "*.txt"));
        assertFalse(WildcardUtil.matches("a.tx", "*.txt"));
        assertTrue(WildcardUtil.matches("ab", "a**b"));
        assertTrue(WildcardUtil.matches("", "**"));
        assertTrue(WildcardUtil.matches("ab", "**"));
        assertTrue(WildcardUtil.matches("xaab", "*a?b"));
        assertFalse(WildcardUtil.matches("ab", "*a?b"));
        assertTrue(WildcardUtil.matches("a", "a*"));
        assertTrue(WildcardUtil.matches("abc", "a*"));
        assertTrue(WildcardUtil.matches("ba", "*a"));
        assertTrue(WildcardUtil.matches("aa", "a*a"));
        assertTrue(WildcardUtil.matches("aba", "a*a"));
        assertFalse(WildcardUtil.matches("a", "a*a"));
        assertTrue(WildcardUtil.matches("aXbYc", "a*b*c"));
        assertFalse(WildcardUtil.matches("ab", "a*b*c"));
        assertTrue(WildcardUtil.matches("a+", "a+"));
        assertFalse(WildcardUtil.matches("aaa", "a+"));
        assertTrue(WildcardUtil.matches(".", "."));
        assertFalse(WildcardUtil.matches("a", "."));
        assertTrue(WildcardUtil.matches("[a]", "[a]"));
        assertFalse(WildcardUtil.matches("a", "[a]"));
        assertFalse(WildcardUtil.matches("\uD83D\uDE00", "?"));
        assertTrue(WildcardUtil.matches("\uD83D\uDE00", "??"));
        assertTrue(WildcardUtil.matches("\uD83D\uDE00", "*"));
    }

    @Test
    public void matches_backslashEscapesOnlyStarQuestionAndSlash() {
        assertTrue(WildcardUtil.matches("a*b", "a\\*b"));
        assertFalse(WildcardUtil.matches("ab", "a\\*b"));
        assertFalse(WildcardUtil.matches("axb", "a\\*b"));
        assertTrue(WildcardUtil.matches("*", "\\*"));
        assertFalse(WildcardUtil.matches("a", "\\*"));
        assertTrue(WildcardUtil.matches("a?b", "a\\?b"));
        assertFalse(WildcardUtil.matches("axb", "a\\?b"));
        assertTrue(WildcardUtil.matches("a\\b", "a\\\\b"));
        assertTrue(WildcardUtil.matches("*?\\", "\\*\\?\\\\"));
        assertTrue(WildcardUtil.matches("\\abc", "\\\\*"));
        assertFalse(WildcardUtil.matches("abc", "\\\\*"));
        assertInvalid("\\a");
        assertInvalid("\\n");
        assertInvalid("\\");
        assertInvalid("a\\");
        assertInvalid("a\\b");
        assertInvalid("*\\");
    }

    @Test
    public void escape_makesPatternMatchOnlyThatLiteral() {
        assertEquals("", WildcardUtil.escape(null));
        assertEquals("", WildcardUtil.escape(""));
        assertEquals("abc", WildcardUtil.escape("abc"));
        assertEquals("a\\*b\\?c\\\\d", WildcardUtil.escape("a*b?c\\d"));
        String[] literals = new String[] {
                "",
                "a",
                "*",
                "?",
                "\\",
                "*?",
                "a*b?c\\",
                "a/b",
                " ",
                "A",
                "你",
                "a\nb",
                "\u0000",
                "*.txt",
                "a+"
        };
        for (int i = 0; i < literals.length; i++) {
            String literal = literals[i];
            assertTrue(WildcardUtil.matches(literal, WildcardUtil.escape(literal)));
            assertFalse(WildcardUtil.matches(literal + "x", WildcardUtil.escape(literal)));
            assertFalse(WildcardUtil.matches("x" + literal, WildcardUtil.escape(literal)));
            if (literal.length() > 0) {
                assertFalse(WildcardUtil.matches(
                        literal.substring(0, literal.length() - 1),
                        WildcardUtil.escape(literal)));
            }
        }
        assertFalse(WildcardUtil.matches("a", WildcardUtil.escape(null)));
        assertTrue(WildcardUtil.matches("", WildcardUtil.escape(null)));
        assertFalse(WildcardUtil.matches("*", WildcardUtil.escape("\\*")));
        assertTrue(WildcardUtil.matches("\\*", WildcardUtil.escape("\\*")));
    }

    private static void assertInvalid(String pattern) {
        try {
            WildcardUtil.matches("a", pattern);
            fail("expected IllegalArgumentException for " + pattern);
        } catch (IllegalArgumentException expected) {
            // 非法模式抛异常，不是不匹配。
        }
        try {
            WildcardUtil.matches("", pattern);
            fail("expected IllegalArgumentException for empty text " + pattern);
        } catch (IllegalArgumentException expected) {
            // 文本为空时非法模式仍然抛异常。
        }
    }
}
