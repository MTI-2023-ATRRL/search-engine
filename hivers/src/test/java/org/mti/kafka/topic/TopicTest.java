package org.mti.kafka.topic;

import org.junit.jupiter.api.Test;
import org.mti.kafka.consumer.Consumer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class TopicTest {
    @Test
    void shouldCreateTopic() {
        var topic = new Topic("topic", 5);
        assertNotNull(topic);
    }

    @Test
    void shouldCreateTopicAndAddOneConsumer() {
        var topicName = "topic";
        var partitionsSize = 5;
        var consumerName = "consumer";
        var topic = new Topic(topicName, partitionsSize);
        var consumer = new Consumer(topicName, consumerName);

        topic.connect(consumer);

        assertEquals(1, topic.connectedConsumerMap.size());
        assertEquals(partitionsSize, topic.connectedConsumerMap.get(consumerName).getPartitions().size());
    }

    @Test
    void shouldCreateTopicAndAddTwoConsumers() {
        var topicName = "topic";
        var partitionsSize = 5;
        var firstConsumerName = "consumer1";
        var secondConsumerName = "consumer2";

        var topic = new Topic(topicName, partitionsSize);
        var firstConsumer = new Consumer(topicName, firstConsumerName);
        var secondConsumer = new Consumer(topicName, secondConsumerName);

        topic.connect(firstConsumer);
        topic.connect(secondConsumer);

        assertEquals(2, topic.connectedConsumerMap.size());
        assertEquals(3, topic.connectedConsumerMap.get(firstConsumerName).getPartitions().size());
        assertEquals(2, topic.connectedConsumerMap.get(secondConsumerName).getPartitions().size());
    }

    @Test
    void shouldCreateTopicAndAddAConsumerPerPartition() {
        var topicName = "topic";
        var partitionsSize = 2;
        var firstConsumerName = "consumer1";
        var secondConsumerName = "consumer2";

        var topic = new Topic(topicName, partitionsSize);
        var firstConsumer = new Consumer(topicName, firstConsumerName);
        var secondConsumer = new Consumer(topicName, secondConsumerName);

        topic.connect(firstConsumer);
        topic.connect(secondConsumer);

        assertEquals(2, topic.connectedConsumerMap.size());
        assertEquals(1, topic.connectedConsumerMap.get(firstConsumerName).getPartitions().size());
        assertEquals(1, topic.connectedConsumerMap.get(secondConsumerName).getPartitions().size());
    }

    @Test
    void shouldCreateTopicAndAddNineConsumers() {
        var topicName = "topic";
        var partitionsSize = 6;
        var firstConsumerName = "consumer1";
        var secondConsumerName = "consumer2";
        var thirdConsumerName = "consumer3";
        var fourthConsumerName = "consumer4";

        var topic = new Topic(topicName, partitionsSize);
        var firstConsumer = new Consumer(topicName, firstConsumerName);
        var secondConsumer = new Consumer(topicName, secondConsumerName);
        var thirdConsumer = new Consumer(topicName, thirdConsumerName);
        var fourthConsumer = new Consumer(topicName, fourthConsumerName);

        topic.connect(firstConsumer);
        topic.connect(secondConsumer);
        topic.connect(thirdConsumer);
        topic.connect(fourthConsumer);

        assertEquals(4, topic.connectedConsumerMap.size());
        assertEquals(2, topic.connectedConsumerMap.get(firstConsumerName).getPartitions().size());
        assertEquals(2, topic.connectedConsumerMap.get(secondConsumerName).getPartitions().size());
        assertEquals(1, topic.connectedConsumerMap.get(thirdConsumerName).getPartitions().size());
        assertEquals(1, topic.connectedConsumerMap.get(fourthConsumerName).getPartitions().size());
    }

    @Test
    void shouldConnectOneAndDisconnectOneConsumerFromTopic() {
        var topicName = "topic";
        var partitionsSize = 5;
        var consumerName = "consumer";

        var topic = new Topic(topicName, partitionsSize);
        var consumer = new Consumer(topicName, consumerName);

        topic.connect(consumer);

        topic.disconnect(consumer);

        assertEquals(0, topic.connectedConsumerMap.size());
    }

    @Test
    void shouldConnectTwoAndDisconnectOneConsumerFromTopic() {
        var topicName = "topic";
        var partitionsSize = 5;
        var firstConsumerName = "consumer1";
        var secondConsumerName = "consumer2";

        var topic = new Topic(topicName, partitionsSize);
        var firstConsumer = new Consumer(topicName, firstConsumerName);
        var secondConsumer = new Consumer(topicName, secondConsumerName);

        topic.connect(firstConsumer);
        topic.connect(secondConsumer);

        topic.disconnect(firstConsumer);

        assertEquals(1, topic.connectedConsumerMap.size());
        assertEquals(5, topic.connectedConsumerMap.get(secondConsumerName).getPartitions().size());
    }

    @Test
    void shouldConnectThreeAndDisconnectOneConsumerFromTopic() {
        var topicName = "topic";
        var partitionsSize = 6;
        var firstConsumerName = "consumer1";
        var secondConsumerName = "consumer2";
        var thirdConsumerName = "consumer3";

        var topic = new Topic(topicName, partitionsSize);
        var firstConsumer = new Consumer(topicName, firstConsumerName);
        var secondConsumer = new Consumer(topicName, secondConsumerName);
        var thirdConsumer = new Consumer(topicName, thirdConsumerName);

        topic.connect(firstConsumer);
        topic.connect(secondConsumer);
        topic.connect(thirdConsumer);

        topic.disconnect(thirdConsumer);

        assertEquals(2, topic.connectedConsumerMap.size());
        assertEquals(3, topic.connectedConsumerMap.get(firstConsumerName).getPartitions().size());
        assertEquals(3, topic.connectedConsumerMap.get(secondConsumerName).getPartitions().size());
    }


    @Test
    void shouldConnectThreeAndDisconnectOneConsumerFromTopicWith31Partitions() {
        var topicName = "topic";
        var partitionsSize = 31;
        var firstConsumerName = "consumer1";
        var secondConsumerName = "consumer2";
        var thirdConsumerName = "consumer3";

        var topic = new Topic(topicName, partitionsSize);
        var firstConsumer = new Consumer(topicName, firstConsumerName);
        var secondConsumer = new Consumer(topicName, secondConsumerName);
        var thirdConsumer = new Consumer(topicName, thirdConsumerName);

        topic.connect(firstConsumer);
        topic.connect(secondConsumer);
        topic.connect(thirdConsumer);

        topic.disconnect(firstConsumer);

        assertEquals(2, topic.connectedConsumerMap.size());
        assertEquals(15, topic.connectedConsumerMap.get(secondConsumerName).getPartitions().size());
        assertEquals(16, topic.connectedConsumerMap.get(thirdConsumerName).getPartitions().size());
    }
}