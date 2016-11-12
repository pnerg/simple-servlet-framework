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

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static javascalautils.OptionCompanion.None;

/**
 * Response object containing the response to be sent to the client.
 *
 * @author Peter Nerg
 * @since 1.0
 */
public class Response {
    final int responseCode;
    final String message;
    final Option<String> mediaType;
    private final Map<String, String> headers = new HashMap<>();

    /**
     * Creates an instance
     *
     * @param responseCode
     *            The HTTP response code
     * @param message
     *            The body of the response
     */
    public Response(int responseCode, String message) {
        this(responseCode, message, None());
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
     */
    Response(int responseCode, String message, Option<String> mediaType) {
        this.responseCode = responseCode;
        this.message = message;
        this.mediaType = mediaType;
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
}
