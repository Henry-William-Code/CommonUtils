package com.henry.common.utils.color;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class ColorUtilTest {

    @Test
    public void parse_expandsShortFormsAndKeepsOpaqueBlack() {
        assertEquals(0xFF00FF88, ColorUtil.parse("#0F8"));
        assertEquals(0xFF00FF88, ColorUtil.parse("#0f8"));
        assertEquals(0x8800FF88, ColorUtil.parse("#80F8"));
        assertEquals(0x8800FF88, ColorUtil.parse("#80f8"));
        assertEquals(0xFF000000, ColorUtil.parse("#000"));
        assertEquals(0xFFFFFFFF, ColorUtil.parse("#FFF"));
        assertEquals(0xFFFFFFFF, ColorUtil.parse("#fff"));
        assertEquals(0xFFFF0000, ColorUtil.parse("#F00"));
        assertEquals(0x88FF0000, ColorUtil.parse("#8F00"));
        assertEquals(0xFF000000, ColorUtil.parse("#000000"));
        assertEquals(0xFF00FF00, ColorUtil.parse("#00FF00"));
        assertEquals(0xFF00FF00, ColorUtil.parse("#00ff00"));
        assertEquals(0x80FF00FF, ColorUtil.parse("#80FF00FF"));
        assertEquals(0x00ABCDEF, ColorUtil.parse("#00abcdef"));
        assertEquals(0xFF000000, ColorUtil.parse("#FF000000"));
        assertEquals(0, ColorUtil.parse("#00000000"));
        assertEquals(0xFF000000, ColorUtil.parse("#000"));
    }

    @Test
    public void toHex_alwaysWritesEightUppercaseDigits() {
        assertEquals("#00000000", ColorUtil.toHex(0));
        assertEquals("#FF000000", ColorUtil.toHex(0xFF000000));
        assertEquals("#FF00FF88", ColorUtil.toHex(0xFF00FF88));
        assertEquals("#00ABCDEF", ColorUtil.toHex(0x00ABCDEF));
        assertEquals("#FFFFFFFF", ColorUtil.toHex(-1));
        assertEquals("#FF000000", ColorUtil.toHex(ColorUtil.parse("#000")));
        assertEquals("#FF00FF88", ColorUtil.toHex(ColorUtil.parse("#0F8")));
        assertEquals("#8800FF88", ColorUtil.toHex(ColorUtil.parse("#80f8")));
        assertEquals(9, ColorUtil.toHex(0x00abcdef).length());
    }

    @Test
    public void parseAndToHex_roundTripEverySample() {
        int[] samples = new int[] {
                0,
                1,
                -1,
                255,
                0xFF000000,
                0x00FF00FF,
                0x80000000,
                0x01020304,
                0xFF00FF88,
                Integer.MIN_VALUE,
                Integer.MAX_VALUE
        };
        for (int i = 0; i < samples.length; i++) {
            assertEquals(samples[i], ColorUtil.parse(ColorUtil.toHex(samples[i])));
        }
        for (int i = 0; i < 256; i++) {
            assertEquals(i, ColorUtil.parse(ColorUtil.toHex(i)));
            assertEquals(i << 24, ColorUtil.parse(ColorUtil.toHex(i << 24)));
        }
    }

    @Test
    public void parse_rejectsNullEmptyPrefixLengthAndNonHex() {
        String[] invalid = new String[] {
                null,
                "",
                " ",
                "  ",
                "#",
                "#F",
                "#FF",
                "#FFFFF",
                "#FFFFFFF",
                "#FFFFFFFFF",
                "FFF",
                "#FFF ",
                " #FFF",
                "0xFFF",
                "0xFFFFFF",
                "0XFFFFFF",
                "#GGG",
                "#gg0000",
                "#12GG56",
                "red",
                "black",
                "rgb(0,0,0)",
                "argb(255,0,0,0)",
                "#0xFFF",
                "\uFF03FFF",
                "#FF FFFF",
                "#FF\nFFFF"
        };
        for (int i = 0; i < invalid.length; i++) {
            try {
                ColorUtil.parse(invalid[i]);
                fail("expected IllegalArgumentException for " + invalid[i]);
            } catch (IllegalArgumentException expected) {
                // 非法颜色不返回 0，也不截断。
            }
        }
    }
}
