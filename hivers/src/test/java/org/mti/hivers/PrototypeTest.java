package org.mti.hivers;

import org.junit.jupiter.api.Test;

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
        var pingService = prototype.instanceOf(PingService.class).orElseThrow();
        assertEquals(pingService.ping(), "Ping");
    }

    @Test
    void shouldBeAbleToCreateMultipleInstanceOfPrototype() {
        var prototype = new Prototype<>(PingService.class, PingService::new);
        assertNotEquals(prototype.instanceOf(PingService.class).orElseThrow(), prototype.instanceOf(PingService.class).orElseThrow());
    }
}