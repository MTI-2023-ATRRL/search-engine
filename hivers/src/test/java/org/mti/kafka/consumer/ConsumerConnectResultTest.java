package org.mti.kafka.consumer;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ConsumerConnectResultTest {

    @Test
    void shouldBeAbleToCreateConsumerConnectResult() {
        var consumerConnectResult = new ConsumerConnectResult(ConsumerConnectResult.ConsumerConnectStatus.SUCCESS);
        assertNotNull(consumerConnectResult);
        assertEquals(consumerConnectResult.status, ConsumerConnectResult.ConsumerConnectStatus.SUCCESS);
    }
}