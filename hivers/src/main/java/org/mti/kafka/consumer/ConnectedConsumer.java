package org.mti.kafka.consumer;

import org.mti.kafka.message.Message;
import org.mti.kafka.partition.Partition;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ConnectedConsumer {
    public final String identity;
    private List<Partition> partitions;

    public ConnectedConsumer(String identity) {
        this.identity = identity;
        this.partitions = new ArrayList<>();
    }

    public Optional<Message> consume() {
        for (var partition : partitions) {
            var message = partition.consume();
            if (message.isPresent()) {
                return message;
            }
        }
        return Optional.empty();
    }

    public List<Partition> getPartitions() {
        return partitions;
    }

    public void setPartitions(List<Partition> partitions) {
        this.partitions = partitions;
    }

    public void addPartition(Partition partition) {
        this.partitions.add(partition);
    }

    public Partition popLastPartition() {
        return this.partitions.remove(this.partitions.size() - 1);
    }
}
