package org.mti.kafka.consumer;

public record ConsumerResult(ConsumeStatus status, String id, String content) {
    public enum ConsumeStatus {
        SUCCESS,
        TOPIC_DOES_NOT_EXIST,
        NO_MESSAGE_AVAILABLE,
        NO_CONNECTED_TO_THIS_TOPIC,
    }

}
