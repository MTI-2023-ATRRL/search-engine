package org.mti.tfidf.transport;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TransportLayerTextTest {
    @Test
    public void shouldBeAbleToCreateTransportLayerText() {
        var transportLayerText = new TransportLayerText("Test");
        assertNotNull(transportLayerText);
    }

    @Test
    public void shouldBeAbleToGetTransportLayerText() {
        var transportLayerText = new TransportLayerText("Test");
        assertEquals(transportLayerText.getText(), "Test");
    }
}