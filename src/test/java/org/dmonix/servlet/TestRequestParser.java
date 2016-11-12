package org.dmonix.servlet;

import javascalautils.Try;
import junitextensions.OptionAssert;
import junitextensions.TryAssert;
import org.junit.Test;
import org.mockito.Mockito;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

import static org.mockito.Mockito.when;

/**
 * Test the class {@link RequestParser}
 * @author Peter Nerg
 */
public class TestRequestParser extends BaseAssert implements RequestParser, OptionAssert, TryAssert {

    private final HttpServletRequest req = Mockito.mock(HttpServletRequest.class);

    @Test
    public void getPathInfo_noPath() {
        when(req.getPathInfo()).thenReturn(null);

        assertNone(getPathInfo(req));
    }

    @Test
    public void getPathInfo_withPath() {
        when(req.getPathInfo()).thenReturn("/the-path");

        assertSome("the-path", getPathInfo(req));
    }

    @Test
    public void fromJson() throws Throwable {
        when(req.getInputStream()).thenReturn(new MockServletInputStream("{\"name\":\"Peter\", \"id\":666}"));

        Try<TestObj> result = fromJson(req, TestObj.class);
        assertSuccess(result);
        result.forEach(r -> {
            assertEquals("Peter", r.name);
            assertEquals(666, r.id);
        });
    }

    @Test
    public void fromJson_failParse() throws IOException {
        when(req.getInputStream()).thenReturn(null);

        Try<TestObj> result = fromJson(req, TestObj.class);
        assertFailure(result);
    }

    static class TestObj {
        private final String name;
        private final int id;
        TestObj(String name, int id) {
            this.name = name;
            this.id = id;
        }
    }
}
