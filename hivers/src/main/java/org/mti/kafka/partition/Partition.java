package org.mti.kafka.partition;

import org.mti.kafka.message.Message;

import java.util.*;

public class Partition {
    private final List<Message> messages;
    private int messageIndex = 0;

    public Partition() {
        this.messages = new ArrayList<>();
    }

    public Optional<Message> consume() {
        if (messageIndex < messages.size()) {
            var message = this.messages.get(messageIndex);
            messageIndex++;
            return Optional.ofNullable(message);
        }
        return Optional.empty();
    }

    public void supply(Message message) {
        this.messages.add(message);
    }

    public List<Message> getMessages() {
        return messages;
    }
}
