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

import javax.servlet.http.Cookie;
import java.util.*;

import static javascalautils.OptionCompanion.None;
import static javascalautils.OptionCompanion.Some;

/**
 * Response object containing the response to be sent to the client.
 *
 * @author Peter Nerg
 * @since 1.0
 */
public class Response {
    /** The HTTP response code for the response.*/
    public final int responseCode;

    /** The message/body for the response.*/
    public final String message;

    /** The optional media type for the response for the response.*/
    public final Option<String> mediaType;

    /** The optional character encoding for the response.*/
    public final Option<String> charEncoding;

    /** The headers for the response.*/
    private final Map<String, String> headers = new HashMap<>();

    /** The cookies for the response.*/
    private final Set<Cookie> cookies = new HashSet<>();

    /**
     * Creates an instance
     *
     * @param responseCode
     *            The HTTP response code
     * @param message
     *            The body of the response
     */
    public Response(int responseCode, String message) {
        this(responseCode, message, None(), Some("UTF-8"));
    }

    /**
     * Creates an instance
     *
     * @param responseCode
     *            The HTTP response code
     * @param message
     *            The body of the response
     * @param mediaType
     *            An optional media type of the response data
     * @param charEncoding
     *            An optional character encoding of the response data
     */
    Response(int responseCode, String message, Option<String> mediaType, Option<String> charEncoding) {
        this.responseCode = responseCode;
        this.message = message;
        this.mediaType = mediaType;
        this.charEncoding = charEncoding;
    }

    /**
     * Add a header to put into the HTTP response.
     * @param name The name of the header
     * @param value The value of the header
     */
    public void addHeader(String name, String value) {
        headers.put(name, value);
    }

    /**
     * Get all the headers to use for the HTTP response.
     * @return The headers
     */
    public Map<String, String> headers() {
        return Collections.unmodifiableMap(headers);
    }

    /**
     * Adds a cookie to the HTTP response.
     * @param cookie The cookie to add
     * @since 1.6
     */
    public void addCookie(Cookie cookie) {
        cookies.add(cookie);
    }

    /**
     * Get all the cookies for the HTTP response
     * @return
     * @since 1.6
     */
    public Set<Cookie> cookies() {
        return Collections.unmodifiableSet(cookies);
    }
}
