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
        writeResponse(resp, getWithTry(new Request(req)));
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
        //if GET is supported then HEAD is also supported
        if (isOperationSupported(methods, "get")) {
            allow.append(", ");
            allow.append("GET");
            allow.append(", ");
            allow.append("HEAD");
        }
        if (isOperationSupported(methods, "post")) {
            allow.append(", ");
            allow.append("POST");
        }
        if (isOperationSupported(methods, "put")) {
            allow.append(", ");
            allow.append("PUT");
        }
        if (isOperationSupported(methods, "delete")) {
            allow.append(", ");
            allow.append("DELETE");
        }

        resp.setHeader("Allow", allow.toString());
    }

    private boolean isOperationSupported(Set<String> methods, String operation) {
        return methods.contains(operation) || methods.contains(operation+"WithTry");
    }

    /**
     * Implements the <tt>GET</tt> method. <br>
     * Should be overridden by servlets needing to support this method. <br>
     * If not override a default <tt>405 - Not supported</tt> is returned.
     * @param request The request data
     * @return The response data.
     * @throws ServletException Any exception during processing
     * @throws IOException Any exception during processing
     */
    protected Response get(Request request) throws ServletException, IOException {
        return ErrorResponseUnsupportedOperation();
    }

    /**
     * Implements the <tt>GET</tt> method. <br>
     * Should be overridden by servlets needing to support this method. <br>
     * If not overridden this method invokes {@link #get(Request)}. <br>
     * In other words this method takes precedence over {@link #get(Request)} so overriding both will not make sense.
     * @param request The request data
     * @return The response data in case of Success, else a Failure
     * @since 1.1
     * @see #get(Request)
     */
    protected Try<Response> getWithTry(Request request) {
        return Try(() -> get(request));
    }

    /**
     * Implements the <tt>DELETE</tt> method. <br>
     * Should be overridden by servlets needing to support this method. <br>
     * If not overridden a default <tt>405 - Not supported</tt> is returned.
     * @param request The request data
     * @return The response data.
     * @throws ServletException Any exception during processing
     * @throws IOException Any exception during processing
     */
    protected Response delete(Request request) throws ServletException, IOException {
        return ErrorResponseUnsupportedOperation();
    }

    /**
     * Implements the <tt>DELETE</tt> method. <br>
     * Should be overridden by servlets needing to support this method. <br>
     * If not overridden this method invokes {@link #delete(Request)}. <br>
     * In other words this method takes precedence over {@link #delete(Request)} so overriding both will not make sense.
     * @param request The request data
     * @return The response data in case of Success, else a Failure
     * @since 1.1
     * @see #get(Request)
     */
    protected Try<Response> deleteWithTry(Request request) {
        return Try(() -> delete(request));
    }

    /**
     * Implements the <tt>POST</tt> method. <br>
     * Should be overridden by servlets needing to support this method. <br>
     * If not overridden a default <tt>405 - Not supported</tt> is returned.
     * @param request The request data
     * @return The response data.
     * @throws ServletException Any exception during processing
     * @throws IOException Any exception during processing
     */
    protected Response post(Request request) throws ServletException, IOException {
        return ErrorResponseUnsupportedOperation();
    }

    /**
     * Implements the <tt>POST</tt> method. <br>
     * Should be overridden by servlets needing to support this method. <br>
     * If not overridden this method invokes {@link #post(Request)}. <br>
     * In other words this method takes precedence over {@link #post(Request)} so overriding both will not make sense.
     * @param request The request data
     * @return The response data in case of Success, else a Failure
     * @since 1.1
     * @see #get(Request)
     */
    protected Try<Response> postWithTry(Request request) {
        return Try(() -> post(request));
    }

    /**
     * Implements the <tt>PUT</tt> method. <br>
     * Should be overridden by servlets needing to support this method. <br>
     * If not overridden a default <tt>405 - Not supported</tt> is returned.
     * @param request The request data
     * @return The response data.
     * @throws ServletException Any exception during processing
     * @throws IOException Any exception during processing
     */
    protected Response put(Request request) throws ServletException, IOException {
        return ErrorResponseUnsupportedOperation();
    }

    /**
     * Implements the <tt>PUT</tt> method. <br>
     * Should be overridden by servlets needing to support this method. <br>
     * If not overridden this method invokes {@link #put(Request)}. <br>
     * In other words this method takes precedence over {@link #put(Request)} so overriding both will not make sense.
     * @param request The request data
     * @return The response data in case of Success, else a Failure
     * @since 1.1
     * @see #get(Request)
     */
    protected Try<Response> putWithTry(Request request) {
        return Try(() -> put(request));
    }
}
