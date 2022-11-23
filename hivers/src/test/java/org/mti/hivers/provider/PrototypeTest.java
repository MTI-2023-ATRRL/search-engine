package org.mti.hivers.provider;

import org.junit.jupiter.api.Test;
import org.mti.hivers.aspect.PongAspect;
import org.mti.hivers.proxy.ProxyDefinition;

import static org.junit.jupiter.api.Assertions.*;

class PrototypeTest {

    public static interface Ping {
        public String ping();
    }

    public static class PingService implements Ping {
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

    @Test
    void shouldBeAbleToAddProxy() {
        var prototype = new Prototype<>(Ping.class, PingService::new);
        prototype.withProxies(ProxyDefinition.around("ping", new PongAspect("pong")));
        var pingService = prototype.getValue();
        assertEquals(pingService.ping(), "pong");
    }

    @Test
    void shouldBeAbleToAddMultipleProxy() {
        var prototype = new Prototype<>(Ping.class, PingService::new);
        prototype.withProxies(ProxyDefinition.around("ping", new PongAspect("pong")));
        prototype.withProxies(ProxyDefinition.around("ping", new PongAspect("pang")));
        var pingService = prototype.getValue();
        assertEquals(pingService.ping(), "pang");
    }
}