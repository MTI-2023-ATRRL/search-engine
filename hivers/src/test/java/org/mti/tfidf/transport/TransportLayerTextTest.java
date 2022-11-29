package org.mti.tfidf.transport;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TransportLayerTextTest {
    @Test
    void shouldBeAbleToCreateTransportLayerText() {
        var transportLayerText = new TransportLayerText("Test");
        assertNotNull(transportLayerText);
    }

    @Test
    void shouldBeAbleToGetTransportLayerText() {
        var transportLayerText = new TransportLayerText("Test");
        assertEquals(transportLayerText.getText(), "Test");
    }
}