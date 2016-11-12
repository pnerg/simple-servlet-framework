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

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Mock implementation of a {@link javax.servlet.ServletInputStream} used to provide JSon response data
 * @author Peter Nerg
 */
public final class MockServletInputStream extends ServletInputStream {
    private final InputStream wrapped;

    /**
     * Creates the stream with a JSon string.
     * @param response
     */
    public MockServletInputStream(String response) {
        this.wrapped = new ByteArrayInputStream(response.getBytes());
    }

    /**
     * Unsupported operation.
     * @return
     */
    @Override
    public boolean isFinished() {
        throw new UnsupportedOperationException();
    }

    /**
     * Unsupported operation.
     * @return
     */
    @Override
    public boolean isReady() {
        throw new UnsupportedOperationException();
    }

    /**
     * Unsupported operation.
     * @return
     */
    @Override
    public void setReadListener(ReadListener readListener) {
        throw new UnsupportedOperationException();
    }

    /**
     * Delegates the read to the wrapped {@link ByteArrayInputStream} containing the JSon string data.
     * @return
     * @throws IOException
     */
    @Override
    public int read() throws IOException {
        return wrapped.read();
    }
}
