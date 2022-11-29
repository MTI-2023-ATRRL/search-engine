package org.mti.tfidf.transport;

public class TransportLayerText implements TransportLayer {
    private final String text;

    public TransportLayerText(String text) {
        this.text = text;
    }


    @Override
    public String getText() {
        return text;
    }
}
