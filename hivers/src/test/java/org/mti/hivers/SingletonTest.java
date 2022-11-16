package org.mti.hivers;

import org.junit.jupiter.api.Test;

import java.security.Signature;

import static org.junit.jupiter.api.Assertions.*;

class SingletonTest {

    public static class PingService {
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
        assertNotNull(singleton.instanceOf(PingService.class));
    }

    @Test
    void shouldBeAbleToCreateAndRetrieveAndUseIt() {
        var singleton = new Singleton<>(PingService.class, new PingService());
        assertEquals(singleton.instanceOf(PingService.class).ping(), "Ping");
    }

    @Test
    void shouldntCreateMultipleInstance() {
        var singleton1 = new Singleton<>(PingService.class, new PingService());
        var singleton2 = new Singleton<>(PingService.class, new PingService());

        assert (singleton1.instanceOf(PingService.class).equals(singleton2.instanceOf(PingService.class)));
    }
}