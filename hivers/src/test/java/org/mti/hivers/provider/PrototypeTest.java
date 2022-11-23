package org.mti.hivers.provider;

import org.junit.jupiter.api.Test;
import org.mti.hivers.aspect.PongAspect;
import org.mti.hivers.proxy.ProxyDefinition;

import static org.junit.jupiter.api.Assertions.*;

class PrototypeTest {

    public interface Ping {
        public String ping();
    }

    public static class PingService implements Ping {
        public String ping() {
            return "Ping";
        }
    }

    @Test
    void shoudBeAbleToCreatePrototype() {
        Prototype<Ping> prototype = new Prototype<>(Ping.class, PingService::new);
        assertNotNull(prototype);
    }

    @Test
    void shouldBeAbleToCreatePrototypeAndGetAnInstance() {
        var prototype = new Prototype<>(Ping.class, PingService::new);
        var pingService = prototype.getValue();
        assertEquals(pingService.ping(), "Ping");
    }

    @Test
    void shouldBeAbleToCreateMultipleInstanceOfPrototype() {
        var prototype = new Prototype<>(Ping.class, PingService::new);
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
        prototype.withProxies(ProxyDefinition.around("ping", new PongAspect("pong")), ProxyDefinition.around("ping", new PongAspect("pang")));
        var pingService = prototype.getValue();
        assertEquals(pingService.ping(), "pang");
    }

    @Test
    void shouldBeAbleToAddInitProxy() {
        var prototype = new Prototype<>(Ping.class, PingService::new);
        prototype.withProxies(ProxyDefinition.init(() -> System.out.println("Hello !")));
        prototype.getValue();
    }
}