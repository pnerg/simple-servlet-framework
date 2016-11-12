/**
 *  Copyright 2016 Peter Nerg
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.dmonix.servlet;

import javascalautils.Try;
import org.junit.Test;
import org.mockito.Mockito;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

import static org.mockito.Mockito.when;

/**
 * Test the class {@link RequestParser}
 * @author Peter Nerg
 */
public class TestRequestParser extends BaseAssert implements RequestParser  {

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

        Try<DummyData> result = fromJson(req, DummyData.class);
        assertSuccess(result);
        result.forEach(r -> {
            assertEquals("Peter", r.name);
            assertEquals(666, r.id);
        });
    }

    @Test
    public void fromJson_withCharset() throws Throwable {
        when(req.getInputStream()).thenReturn(new MockServletInputStream("{\"name\":\"Peter\", \"id\":666}"));

        Try<DummyData> result = fromJson(req, "UTF-8", DummyData.class);
        assertSuccess(result);
        result.forEach(r -> {
            assertEquals("Peter", r.name);
            assertEquals(666, r.id);
        });
    }

    @Test
    public void fromJson_failParse() throws IOException {
        when(req.getInputStream()).thenReturn(null);

        Try<DummyData> result = fromJson(req, DummyData.class);
        assertFailure(result);
    }

}
