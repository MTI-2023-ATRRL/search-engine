package org.mti.hivers.provider;

import org.junit.jupiter.api.Test;
import org.mti.hivers.aspect.PongAspect;
import org.mti.hivers.proxy.ProxyDefinition;

import static org.junit.jupiter.api.Assertions.*;

public class PrototypeTest {

    public interface Ping {
        public String ping();
    }

    public static class PingService implements Ping {
        public String ping() {
            return "Ping";
        }
    }

    @Test
    public void shoudBeAbleToCreatePrototype() {
        Prototype<Ping> prototype = new Prototype<>(Ping.class, PingService::new);
        assertNotNull(prototype);
    }

    @Test
    public void shouldBeAbleToCreatePrototypeAndGetAnInstance() {
        var prototype = new Prototype<>(Ping.class, PingService::new);
        var pingService = prototype.getValue();
        assertEquals(pingService.ping(), "Ping");
    }

    @Test
    public void shouldBeAbleToCreateMultipleInstanceOfPrototype() {
        var prototype = new Prototype<>(Ping.class, PingService::new);
        assertNotEquals(prototype.getValue(), prototype.getValue());
    }

    @Test
    public void shouldBeAbleToAddProxy() {
        var prototype = new Prototype<>(Ping.class, PingService::new);
        prototype.withProxies(ProxyDefinition.around("ping", new PongAspect("pong")));
        var pingService = prototype.getValue();
        assertEquals(pingService.ping(), "pong");
    }

    @Test
    public void shouldBeAbleToAddMultipleProxy() {
        var prototype = new Prototype<>(Ping.class, PingService::new);
        prototype.withProxies(ProxyDefinition.around("ping", new PongAspect("pong")), ProxyDefinition.around("ping", new PongAspect("pang")));
        var pingService = prototype.getValue();
        assertEquals(pingService.ping(), "pang");
    }

    @Test
    public void shouldBeAbleToAddInitProxy() {
        var prototype = new Prototype<>(Ping.class, PingService::new);
        prototype.withProxies(ProxyDefinition.init(() -> System.out.println("Hello !")));
        prototype.getValue();
    }
}