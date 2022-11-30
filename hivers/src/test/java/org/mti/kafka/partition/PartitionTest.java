package org.mti.kafka.partition;

import org.junit.jupiter.api.Test;
import org.mti.kafka.message.Message;

import static org.junit.jupiter.api.Assertions.*;

class PartitionTest {
    @Test
    void shouldBeAbleToCreatePartition() {
        var partition = new Partition();
        assertNotNull(partition);
    }

    @Test
    void shouldnotBeAbleToConsumeWhenNoMessage() {
        var partition = new Partition();
        assertTrue(partition.consume().isEmpty());
    }

    @Test
    void shouldBeAbleToSupplyAndConsumeMessage() {
        var message = new Message("0", "Bonsoir");
        var partition = new Partition();

        partition.supply(message);
        var m = partition.consume();

        assertTrue(m.isPresent());
        assertEquals(m.get(), message);
    }

    @Test
    void shouldGetFirstMessage() {
        var m1 = new Message("1", "Bonjour");
        var m2 = new Message("2", "Bonsoir");

        var partition = new Partition();
        partition.supply(m1);
        partition.supply(m2);

        var m = partition.consume();
        assertTrue(m.isPresent());
        assertEquals(m.get(), m1);

        m = partition.consume();
        assertEquals(m.get(), m2);


        m = partition.consume();
        assertTrue(m.isEmpty());
    }

}