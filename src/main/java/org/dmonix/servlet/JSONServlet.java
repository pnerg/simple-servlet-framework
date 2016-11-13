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

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import static javascalautils.TryCompanion.Try;

/**
 * Extends the standard {@link HttpServlet} by providing a nicer way to read/write request/response data.
 * @author Peter Nerg
 * @since 1.0
 */
public abstract class JSONServlet extends HttpServlet implements RequestParser, ResponseBuilder {

    @Override
    protected final void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        writeResponse(resp, Try(() -> get(new Request(req))));
    }

    @Override
    protected final void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        writeResponse(resp, Try(() -> delete(new Request(req))));
    }

    @Override
    protected final void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        writeResponse(resp, Try(() -> post(new Request(req))));
    }

    @Override
    protected final void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        writeResponse(resp, Try(() -> put(new Request(req))));
    }

    @Override
    protected final void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //get all methods the sub class is implementing
        Set<String> methods = Arrays.stream(getClass().getDeclaredMethods()).map(Method::getName).collect(Collectors.toSet());

        StringBuilder allow = new StringBuilder();
        //OPTIONS and TRACE are always enabled
        allow.append("OPTIONS");
        allow.append(", ");
        allow.append("TRACE");
        allow.append(", ");
        //if GET is supported then HEAD is also supported
        if (methods.contains("get")) {
            allow.append("GET");
            allow.append(", ");
            allow.append("HEAD");
        }
        if (methods.contains("post")) {
            allow.append(", ");
            allow.append("POST");
        }
        if (methods.contains("put")) {
            allow.append(", ");
            allow.append("PUT");
        }
        if (methods.contains("delete")) {
            allow.append(", ");
            allow.append("DELETE");
        }

        resp.setHeader("Allow", allow.toString());
    }

    /**
     * Implements the <tt>GET</tt> method. <br>
     * Should be override by servlets needing to support this method. <br>
     * If not override a default <tt>405 - Not supported</tt> is returned.
     * @param request The request data
     * @return The response data.
     * @throws ServletException
     * @throws IOException
     */
    protected Response get(Request request) throws ServletException, IOException {
        return ErrorResponseUnsupportedOperation();
    }

    /**
     * Implements the <tt>DELETE</tt> method. <br>
     * Should be override by servlets needing to support this method. <br>
     * If not override a default <tt>405 - Not supported</tt> is returned.
     * @param request The request data
     * @return The response data.
     * @throws ServletException
     * @throws IOException
     */
    protected Response delete(Request request) throws ServletException, IOException {
        return ErrorResponseUnsupportedOperation();
    }

    /**
     * Implements the <tt>POST</tt> method. <br>
     * Should be override by servlets needing to support this method. <br>
     * If not override a default <tt>405 - Not supported</tt> is returned.
     * @param request The request data
     * @return The response data.
     * @throws ServletException
     * @throws IOException
     */
    protected Response post(Request request) throws ServletException, IOException {
        return ErrorResponseUnsupportedOperation();
    }

    /**
     * Implements the <tt>PUT</tt> method. <br>
     * Should be override by servlets needing to support this method. <br>
     * If not override a default <tt>405 - Not supported</tt> is returned.
     * @param request The request data
     * @return The response data.
     * @throws ServletException
     * @throws IOException
     */
    protected Response put(Request request) throws ServletException, IOException {
        return ErrorResponseUnsupportedOperation();
    }
}
