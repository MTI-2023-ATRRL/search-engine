package org.mti.tfidf.transport;

import java.io.IOException;
import java.util.List;

public interface TransportLayer {
    String getText() throws IOException;
    List<String> getLinksInDocument() throws IOException;
}
