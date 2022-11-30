package org.mti.kafka;

public class Consumer {
    public final String topicName;
    public final String identity;

    public Consumer(String topicName, String identity) {
        this.topicName = topicName;
        this.identity = identity;
    }
}
