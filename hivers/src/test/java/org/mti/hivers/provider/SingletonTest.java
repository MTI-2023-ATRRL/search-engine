package org.mti.hivers.provider;

import org.junit.jupiter.api.Test;
import org.mti.hivers.provider.Singleton;

import static org.junit.jupiter.api.Assertions.*;

public class SingletonTest {

    public static class PingService {
        PingService() {}
        public String ping() { return "Ping";}
    }

    @Test
    public void shouldCreateSingleton() {
        var singleton = new Singleton<>(PingService.class, new PingService());
        assertNotNull(singleton);
    }

    @Test
    public void shouldBeAbleToCreateAndRetrieveSingleton() {
        var singleton = new Singleton<>(PingService.class, new PingService());
        assertNotNull(singleton.getValue());
    }

    @Test
    public void shouldBeAbleToCreateAndRetrieveAndUseIt() {
        var singleton = new Singleton<>(PingService.class, new PingService());
        assertEquals(singleton.getValue().ping(), "Ping");
    }

    @Test
    public void shouldBeAbleToCreateWithLamba() {
        var singleton = new Singleton<>(PingService.class, PingService::new);
        var pingService = singleton.getValue();
        assertNotNull(pingService);
    }

    @Test
    public void shouldOnlyCreateOneInstance() {
        var singleton = new Singleton<>(PingService.class, PingService::new);
        assertEquals(singleton.getValue(), singleton.getValue());
    }

    @Test
    public void test() {
        var singleton = new Singleton<>(PingService.class, PingService::new);
    }
}