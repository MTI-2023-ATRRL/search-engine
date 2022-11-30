package org.mti.kafka.supplier;

import org.mti.kafka.message.Message;

public class Supplier {
    public final String topicName;
    public final Message message;

    public Supplier(String topicName, Message message) {
        this.topicName = topicName;
        this.message = message;
    }
}
