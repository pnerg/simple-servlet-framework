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

import javascalautils.Option;
import javascalautils.Try;

import javax.servlet.http.HttpServletRequest;

/**
 * Utilities for parsing HTTP requests
 * @author Peter Nerg.
 * @since 1.0
 */
public interface RequestParser {

    /**
     * Get the path info as specified in the URI.
     *
     * @param req The HTTP request
     * @return The path info, i.e. the last part of the URI
     */
    default Option<String> getPathInfo(HttpServletRequest req) {
        return ParserUtils.getPathInfo(req);
    }

    /**
     * Get the path info as specified in the URI. <br>
     * Returns a Success containing the path if such exists, else a Failure with a JSONServlet exception containing an error response
     * @param req The HTTP request
     * @return The path info, i.e. the last part of the URI
     * @since 1.3
     */
    default Try<String> getPathInfoAsTry(HttpServletRequest req) {
        return ParserUtils.getPathInfoAsTry(req);
    }

    /**
     * Parses an object from the json stream in the HTTP request
     * @param req The HTTP request
     * @param type The type to parse
     * @param <T> The return type
     * @return The parsed object
     */
    default <T> Try<T> fromJson(HttpServletRequest req, Class<T> type) {
        return ParserUtils.fromJson(req, type);
    }

    /**
     * Parses an object from the json stream in the HTTP request
     * @param req The HTTP request
     * @param charsetName The charset to expect
     * @param type The type to parse
     * @param <T> The return type
     * @return The parsed object
     */
    default <T> Try<T> fromJson(HttpServletRequest req, String charsetName, Class<T> type) {
        return ParserUtils.fromJson(req, charsetName, type);
    }
}
