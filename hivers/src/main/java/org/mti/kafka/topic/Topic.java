package org.mti.kafka.topic;

import org.mti.kafka.consumer.ConnectedConsumer;
import org.mti.kafka.consumer.Consumer;
import org.mti.kafka.consumer.ConsumerConnectResult;
import org.mti.kafka.message.Message;
import org.mti.kafka.partition.Partition;

import java.util.*;

public class Topic {
    public final String name;
    public List<Partition> partitions;
    public int partitionSize;

    public Map<String, ConnectedConsumer> connectedConsumerMap;

    public Topic(String name, int partitionSize) {
        this.connectedConsumerMap = new HashMap<>();
        this.name = name;

        this.partitionSize = partitionSize;
        this.partitions = new ArrayList<>(partitionSize);
        for (var i = 0; i < partitionSize; i++) {
            this.partitions.add(new Partition());
        }
    }

    public void supply(Message message) {
        int value = 0;
        for (var b : message.id.getBytes()) {
            value = (value << 8) + (b & 0xFF);
        }

        int chosenPartition = value % partitionSize;
        this.partitions.get(chosenPartition).supply(message);
    }

    // TODO: Refactor -> Take a consumer
    // Check if consumer connected if not connected return error
    // Get ConnectedConsumer
    // Execute consume
    public Optional<Message> consume(Consumer consumer) {
        return Optional.empty();
    }

    public ConsumerConnectResult connect(Consumer consumer) {
        if (this.connectedConsumerMap.containsKey(consumer.identity)) {
            return new ConsumerConnectResult(ConsumerConnectResult.ConsumerConnectStatus.ALREADY_CONNECTED);
        }
        if (this.connectedConsumerMap.size() == this.partitionSize) {
            return new ConsumerConnectResult(ConsumerConnectResult.ConsumerConnectStatus.UNABLE_TO_CONNECT);
        }

        if (this.connectedConsumerMap.size() == 0) {
            var copyPartition = new ArrayList<Partition>();
            for (var partition : partitions) {
                copyPartition.add(partition);
            }

            var connectedConsumer = new ConnectedConsumer(consumer.identity, partitions);
            this.connectedConsumerMap.put(consumer.identity, connectedConsumer);
        }

        return new ConsumerConnectResult(ConsumerConnectResult.ConsumerConnectStatus.SUCCESS);
    }

    public ConsumerConnectResult disconnect(Consumer consumer) {
        if (!this.connectedConsumerMap.containsKey(consumer.identity)) {
            return new ConsumerConnectResult(ConsumerConnectResult.ConsumerConnectStatus.NOT_CONNECTED);
        }

        this.connectedConsumerMap.remove(consumer.identity);
        return new ConsumerConnectResult(ConsumerConnectResult.ConsumerConnectStatus.SUCCESS);
    }

}
