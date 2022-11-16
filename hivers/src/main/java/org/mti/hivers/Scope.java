package org.mti.hivers;

import org.mti.hivers.provider.Provider;
import org.mti.hivers.provider.Singleton;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Scope {
    private Map<Class<?>, Provider> providers;

    Scope() {
        providers = new HashMap<>();
    }

    public<OBJECT_TYPE> void setProvider(Class<OBJECT_TYPE> bindingObject, Provider provider) {
        this.providers.put(bindingObject, provider);
    }

    public<OBJECT_TYPE> Optional<Provider> getProvider(Class<OBJECT_TYPE> bindingObject) {
        if (this.providers.containsKey(bindingObject)) {
            return Optional.of(this.providers.get(bindingObject));
        }
        return Optional.empty();
    }
}
