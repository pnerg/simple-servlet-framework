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

import com.google.gson.Gson;
import org.dmonix.servlet.DummyServlets.GetOnlyServlet;
import org.dmonix.servlet.DummyServlets.ServletWithAllOps;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;

import static org.hamcrest.CoreMatchers.*;

/**
 * Start a Jetty server and deploy dummy servlet(s).
 * Then perform some remote HTTP operations towards these.
 * @author Peter Nerg
 */
public class TestWithServlets extends BaseAssert {

    private static final Server server = new Server();

    private static URI serverURI;

    @BeforeClass
    public static void startJetty() throws Exception {
        server.setStopAtShutdown(true);

        ServerConnector connector = new ServerConnector(server);
        connector.setPort(0); // auto-bind to available port
        server.addConnector(connector);

        ServletContextHandler context = new ServletContextHandler();
        context.addServlet(new ServletHolder("test", GetOnlyServlet.class),"/");
        context.addServlet(new ServletHolder("test2", ServletWithAllOps.class),"/allops");
        server.setHandler(context);

        server.start();

        serverURI = new URI("http://localhost:"+connector.getLocalPort());
    }

    @AfterClass
    public static void stopJetty() throws Exception {
        server.stop();
    }

    @Test
    public void get() throws IOException {
        Gson gson = new Gson();
        HttpURLConnection http = connect("GET");
        assertEquals(200, http.getResponseCode());
        DummyData data = gson.fromJson(new InputStreamReader(http.getInputStream()), DummyData.class);
        assertNotNull(data);
        assertEquals("peter", data.name);
    }

    /**
     * Test unsupported operation.
     * @throws IOException
     */
    @Test
    public void put() throws IOException {
        HttpURLConnection http = connect("PUT");
        assertEquals(405, http.getResponseCode());
    }

    /**
     * Test unsupported operation.
     * @throws IOException
     */
    @Test
    public void post() throws IOException {
        HttpURLConnection http = connect("POST");
        assertEquals(405, http.getResponseCode());
    }

    /**
     * Test unsupported operation.
     * @throws IOException
     */
    @Test
    public void delete() throws IOException {
        HttpURLConnection http = connect("DELETE");
        assertEquals(405, http.getResponseCode());
    }

    /**
     * Test the OPTIONS method to verify the we produce the correct method names depending on what the servlet supports
     * @throws IOException
     */
    @Test
    public void options() throws IOException {
        HttpURLConnection http = connect("OPTIONS");

        assertEquals(200, http.getResponseCode());
        String allow = http.getHeaderField("Allow");
        assertNotNull(allow);

        //assert the servlet supports the expected operations
        assertThat(allow, allOf(
                containsString("OPTIONS"),
                containsString("TRACE"),
                containsString("GET"),
                containsString("HEAD")));

        //assert we don't get the operations not supported by the servlet
        assertThat(allow, not(allOf(
                containsString("PUT"),
                containsString("POST"),
                containsString("DELETE"))));
    }

    /**
     * Test the OPTIONS method to verify the we produce the correct method names depending on what the servlet supports
     * @throws IOException
     */
    @Test
    public void options_forServletWithAllOps() throws IOException {
        HttpURLConnection http = (HttpURLConnection) serverURI.resolve("/allops").toURL().openConnection();
        http.setRequestMethod("OPTIONS");
        http.connect();

        assertEquals(200, http.getResponseCode());
        String allow = http.getHeaderField("Allow");
        assertNotNull(allow);

        //assert the servlet supports the expected operations
        assertThat(allow, allOf(
                containsString("OPTIONS"),
                containsString("TRACE"),
                containsString("GET"),
                containsString("PUT"),
                containsString("POST"),
                containsString("DELETE"),
                containsString("HEAD")));
    }

    private static HttpURLConnection connect(String operation) throws IOException {
        HttpURLConnection http = (HttpURLConnection) serverURI.resolve("/").toURL().openConnection();
        http.setRequestMethod(operation);
        http.connect();
        return http;
    }
}
