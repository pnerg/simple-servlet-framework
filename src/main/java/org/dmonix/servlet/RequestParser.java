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
import javascalautils.Option;
import javascalautils.Try;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStreamReader;

import static javascalautils.OptionCompanion.Option;
import static javascalautils.TryCompanion.Try;

/**
 * Utilities for parsing HTTP requests
 * @author Peter Nerg.
 * @since 1.0
 */
public interface RequestParser {

    Gson gson = new Gson();

    /**
     * Get the path info as specified in the URI.
     *
     * @param req The HTTP request
     * @return The path info, i.e. the last part of the URI
     */
    default Option<String> getPathInfo(HttpServletRequest req) {
        return Option(req.getPathInfo()).map(p -> p.substring(1));
    }

    /**
     * Parses an object from the json stream in the HTTP request
     * @param req The HTTP request
     * @param type The type to parse
     * @param <T> The return type
     * @return The parsed object
     */
    default <T> Try<T> fromJson(HttpServletRequest req, Class<T> type) {
        return fromJson(req, "UTF-8", type);
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
        return Try(() -> gson.fromJson(new InputStreamReader(req.getInputStream(), charsetName), type));
    }
}
