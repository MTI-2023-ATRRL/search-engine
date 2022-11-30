package org.mti.kafka.topic;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TopicTest {
    @Test
    public void shouldCreateTopic() {
        var topic = new Topic("Topic", 5);
        assertNotNull(topic);
    }

    @Test
    public void shouldCreateTopicAndAddOneConsumer() {

    }
}