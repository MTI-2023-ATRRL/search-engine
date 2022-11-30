package org.mti.kafka.consumer;

public class ConsumerConnectResult {
    public enum ConsumerConnectStatus {
        SUCCESS,
        ALREADY_CONNECTED,
        UNABLE_TO_CONNECT,
        TOPIC_DOES_NOT_EXIST,
        NOT_CONNECTED,
    }

    public final ConsumerConnectStatus status;

    public ConsumerConnectResult(ConsumerConnectStatus status) {
        this.status = status;
    }
}
