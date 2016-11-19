/**
 * Copyright 2016 Peter Nerg
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.dmonix.servlet;

import javascalautils.Try;
import org.junit.Test;
import org.mockito.Mockito;

import javax.servlet.http.HttpServletRequest;

import static org.mockito.Mockito.when;

/**
 * Test the class {@link Request}
 * @author Peter Nerg
 */
public class TestRequest extends BaseAssert {
    private final HttpServletRequest servletRequest = Mockito.mock(HttpServletRequest.class);
    private final Request request = new Request(servletRequest);

    @Test
    public void request() {
        assertNotNull(request.request());
    }

    @Test
    public void getPathInfo() {
        when(servletRequest.getPathInfo()).thenReturn("/the-path");
        assertSome("the-path", request.getPathInfo());
    }

    @Test
    public void getPathInfoAsTry() {
        when(servletRequest.getPathInfo()).thenReturn("/the-path");
        assertSuccess("the-path", request.getPathInfoAsTry());
    }

    @Test
    public void fromJson() throws Throwable {
        when(servletRequest.getInputStream()).thenReturn(new MockServletInputStream("{\"name\":\"Peter\", \"id\":666}"));

        Try<DummyData> result = request.fromJson(DummyData.class);
        assertSuccess(result);
        result.forEach(r -> {
            assertEquals("Peter", r.name);
            assertEquals(666, r.id);
        });
    }

}
