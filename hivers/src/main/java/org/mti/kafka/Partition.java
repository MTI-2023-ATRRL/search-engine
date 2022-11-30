package org.mti.kafka;

import java.util.LinkedList;
import java.util.Optional;
import java.util.Queue;

public class Partition {
    public final Queue<Message> messages;

    public Partition() {
        this.messages = new LinkedList<>();
    }

    public Optional<Message> consume() {
        var message = this.messages.poll();
        return Optional.ofNullable(message);
    }

    public void supply(Message message) {
        this.messages.add(message);
    }
}
