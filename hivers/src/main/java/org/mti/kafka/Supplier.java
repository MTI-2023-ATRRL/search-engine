package org.mti.kafka;

public class Supplier {
    public final String topicName;
    public final Message message;

    public Supplier(String topicName, Message message) {
        this.topicName = topicName;
        this.message = message;
    }
}
