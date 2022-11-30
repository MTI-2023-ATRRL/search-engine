package org.mti.kafka;

public record SupplyResult(SupplyStatus status) {
    public enum SupplyStatus {
        SUCCESS,
        TOPIC_DOES_NOT_EXIST,
    }

}
