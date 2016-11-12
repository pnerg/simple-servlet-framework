package org.dmonix.servlet;

import org.junit.Test;

import static javascalautils.OptionCompanion.Some;

/**
 * Test the class {@link Response}
 * @author Peter Nerg
 */
public class TestResponse extends BaseAssert {

    private final Response response = new Response(666, "Evil is about");

    @Test
    public void responseCode() {
        assertEquals(666, response.responseCode);
    }

    @Test
    public void message() {
        assertEquals("Evil is about", response.message);
    }

    @Test
    public void mediaType() {
        Response rsp = new Response(666, "Evil is about", Some("text/plain"));
        assertSome("text/plain", rsp.mediaType);
    }

    @Test
    public void mediaType_notSet() {
        assertNone(response.mediaType);
    }

    @Test
    public void headers_empty() {
        assertTrue(response.headers().isEmpty());
    }

    @Test
    public void headers() {
        response.addHeader("Token", "wohoo");
        assertEquals(1, response.headers().size());
        assertEquals("wohoo", response.headers().get("Token"));
    }

}
