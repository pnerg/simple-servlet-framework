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

import static javax.servlet.http.HttpServletResponse.SC_BAD_REQUEST;

/**
 * Special exception to be used in Try clauses that are failed. <br>
 * The point is to be able to return a Failure containing this exception and get an automated rendering out the
 * response sent to the client with the response code in the exception. <br>
 * Use cases are failed validation where one wants to return a client error code but still perform the validation using
 * Try clauses.
 * @author Peter Nerg
 * @since 1.3
 */
public final class JSONServletException extends Exception {

    static final long serialVersionUID = 923874239472389472L;
    final Response response;

    public JSONServletException(Response response) {
        this.response = response;
    }

    /**
     * Creates an exception containing error code <tt>400</tt> with the message <tt>missing path</tt>
     * @return The exception
     */
    public static JSONServletException MissingPathException() {
        return new JSONServletException(new Response(SC_BAD_REQUEST, "Missing path"));
    }

}
