package org.mti.kafka.consumer;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ConsumerResultTest {

    @Test
    public void shouldBeAbleToCreateConsumerResult() {
        var consumerResult = new ConsumerResult(ConsumerResult.ConsumeStatus.SUCCESS, "id", "content");
        assertNotNull(consumerResult);
        assertEquals(consumerResult.status(), ConsumerResult.ConsumeStatus.SUCCESS);
        assertEquals(consumerResult.id(), "id");
        assertEquals(consumerResult.content(), "content");
    }
}