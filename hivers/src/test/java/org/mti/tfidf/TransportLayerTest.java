package org.mti.tfidf;

import junit.framework.Assert;
import org.junit.jupiter.api.Test;
import org.mti.hivers.Scope;

import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;

class TransportLayerTest {
    @Test
    void shouldGetUrlBody() throws IOException {
        var url = "https://example.com";
        var transportLayer = new TransportLayer(url, TransportProtocol.BY_URL);
        var body = transportLayer.getBody();

        var expected = "Example Domain This domain is for use in illustrative examples in documents. You may use this domain in literature without prior coordination or asking for permission. More information...";
        assertEquals(expected, body);
    }
}
