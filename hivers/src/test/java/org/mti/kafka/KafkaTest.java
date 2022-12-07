package org.mti.kafka;

import org.junit.jupiter.api.Test;
import org.mti.kafka.consumer.Consumer;
import org.mti.kafka.consumer.ConsumerConnectResult;
import org.mti.kafka.consumer.ConsumerResult;
import org.mti.kafka.message.Message;
import org.mti.kafka.supplier.Supplier;
import org.mti.kafka.supplier.SupplyResult;

import static org.junit.jupiter.api.Assertions.*;

class KafkaTest {
    @Test
    void shouldBeAbleToCreateKafka() {
        var kafka = new Kafka();
        assertNotNull(kafka);
    }

    @Test
    void shouldNotBeAbleToCreateTwoIdenticalTopic() {
        var kafka = new Kafka();
        kafka.addTopic("topic", 8);

        try {
            kafka.addTopic("topic", 12);
            fail();
        } catch (Error err) {
            assertTrue(true);
        }

    }

    @Test
    void shouldNotBeAbleToConsumeNoExistingTopic() {
        var kafka = new Kafka();
        var consumerResult = kafka.consume(new Consumer("topic", "identity"));
        assertEquals(consumerResult.status(), ConsumerResult.ConsumeStatus.TOPIC_DOES_NOT_EXIST);
    }

    @Test
    void shouldBeAbleToAddTopic() {
        var kafka = new Kafka();
        kafka.addTopic("topic", 8);

        var consumerResult = kafka.consume(new Consumer("topic", "identity"));
        assertEquals(consumerResult.status(), ConsumerResult.ConsumeStatus.NO_MESSAGE_AVAILABLE);
    }

    @Test
    void shouldNotBeAbleToSupplyUnexcitingTopic() {
        var kafka = new Kafka();
        var supplyResult = kafka.supply(new Supplier("topic", new Message("id", "content")));
        assertEquals(supplyResult.status(), SupplyResult.SupplyStatus.TOPIC_DOES_NOT_EXIST);
    }

    @Test
    void shouldBeAbleToSupplyExcitingTopic() {
        var kafka = new Kafka();
        kafka.addTopic("topic", 8);

        var supplyResult = kafka.supply(new Supplier("topic", new Message("id", "content")));
        assertEquals(supplyResult.status(), SupplyResult.SupplyStatus.SUCCESS);

    }

    @Test
    void shouldNotBeAbleToConnectToUnexcitingTopic() {
        var kafka = new Kafka();
        var consumerConnectResult = kafka.connect(new Consumer("topic", "identit"));
        assertEquals(consumerConnectResult.status, ConsumerConnectResult.ConsumerConnectStatus.TOPIC_DOES_NOT_EXIST);
    }

    @Test
    void shouldNotBeAbleToDisconnectToUnexcitingTopic() {
        var kafka = new Kafka();
        var consumerConnectResult = kafka.disconnect(new Consumer("topic", "identity"));
        assertEquals(consumerConnectResult.status, ConsumerConnectResult.ConsumerConnectStatus.TOPIC_DOES_NOT_EXIST);
    }

    @Test
    void shouldBeAbleToConnectToTopic() {
        var kafka = new Kafka();
        kafka.addTopic("topic", 8);

        var consumerConnectResult = kafka.connect(new Consumer("topic", "identity"));
        assertEquals(consumerConnectResult.status, ConsumerConnectResult.ConsumerConnectStatus.SUCCESS);
    }

    @Test
    void shouldNotBeAbleToConnectTwiceToTopic() {
        var kafka = new Kafka();
        kafka.addTopic("topic", 8);

        var consumer = new Consumer("topic", "identity");
        kafka.connect(consumer);
        var consumerConnectResult = kafka.connect(consumer);
        assertEquals(consumerConnectResult.status, ConsumerConnectResult.ConsumerConnectStatus.ALREADY_CONNECTED);
    }

    @Test
    void shouldBeAbleToDisconnect() {
        var kafka = new Kafka();
        kafka.addTopic("topic", 8);

        var consumer = new Consumer("topic", "identity");
        kafka.connect(consumer);
        var consumerConnectResult = kafka.disconnect(consumer);
        assertEquals(consumerConnectResult.status, ConsumerConnectResult.ConsumerConnectStatus.SUCCESS);
    }

    @Test
    void shouldNotBeAbleToDisconnectWhenNotConnected() {
        var kafka = new Kafka();
        kafka.addTopic("topic", 8);

        var consumerConnectResult = kafka.disconnect(new Consumer("topic", "identity"));
        assertEquals(consumerConnectResult.status, ConsumerConnectResult.ConsumerConnectStatus.NOT_CONNECTED);
    }

    @Test
    void shouldBeAbleToConsumeMessage() {
        var kafka = new Kafka();
        var topicName = "topic";
        var consumerIdentity = "identity";
        kafka.addTopic(topicName, 8);

        var supplier = new Supplier(topicName, new Message("id", "content"));
        kafka.supply(supplier);

        var consumer = new Consumer(topicName, consumerIdentity);
        kafka.connect(consumer);

        var partitions = kafka.getTopics().get(topicName).connectedConsumerMap.get(consumerIdentity).getPartitions();
        var partition = partitions.stream().filter(x -> x.getMessages().size() > 0).toList().stream().findFirst().get();

        var consumerResult = kafka.consume(consumer);
        assertEquals(consumerResult.status(), ConsumerResult.ConsumeStatus.SUCCESS);
        assertEquals(consumerResult.id(), "id");
        assertNotEquals(0, partition.getMessages().size());
    }
}