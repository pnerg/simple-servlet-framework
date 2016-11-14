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
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Tests for the {@link JSONServlet}. <br>
 * This class tests the default behavior of the servlet implementation. <br>
 * That is no operations are supported.
 * @author Peter Nerg
 */
public class TestJSONServlet extends BaseAssert {
    private final HttpServletRequest servletRequest = Mockito.mock(HttpServletRequest.class);
    private final HttpServletResponse servletResponse = Mockito.spy(HttpServletResponse.class);
    private final Request request = new Request(servletRequest);

    private final JSONServlet testServlet = new JSONServlet() {};

    @Before
    public void before() throws Exception {
        when(servletResponse.getWriter()).thenReturn(new PrintWriter(new NullWriter()));
    }

    @Test
    public void doGet() throws ServletException, IOException {
        testServlet.doGet(servletRequest, servletResponse);
        verify(servletResponse).setStatus(405);
    }

    @Test
    public void get() throws ServletException, IOException {
        assertNotSupported(testServlet.get(request));
    }

    @Test
    public void getWithTry() {
        assertNotSupported(testServlet.getWithTry(request));
    }

    @Test
    public void doDelete() throws ServletException, IOException {
        testServlet.doDelete(servletRequest, servletResponse);
        verify(servletResponse).setStatus(405);
    }

    @Test
    public void delete() throws ServletException, IOException {
        assertNotSupported(testServlet.delete(request));
    }

    @Test
    public void deleteWithTry() {
        assertNotSupported(testServlet.deleteWithTry(request));
    }

    @Test
    public void doPut() throws ServletException, IOException {
        testServlet.doPut(servletRequest, servletResponse);
        verify(servletResponse).setStatus(405);
    }

    @Test
    public void put() throws ServletException, IOException {
        assertNotSupported(testServlet.put(request));
    }

    @Test
    public void doPost() throws ServletException, IOException {
        testServlet.doPost(servletRequest, servletResponse);
        verify(servletResponse).setStatus(405);
    }

    @Test
    public void post() throws ServletException, IOException {
        assertNotSupported(testServlet.post(request));
    }

    @Test
    public void postWithTry() {
        assertNotSupported(testServlet.postWithTry(request));
    }

    @Test
    public void doOptions() throws ServletException, IOException {
        testServlet.doOptions(servletRequest, servletResponse);
        verify(servletResponse).setHeader(eq("Allow"), anyString());
    }

    private void assertNotSupported(Response response) {
        assertEquals(405, response.responseCode);
    }

    private void assertNotSupported(Try<Response> response) {
        assertSuccess(response);
        response.forEach(this::assertNotSupported);
    }
}
