package org.mti.kafka.message;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class MessageTest {

    @Test
    void shouldBeAbleToCreateMessage() {
        var m = new Message("0", "test");
        assertNotNull(m);
        assertEquals(m.id, "0");
        assertEquals(m.content, "test");
    }

}