package com.henry.common.utils.web;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class WebUtilTest {

    @Test
    public void isHttpUrl_acceptsHttpAndHttpsSchemes() {
        assertTrue(WebUtil.isHttpUrl("http://example.com"));
        assertTrue(WebUtil.isHttpUrl("https://example.com/a?b=1"));
        assertTrue(WebUtil.isHttpUrl("HTTP://example.com"));
        assertTrue(WebUtil.isHttpUrl("HtTpS://example.com"));
        assertTrue(WebUtil.isHttpUrl("http:// a"));
    }

    @Test
    public void isHttpUrl_rejectsMissingHostOtherSchemesAndEdges() {
        assertFalse(WebUtil.isHttpUrl(null));
        assertFalse(WebUtil.isHttpUrl(""));
        assertFalse(WebUtil.isHttpUrl(" https://example.com"));
        assertFalse(WebUtil.isHttpUrl("http://"));
        assertFalse(WebUtil.isHttpUrl("https://"));
        assertFalse(WebUtil.isHttpUrl("https://   "));
        assertFalse(WebUtil.isHttpUrl("http:/example.com"));
        assertFalse(WebUtil.isHttpUrl("https:example.com"));
        assertFalse(WebUtil.isHttpUrl("javascript:alert(1)"));
        assertFalse(WebUtil.isHttpUrl("file:///tmp/a"));
        assertFalse(WebUtil.isHttpUrl("intent://scan/#Intent"));
        assertFalse(WebUtil.isHttpUrl("ftp://example.com"));
    }
}