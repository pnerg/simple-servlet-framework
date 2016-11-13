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
package org.dmonix.servlet.web;

import org.dmonix.servlet.DummyData;
import org.dmonix.servlet.JSONServlet;
import org.dmonix.servlet.Request;
import org.dmonix.servlet.Response;

import javax.servlet.annotation.WebServlet;

/**
 * Simple servlet built with {@link JSONServlet} for testing purposes.
 * @author Peter Nerg
 */
@WebServlet(name = "TestServlet", urlPatterns = {"/*"})
public class TestServlet extends JSONServlet {
    @Override
    protected Response get(Request request) {
        return ObjectResponse(new DummyData("peter", 69));
    }

}
