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

import javascalautils.Option;
import javascalautils.Try;

import javax.servlet.http.HttpServletRequest;

/**
 * Wrapper for the {@link HttpServletRequest} class.
 * @author Peter Nerg
 * @since 1.0
 */
public final class Request {
    private final HttpServletRequest request;

    public Request(HttpServletRequest request) {
        this.request = request;
    }

    /**
     * Get the wrapped {@link HttpServletRequest}
     * @return The original HTTP request
     */
    public HttpServletRequest request() {
        return request;
    }

    /**
     * Get the path info as specified in the URI.
     *
     * @return The path info, i.e. the last part of the URI
     */
    public Option<String> getPathInfo() {
        return ParserUtils.getPathInfo(request);
    }

    /**
     * Parses an object from the json stream in the HTTP request
     * @param type The type to parse
     * @param <T> The return type
     * @return The parsed object
     */
    public <T> Try<T> fromJson(Class<T> type) {
        return fromJson("UTF-8", type);
    }

    /**
     * Parses an object from the json stream in the HTTP request
     * @param charsetName The charset to expect
     * @param type The type to parse
     * @param <T> The return type
     * @return The parsed object
     */
    public <T> Try<T> fromJson(String charsetName, Class<T> type) {
        return ParserUtils.fromJson(request, charsetName, type);
    }
}
