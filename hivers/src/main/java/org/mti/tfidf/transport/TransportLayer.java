package org.mti.tfidf.transport;

import java.io.IOException;

public interface TransportLayer {
    String getText() throws IOException;
}
