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

        var connectedConsumer = new ConnectedConsumer(consumer.identity);
        this.connectedConsumerMap.put(consumer.identity, connectedConsumer);

        if (connectedConsumerMap.size() == 1) {
            this.connectedConsumerMap.get(connectedConsumer.identity).setPartitions(partitions);
            return new ConsumerConnectResult(ConsumerConnectResult.ConsumerConnectStatus.SUCCESS);
        }

        var ratio = (double) partitionSize / connectedConsumerMap.size();

        while (!isRatioVerifiedPerConsumer(ratio)) {
            for (var c : connectedConsumerMap.values()) {
                var partitions = c.getPartitions();
                if (removeGreaterThanRatio(partitions.size(), ratio)) {
                    var partitionToSwap = c.popLastPartition();
                    addPartitionToConsumer(partitionToSwap, ratio);
                    break;
                }
            }
        }


        return new ConsumerConnectResult(ConsumerConnectResult.ConsumerConnectStatus.SUCCESS);
    }

    private boolean isRatioVerifiedPerConsumer(double ratio) {
        for (var consumer : connectedConsumerMap.values()) {
            var partitionsSize = consumer.getPartitions().size();
            if (partitionsSize < Math.floor(ratio) || partitionsSize > Math.ceil(ratio)) {
                return false;
            }
        }
        return true;
    }

    private boolean removeGreaterThanRatio(int size, double ratio) {
        return size > ratio;
    }

    private void addPartitionToConsumer(Partition partition, double ratio) {
        for (var consumer : connectedConsumerMap.values()) {
            if (consumer.getPartitions().size() < Math.floor(ratio))
                consumer.addPartition(partition);
        }
    }

    public ConsumerConnectResult disconnect(Consumer consumer) {
        if (!this.connectedConsumerMap.containsKey(consumer.identity)) {
            return new ConsumerConnectResult(ConsumerConnectResult.ConsumerConnectStatus.NOT_CONNECTED);
        }

        this.connectedConsumerMap.remove(consumer.identity);
        return new ConsumerConnectResult(ConsumerConnectResult.ConsumerConnectStatus.SUCCESS);
    }

}
