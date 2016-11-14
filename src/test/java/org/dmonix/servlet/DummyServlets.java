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
import javax.servlet.annotation.WebServlet;
import java.io.IOException;

import static javascalautils.TryCompanion.Success;

/**
 * @author Peter Nerg
 */
public abstract class DummyServlets {

    /**
     * Simple servlet built with {@link JSONServlet} for testing purposes.
     * @author Peter Nerg
     */
    @WebServlet(name = "NoOperationsServlet", urlPatterns = {"/*"})
    public static class NoOperationsServlet extends JSONServlet {
    }

    /**
     * Simple servlet built with {@link JSONServlet} for testing purposes.
     * @author Peter Nerg
     */
    @WebServlet(name = "GetOnlyServlet", urlPatterns = {"/*"})
    public static class GetOnlyServlet extends JSONServlet {
        @Override
        protected Response get(Request request) {
            return ObjectResponse(new DummyData("peter", 69));
        }

    }

    /**
     * Simple servlet built with {@link JSONServlet} for testing purposes.
     * @author Peter Nerg
     */
    @WebServlet(name = "ServletWithAllOps", urlPatterns = {"/*"})
    public static class ServletWithAllOps extends JSONServlet {
        @Override
        protected Response get(Request request) {
            return ObjectResponse(new DummyData("peter", 69));
        }

        @Override
        protected Response delete(Request request) throws ServletException, IOException {
            return AcceptedResponse();
        }

        @Override
        protected Response put(Request request) throws ServletException, IOException {
            return AcceptedResponse();
        }

        @Override
        protected Response post(Request request) throws ServletException, IOException {
            return AcceptedResponse();
        }
    }

    /**
     * Simple servlet built with {@link JSONServlet} for testing purposes.
     * @author Peter Nerg
     */
    @WebServlet(name = "ServletWithAllTryOps", urlPatterns = {"/*"})
    public static class ServletWithAllTryOps extends JSONServlet {
        @Override
        protected Try<Response> getWithTry(Request request) {
            return Success(ObjectResponse(new DummyData("peter", 69)));
        }

        @Override
        protected Try<Response> deleteWithTry(Request request) {
            return Success(AcceptedResponse());
        }

        @Override
        protected Try<Response> putWithTry(Request request) {
            return Success(AcceptedResponse());
        }

        @Override
        protected Try<Response> postWithTry(Request request) {
            return Success(AcceptedResponse());
        }
    }
}
