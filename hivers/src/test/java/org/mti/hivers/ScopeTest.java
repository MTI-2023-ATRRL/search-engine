package org.mti.hivers;

import org.junit.jupiter.api.Test;
import org.mti.hivers.provider.Singleton;

import static org.junit.jupiter.api.Assertions.*;

public class ScopeTest {
    public static class PingService {
        public String ping() {
            return "Ping";
        }
    }

    @Test
    public void shouldBeAbleToCreateAScope() {
        Scope scope = new Scope();
        assertNotNull(scope);
    }

    @Test
    public void shouldGetNullWhenAskingForAnUnsetProvider() {
        Scope scope = new Scope();
        assertTrue(scope.getProvider(PingService.class).isEmpty());
    }

    @Test
    public void shouldGetProviderWhenSettingProvider() {
        Scope scope = new Scope();
        scope.setProvider(PingService.class, new Singleton(PingService.class, PingService::new));
        assertTrue(scope.getProvider(PingService.class).isPresent());
    }

}
