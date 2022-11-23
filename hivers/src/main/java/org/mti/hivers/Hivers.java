package org.mti.hivers;

import org.mti.hivers.provider.Prototype;
import org.mti.hivers.provider.Provider;
import org.mti.hivers.provider.Singleton;
import org.mti.hivers.server.Extension;
import org.mti.hivers.server.RestHivers;

import java.util.*;

public class Hivers {
    private final List<Scope> scopes;

    private Map<Class<?>, Extension> extensions;

    Hivers() {
        this.scopes = new ArrayList<>();
        this.extensions = new HashMap<>();
        scopes.add(new Scope());
    }

    public Provider provider(Provider provider) {
        Scope scope = this.getCurrentScope();
        scope.setProvider(provider.getBoundClass(), provider);
        return provider;
    }

    public<OBJECT_TYPE> Optional<OBJECT_TYPE> instanceOf(Class<OBJECT_TYPE> bindingObject) {
        for (var i = this.scopes.size() - 1; i >= 0; i--) {
            Scope scope = this.scopes.get(i);

            if (scope.getProvider(bindingObject).isPresent()) {
                return Optional.ofNullable((OBJECT_TYPE)scope.getProvider(bindingObject).get().getValue());
            }
        }

        return Optional.empty();
    }

    public Scope push(Scope scope) {
        this.scopes.add(scope);
        return scope;
    }

    public void pop() {
        this.scopes.remove(this.scopes.size() - 1);
    }

    private Scope getCurrentScope() {
        return this.scopes.get(this.scopes.size() - 1);
    }

    public void register(Extension extension) {
        extensions.put(extension.getClass(), extension);
    }

    public Extension extension(Class clazz)
    {
        return extensions.get(clazz);
    }
}
