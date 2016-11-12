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
