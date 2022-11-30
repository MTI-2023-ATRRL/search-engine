package org.mti.kafka.consumer;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ConsumerTest {
    @Test
    void shouldBeAbleToCreateConsumer() {
        var consumer = new Consumer("topic", "10");
        assertNotNull(consumer);
        assertEquals(consumer.identity, "10");
        assertEquals(consumer.topicName, "topic");
    }

}