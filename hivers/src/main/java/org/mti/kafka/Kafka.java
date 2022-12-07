package org.mti.kafka;

import org.mti.kafka.consumer.Consumer;
import org.mti.kafka.consumer.ConsumerConnectResult;
import org.mti.kafka.consumer.ConsumerResult;
import org.mti.kafka.supplier.Supplier;
import org.mti.kafka.supplier.SupplyResult;
import org.mti.kafka.topic.Topic;
import org.mti.kafka.topic.TopicResult;

import java.util.HashMap;
import java.util.Map;

public class Kafka {
    private final Map<String, Topic> topics;

    Kafka() {
        this.topics = new HashMap<>();
    }

    public TopicResult addTopic(String topicName, int numberOfPartition) {
        var topic = new Topic(topicName, numberOfPartition);
        return this.addTopic(topic);
    }

    private TopicResult addTopic(Topic topic) {
        if (topics.containsKey(topic.name)) {
            return new TopicResult(TopicResult.TopicResultStatus.ALREADY_EXIST);
        }
        topics.put(topic.name, topic);
        return new TopicResult(TopicResult.TopicResultStatus.SUCCESS);
    }

    private Topic getTopic(String topicName) {
        if (!topics.containsKey(topicName)) {
            throw new Error("Topic does not exist");
        }
        return this.topics.get(topicName);
    }

    public SupplyResult supply(Supplier supplier) {
        try {
            var topic = this.getTopic(supplier.topicName);
            topic.supply(supplier.message);
            return new SupplyResult(SupplyResult.SupplyStatus.SUCCESS);
        } catch (Error err) {
            return new SupplyResult(SupplyResult.SupplyStatus.TOPIC_DOES_NOT_EXIST);
        }
    }

    public ConsumerResult consume(Consumer consumer) {
        try {
            var topic = this.getTopic(consumer.topicName);
            var message = topic.consume(consumer);
            if (message.isEmpty()) {
                return new ConsumerResult(ConsumerResult.ConsumeStatus.NO_MESSAGE_AVAILABLE, null, null);
            }
            return new ConsumerResult(ConsumerResult.ConsumeStatus.SUCCESS, message.get().id, message.get().content);
        } catch (Error err) {
            return new ConsumerResult(ConsumerResult.ConsumeStatus.TOPIC_DOES_NOT_EXIST, null, null);
        }
    }

    public ConsumerConnectResult connect(Consumer consumer) {
        try {
            var topic = this.getTopic(consumer.topicName);
            return topic.connect(consumer);
        } catch (Error err) {
            return new ConsumerConnectResult(ConsumerConnectResult.ConsumerConnectStatus.TOPIC_DOES_NOT_EXIST);
        }
    }

    public ConsumerConnectResult disconnect(Consumer consumer) {
        try {
            var topic = this.getTopic(consumer.topicName);
            return topic.disconnect(consumer);
        } catch (Error err) {
            return new ConsumerConnectResult(ConsumerConnectResult.ConsumerConnectStatus.TOPIC_DOES_NOT_EXIST);
        }
    }

    public Map<String, Topic> getTopics() {
        return topics;
    }
}
