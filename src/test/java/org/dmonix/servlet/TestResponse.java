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

import org.junit.Test;

import static javascalautils.OptionCompanion.Some;

/**
 * Test the class {@link Response}
 * @author Peter Nerg
 */
public class TestResponse extends BaseAssert {

    private final Response response = new Response(666, "Evil is about");

    @Test
    public void responseCode() {
        assertEquals(666, response.responseCode);
    }

    @Test
    public void message() {
        assertEquals("Evil is about", response.message);
    }

    @Test
    public void mediaType() {
        Response rsp = new Response(666, "Evil is about", Some("text/plain"), Some("UTF-8"));
        assertSome("text/plain", rsp.mediaType);
    }

    @Test
    public void mediaType_notSet() {
        assertNone(response.mediaType);
    }

    @Test
    public void charEncoding() {
        Response rsp = new Response(666, "Evil is about", Some("text/plain"), Some("iso-8859-1"));
        assertSome("iso-8859-1", rsp.charEncoding);
    }

    @Test
    public void charEncoding_notSet() {
        assertSome("UTF-8", response.charEncoding);
    }

    @Test
    public void headers_empty() {
        assertTrue(response.headers().isEmpty());
    }

    @Test
    public void headers() {
        response.addHeader("Token", "wohoo");
        assertEquals(1, response.headers().size());
        assertEquals("wohoo", response.headers().get("Token"));
    }

}
