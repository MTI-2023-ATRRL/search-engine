package org.mti.kafka.supplier;

public record SupplyResult(SupplyStatus status) {
    public enum SupplyStatus {
        SUCCESS,
        TOPIC_DOES_NOT_EXIST,
    }

}
