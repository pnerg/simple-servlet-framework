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

import static javascalautils.TryCompanion.Try;

/**
 * @author Peter Nerg
 */
public abstract class JSONServlet extends HttpServlet implements RequestParser, ResponseBuilder {

    @Override
    protected final void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        writeResponse(resp, Try(() ->get(new Request(req))));
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

    protected Response get(Request request) throws ServletException, IOException {
        return ErrorResponseUnsupportedOperation();
    }

    protected Response delete(Request request) throws ServletException, IOException {
        return ErrorResponseUnsupportedOperation();
    }

    protected Response post(Request request) throws ServletException, IOException {
        return ErrorResponseUnsupportedOperation();
    }

    protected Response put(Request request) throws ServletException, IOException {
        return ErrorResponseUnsupportedOperation();
    }
}
