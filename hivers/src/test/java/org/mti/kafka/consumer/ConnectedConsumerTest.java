package org.mti.kafka.consumer;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ConnectedConsumerTest {

    @Test
    public void shouldBeAbleToCreateConnectedConsumer() {
        var connectedConsumer = new ConnectedConsumer("identity", new ArrayList<>());
        assertNotNull(connectedConsumer);
        assertEquals(connectedConsumer.identity, "identity");
    }

}