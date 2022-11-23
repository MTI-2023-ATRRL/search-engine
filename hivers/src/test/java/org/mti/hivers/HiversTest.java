
package org.mti.hivers;

import org.junit.jupiter.api.Test;
import org.mti.hivers.provider.Prototype;
import org.mti.hivers.provider.Singleton;
import org.mti.hivers.server.RestHivers;

import static org.junit.jupiter.api.Assertions.*;

class HiversTest {

    public void times(Integer max, Runnable runnable) {
        for (var i = 0; i < max; i++) {
            runnable.run();
        }
    }

    @Test
    public void shouldBeAbleToCreateHivers() {
        var hivers = new Hivers();
        assertNotNull(hivers);
    }

    @Test
    public void shouldBeAbleToCreateHiversAndInsertSingleton() {
        var hivers = new Hivers();
        hivers.provider(new Singleton(PingService.class, new PingService()));
        var pingService = hivers.instanceOf(PingService.class).orElseThrow();
        assertEquals(pingService.pong(), "Pong");
    }

    @Test
    public void shouldNotBeAbleToGetInstanceOfPingServiceIfNotInstanced() {
        var hivers = new Hivers();
        assert(hivers.instanceOf(PingService.class).isEmpty());
    }

    @Test
    public void testBasicHiversUserCase() {

        // Init.
        final var hivers = new Hivers();

        // Add providers
        hivers.provider(new Prototype<>(TestService.class, PingService::new));
        hivers.provider(new Prototype<>(Nested.class, () -> new Nested(hivers.instanceOf(TestService.class).orElseThrow())));
        hivers.provider(new Singleton<>(Nested.class, new Nested(hivers.instanceOf(TestService.class).orElseThrow())));

        // Test instance resolution
        hivers.instanceOf(TestService.class).orElseThrow().ping();
        times(3, () -> System.out.println(hivers.instanceOf(TestService.class).orElseThrow()));
        times(3, () -> System.out.println(hivers.instanceOf(Nested.class).orElseThrow()));

        // New scope and test instance resolution
        hivers.push(new Scope());
        hivers.provider(new Singleton<>(TestService.class, new PongService()));
        times(3, () -> System.out.println(hivers.instanceOf(TestService.class).orElseThrow()));

        hivers.instanceOf(TestService.class).orElseThrow().ping();

        // Pop scope and test instance resolution
        hivers.pop();
        hivers.instanceOf(TestService.class).orElseThrow().ping();

//        // Aspects
//        hivers.push(new Scope());
//        hivers.provider(new Singleton<>(TestService.class, new PongService()))
//                .withProxies(ProxyDefinition.around("ping", new LoggerAspect()),
//                        ProxyDefinition.init(() -> System.out.println("Service init.")));
//        hivers.instanceOf(TestService.class).orElseThrow().ping();
//        hivers.pop();
//
//        // Extension
//        hivers.push(new DefaultScope());
        hivers.register(new RestHivers());
        hivers.extension(RestHivers.class)
                .register(RestHivers.Method.GET, "/hello", context -> context.response(200, "Hello, world!"))
                .register(RestHivers.Method.DELETE, "/", context -> {
                    hivers.extension(RestHivers.class).shutdown();
                    context.response(204);
                })
                .start();

    }

    public interface TestService {
        void ping();
    }

    public static class PingService implements TestService {
        @Override
        public void ping() {
            System.out.println("ping");
        }

        public String pong() {
            return "Pong";
        }

        ;
    }

    public static class PongService implements TestService {
        @Override
        public void ping() {
            System.out.println("pong");
        }
    }

    public static class Nested {
        private final TestService testService;

        public Nested(final TestService testService) {
            this.testService = testService;
        }
    }

}
