package org.mti.hivers;

import org.junit.jupiter.api.Test;

import java.security.Signature;

import static org.junit.jupiter.api.Assertions.*;

class SingletonTest {

    public static class PingService {
        PingService() {}
        public String ping() { return "Ping";}
    }

    @Test
    void shouldCreateSingleton() {
        var singleton = new Singleton<>(PingService.class, new PingService());
        assertNotNull(singleton);
    }

    @Test
    void shouldBeAbleToCreateAndRetrieveSingleton() {
        var singleton = new Singleton<>(PingService.class, new PingService());
        assertNotNull(singleton.instanceOf());
    }

    @Test
    void shouldBeAbleToCreateAndRetrieveAndUseIt() {
        var singleton = new Singleton<>(PingService.class, new PingService());
        assertEquals(singleton.instanceOf().ping(), "Ping");
    }

    @Test
    void shouldBeAbleToCreateWithLamba() {
        var singleton = new Singleton<>(PingService.class, PingService::new);
        var pingService = singleton.instanceOf();
        assertNotNull(pingService);
    }

    @Test
    void shouldOnlyCreateOneInstance() {
        var singleton = new Singleton<>(PingService.class, PingService::new);
        assertEquals(singleton.instanceOf(), singleton.instanceOf());
    }
}