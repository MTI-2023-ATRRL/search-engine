package org.mti.kafka.topic;

public class TopicResult {
    public enum TopicResultStatus {
        SUCCESS,
        ALREADY_EXIST,
    }

    public final TopicResultStatus status;

    public TopicResult(TopicResultStatus status) {
        this.status = status;
    }

}
