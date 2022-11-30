package org.mti.kafka.supplier;

import org.junit.jupiter.api.Test;
import org.mti.kafka.message.Message;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class SupplierTest {
    @Test
    public void shouldBeAbleToCreateSupplier() {
        var supplier = new Supplier("topic", new Message("id", "content"));
        assertNotNull(supplier);
        assertEquals(supplier.topicName, "topic");
        assertEquals(supplier.message.id, "id");
        assertEquals(supplier.message.content, "content");
    }

}