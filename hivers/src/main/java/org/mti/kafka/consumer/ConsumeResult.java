package org.mti.kafka.consumer;

public record ConsumeResult(ConsumeStatus status, String id, String content) {
    public enum ConsumeStatus {
        SUCCESS,
        TOPIC_DOES_NOT_EXIST,
        NO_MESSAGE,
    }

}
