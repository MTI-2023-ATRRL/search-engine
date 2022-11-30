package org.mti.kafka.topic;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class TopicTest {
    @Test
    void shouldCreateTopic() {
        var topic = new Topic("Topic", 5);
        assertNotNull(topic);
    }

    @Test
    void shouldCreateTopicAndAddOneConsumer() {
        
    }
}