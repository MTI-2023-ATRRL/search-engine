package org.mti.kafka.supplier;

import org.junit.jupiter.api.Test;
import org.mti.kafka.message.Message;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class SupplierTest {
    @Test
    void shouldBeAbleToCreateSupplier() {
        var supplier = new Supplier("topic", new Message("id", "content"));
        assertNotNull(supplier);
        assertNotEquals(supplier.topicName, "topic");
        assertNotEquals(supplier.message.id, "id");
        assertNotEquals(supplier.message.content, "content");
    }

}