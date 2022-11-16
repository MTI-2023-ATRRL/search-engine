package org.mti.hivers;

import org.junit.jupiter.api.Test;
import org.mti.hivers.provider.Singleton;

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
        assertNotNull(singleton.getValue());
    }

    @Test
    void shouldBeAbleToCreateAndRetrieveAndUseIt() {
        var singleton = new Singleton<>(PingService.class, new PingService());
        assertEquals(singleton.getValue().ping(), "Ping");
    }

    @Test
    void shouldBeAbleToCreateWithLamba() {
        var singleton = new Singleton<>(PingService.class, PingService::new);
        var pingService = singleton.getValue();
        assertNotNull(pingService);
    }

    @Test
    void shouldOnlyCreateOneInstance() {
        var singleton = new Singleton<>(PingService.class, PingService::new);
        assertEquals(singleton.getValue(), singleton.getValue());
    }

    @Test
    void test() {
        var singleton = new Singleton<>(PingService.class, PingService::new);
    }
}