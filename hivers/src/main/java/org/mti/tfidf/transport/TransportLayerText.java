package org.mti.tfidf.transport;

import java.io.IOException;
import java.util.List;

public class TransportLayerText implements TransportLayer {
    private final String text;

    public TransportLayerText(String text) {
        this.text = text;
    }


    @Override
    public String getText() {
        return text;
    }

    @Override
    public List<String> getLinksInDocument() {
        return null;
    }
}
