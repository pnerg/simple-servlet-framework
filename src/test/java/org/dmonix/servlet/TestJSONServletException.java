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

import org.junit.Test;

/**
 * Test the class {@link JSONServletException}
 * @author Peter Nerg
 */
public class TestJSONServletException extends BaseAssert {

    @Test
    public void missingPathException() {
        JSONServletException ex = JSONServletException.MissingPathException();
        assertEquals(400, ex.response.responseCode);
    }

    @Test
    public void unauthorizedException() {
        JSONServletException ex = JSONServletException.UnauthorizedException("Invalid user/psw");
        assertEquals(401, ex.response.responseCode);
    }
}
