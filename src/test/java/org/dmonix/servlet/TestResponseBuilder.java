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
import org.mockito.Mockito;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import static javascalautils.OptionCompanion.Some;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Test the class {@link ResponseBuilder}.
 * @author Peter Nerg
 */
public class TestResponseBuilder extends BaseAssert implements ResponseBuilder {

    @Test
    public void emptyResponse() {
        assertEmptyResponse(200, EmptyResponse(200));
    }

    @Test
    public void acceptedResponse() {
        assertEmptyResponse(202, AcceptedResponse());
    }

    @Test
    public void createdResponse() {
        assertEmptyResponse(201, CreatedResponse());
    }

    @Test
    public void okResponse() {
        assertEmptyResponse(200, OKResponse());
    }

    @Test
    public void objectResponse() {
        Response response = ObjectResponse(new DummyData("peter", 69));
        assertEquals(200, response.responseCode);
        assertSome("UTF-8", response.charEncoding);
        assertFalse(response.message.isEmpty());
    }

    @Test
    public void objectResponse_withCharset() {
        Response response = ObjectResponse(new DummyData("nerg", 69), "ISO-8859-1");
        assertEquals(200, response.responseCode);
        assertSome("ISO-8859-1", response.charEncoding);
        assertFalse(response.message.isEmpty());
    }

    @Test
    public void errorResponse() {
        Response response = ErrorResponse(new Exception("Error, terror!"));
        assertEquals(500, response.responseCode);
        assertEquals("Error, terror!", response.message);
    }

    @Test
    public void errorResponse_withJSONServletException() {
        Response response = ErrorResponse(new JSONServletException(ErrorResponseNotFound("No such data")));
        assertEquals(404, response.responseCode);
        assertEquals("No such data", response.message);
    }

    @Test
    public void errorResponse_withResponseCode() {
        Response response = ErrorResponse(404, "Didn't find");
        assertEquals(404, response.responseCode);
        assertEquals("Didn't find", response.message);
    }

    @Test
    public void errorResponseNotFound() {
        Response response = ErrorResponseNotFound("No such data");
        assertEquals(404, response.responseCode);
        assertEquals("No such data", response.message);
    }

    @Test
    public void errorResponseMissingPath() {
        Response response = ErrorResponseMissingPath();
        assertEquals(400, response.responseCode);
    }

    @Test
    public void errorResponseResourceAlreadyExists() {
        Response response = ErrorResponseResourceAlreadyExists("some-path");
        assertEquals(403, response.responseCode);
    }

    @Test
    public void errorResponseUnsupportedOperation() {
        Response response = ErrorResponseUnsupportedOperation();
        assertEquals(405, response.responseCode);
    }

    @Test
    public void writeResponse() throws IOException {
        HttpServletResponse servletResponse = Mockito.mock(HttpServletResponse.class);
        when(servletResponse.getWriter()).thenReturn(new PrintWriter(new NullWriter()));

        Response response = new Response(666, "Evil is about", Some("text/plain"), Some("UTF-8"));
        response.addHeader("Token", "some-value");
        writeResponse(servletResponse, response);

        verify(servletResponse).setHeader("Token", "some-value");
        verify(servletResponse).setStatus(response.responseCode);
        verify(servletResponse).setContentType("text/plain");
        verify(servletResponse).setCharacterEncoding("UTF-8");
    }

    private void assertEmptyResponse(int responseCode, Response response) {
        assertEquals(responseCode, response.responseCode);
        assertEquals("", response.message);
        assertNoHeaders(response);
    }

    private void assertNoHeaders(Response response) {
        assertTrue(response.headers().isEmpty());
    }


}
