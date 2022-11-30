package org.mti.kafka;

import org.mti.kafka.consumer.Consumer;
import org.mti.kafka.message.Message;
import org.mti.kafka.supplier.Supplier;
import org.mti.kafka.supplier.SupplyResult;
import org.mti.kafka.topic.Topic;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Kafka {
    public Map<String, Topic> topics;

    Kafka() {
        this.topics = new HashMap<>();
    }

    public void addTopic(String topicName, int numberOfPartition) {
        var topic = new Topic(topicName, numberOfPartition);
        this.addTopic(topic);
    }

    private void addTopic(Topic topic) {
        if (topics.containsKey(topic.name)) {
            throw new Error("Topic already exist");
        }
        topics.put(topic.name, topic);
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

    public Optional<Message> consume(Consumer consumer) {
        try {
            var topic = this.getTopic(consumer.topicName);
            return topic.consume(consumer);
        } catch (Error err) {
            // TODO: Tell the user that the topics does not exist
            return Optional.empty();
        }
    }

    public void connect(Consumer consumer) {
        try {
            var topic = this.getTopic(consumer.topicName);
            topic.connect(consumer);
        } catch (Error err) {
            // TODO: Tell the user that the tocis does not exist
        }
    }

    public void disconnect(Consumer consumer) {
        try {
            var topic = this.getTopic(consumer.topicName);
            topic.disconnect(consumer);
        } catch (Error err) {
            // TODO: Tell the user that the topics does not exist
        }
    }
}
