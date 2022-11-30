package org.mti.kafka.supplier;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class SupplyResultTest {

    @Test
    public void shouldBeAbleToCreateSupplyResult() {
        var supplyResult = new SupplyResult(SupplyResult.SupplyStatus.SUCCESS);
        assertNotNull(supplyResult);
        assertEquals(supplyResult.status(), SupplyResult.SupplyStatus.SUCCESS);
    }
}