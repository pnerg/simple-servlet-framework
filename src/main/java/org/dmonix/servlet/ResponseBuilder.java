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

import com.google.gson.Gson;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;

import static javascalautils.OptionCompanion.Some;
import static javax.servlet.http.HttpServletResponse.*;

/**
 * Response object containing the response to be sent to the client.
 *
 * @author Peter Nerg
 * @since 1.0
 */
public interface ResponseBuilder {
    Gson gson = new Gson();

    /**
     * Creates an empty response with only a response code
     *
     * @param responseCode
     *            The HTTP response code
     * @return The response object
     */
    default Response EmptyResponse(int responseCode) {
        return new Response(responseCode, "");
    }

    /**
     * Creates an empty response with only 202 - Accepted
     *
     * @return The response object
     */
    default Response AcceptedResponse() {
        return EmptyResponse(SC_ACCEPTED);
    }

    /**
     * Creates an empty response with only 201 - Created
     *
     * @return The response object
     */
    default Response CreatedResponse() {
        return EmptyResponse(SC_CREATED);
    }

    /**
     * Creates a response where the provided object will be converted to json. <br>
     * Will by default use the charset <tt>UTF-8</tt>
     * @param object
     *            The response object/message
     * @return The response object
     */
    default Response ObjectResponse(Object object) {
        return ObjectResponse(object, "UTF-8");
    }

    /**
     * Creates a response where the provided object will be converted to json.
     *
     * @param object
     *            The response object/message
     * @param charEncoding The character encoding to use
     * @return The response object
     */
    default Response ObjectResponse(Object object, String charEncoding) {
        return new Response(SC_OK, gson.toJson(object), Some("application/json;charset="+charEncoding), Some(charEncoding));
    }

    /**
     * Creates an error response with a internal error code.
     *
     * @param t The underlying issue
     * @return The response object
     */
    default Response ErrorResponse(Throwable t) {
        return ErrorResponse(SC_INTERNAL_SERVER_ERROR, t.getMessage());
    }

    /**
     * Creates an error with a provided error code
     *
     * @param responseCode
     *            The HTTP response code
     * @param message
     * @return The response object
     */
    default Response ErrorResponse(int responseCode, String message) {
        return new Response(responseCode, message);
    }

    /**
     * Creates an error with a 404 - Not found
     *
     * @param message
     * @return The response object
     */
    default Response ErrorResponseNotFound(String message) {
        return ErrorResponse(SC_NOT_FOUND, message);
    }

    /**
     * Creates an error with code <tt>400</tt> and the message that the path is missing
     * @return The response object
     */
    default Response ErrorResponseMissingPath() {
        return ErrorResponse(SC_BAD_REQUEST, "Missing path");
    }

    /**
     * Creates an error with code <tt>403</tt> and the message that the path already exists
     * @param path
     * @return The response object
     */
    default Response ErrorResponseResourceAlreadyExists(String path) {
        return ErrorResponse(SC_FORBIDDEN, "The resource depicted by ["+path+"] already exists");
    }

    /**
     * Write the response to the client.
     *
     * @param resp
     * @param response
     * @throws IOException
     */
    default void writeResponse(HttpServletResponse resp, Response response) throws IOException {
        //set the response code, e.g. 200
        resp.setStatus(response.responseCode);

        //set all headers
        response.headers().forEach((k,v) -> resp.setHeader(k,v));

        //set the optional content-type
        response.mediaType.forEach(mt -> resp.setContentType(mt));

        //set the optional char-encoding
        response.charEncoding.forEach(ce -> resp.setCharacterEncoding(ce));

        //write any body data
        resp.getWriter().write(response.message);
    }

}
