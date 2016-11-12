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

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Tests for the {@link JSONServlet}. <br>
 * This class tests the default behavior of the servlet implementation. <br>
 * That is no operations are supported.
 * @author Peter Nerg
 */
public class TestJSONServlet {
    private final HttpServletRequest servletRequest = Mockito.mock(HttpServletRequest.class);
    private final HttpServletResponse servletResponse = Mockito.mock(HttpServletResponse.class);

    private final JSONServlet testServlet = new JSONServlet() {};

    @Before
    public void before() throws Exception {
        when(servletResponse.getWriter()).thenReturn(new PrintWriter(new NullWriter()));
    }

    @Test
    public void get() throws ServletException, IOException {
        testServlet.doGet(servletRequest, servletResponse);
        verify(servletResponse).setStatus(405);
    }

    @Test
    public void delete() throws ServletException, IOException {
        testServlet.doDelete(servletRequest, servletResponse);
        verify(servletResponse).setStatus(405);
    }

    @Test
    public void put() throws ServletException, IOException {
        testServlet.doPut(servletRequest, servletResponse);
        verify(servletResponse).setStatus(405);
    }

    @Test
    public void post() throws ServletException, IOException {
        testServlet.doPost(servletRequest, servletResponse);
        verify(servletResponse).setStatus(405);
    }
}
