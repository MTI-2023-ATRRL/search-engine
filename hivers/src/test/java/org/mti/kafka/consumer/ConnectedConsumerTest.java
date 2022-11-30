package org.mti.kafka.consumer;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ConnectedConsumerTest {

    @Test
    void shouldBeAbleToCreateConnectedConsumer() {
        var connectedConsumer = new ConnectedConsumer("identity");
        assertNotNull(connectedConsumer);
        assertEquals(connectedConsumer.identity, "identity");
    }

}