package org.mti.hivers;

import org.mti.hivers.provider.Prototype;
import org.mti.hivers.provider.Provider;
import org.mti.hivers.provider.Singleton;

import java.util.*;

public class Hivers {
    private List<Scope> scopes;

    Hivers() {
        this.scopes = new ArrayList<>();
        scopes.add(new Scope());
    }

    public Provider provider(Provider provider) {
        Scope scope = this.getCurrentScope();
        scope.providers.put(provider.getBoundClass(), provider);
        return provider;
    }

    public<T> Optional<T> instanceOf(Class<T> bindingObject) {
        for (var i = this.scopes.size() - 1; i >= 0; i--) {
            Scope scope = this.scopes.get(i);

            if (scope.providers.containsKey(bindingObject)) {
                return (Optional<T>) Optional.of(scope.providers.get(bindingObject).getValue());
            }
        }

        return Optional.empty();
    }

    public void push(Scope scope) {
        this.scopes.add(scope);
    }

    public void pop() {
        this.scopes.remove(this.scopes.size() - 1);
    }

    private Scope getCurrentScope() {
        return this.scopes.get(this.scopes.size() - 1);
    }
}
