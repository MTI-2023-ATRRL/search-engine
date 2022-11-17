package org.mti.hivers.provider;

import org.junit.jupiter.api.Test;
import org.mti.hivers.provider.Prototype;

import static org.junit.jupiter.api.Assertions.*;

class PrototypeTest {

    public static class PingService {
        public String ping() {
            return "Ping";
        }
    }

    @Test
    void shoudBeAbleToCreatePrototype() {
        Prototype<PingService> prototype = new Prototype<>(PingService.class, PingService::new);
        assertNotNull(prototype);
    }

    @Test
    void shouldBeAbleToCreatePrototypeAndGetAnInstance() {
        var prototype = new Prototype<>(PingService.class, PingService::new);
        var pingService = prototype.getValue();
        assertEquals(pingService.ping(), "Ping");
    }

    @Test
    void shouldBeAbleToCreateMultipleInstanceOfPrototype() {
        var prototype = new Prototype<>(PingService.class, PingService::new);
        assertNotEquals(prototype.getValue(), prototype.getValue());
    }
}