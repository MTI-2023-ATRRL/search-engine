package org.mti.hivers;

import java.util.*;

public class Hivers {
    private List<Scope> scopes;

    Hivers() {
        this.scopes = new ArrayList<>();
        scopes.add(new Scope());
    }

    public Hivers provider(Prototype prototype) {
        Scope scope = this.getCurrentScope();
        scope.prototypeProviders.put(prototype.bindingObject, prototype);
        return this;
    }

    public Hivers provider(Singleton singleton) {
        Scope scope = this.getCurrentScope();
        scope.singletonProviders.put(singleton.bindingObject, singleton);
        return this;
    }

    public<T> Optional<T> instanceOf(Class<T> bindingObject) {
        for (var i = this.scopes.size() - 1; i >= 0; i--) {
            Scope scope = this.scopes.get(i);

            if (scope.singletonProviders.containsKey(bindingObject)) {
                return (Optional<T>) Optional.of(scope.singletonProviders.get(bindingObject).instanceOf());
            }

            if (scope.prototypeProviders.containsKey(bindingObject)) {
                return (Optional<T>) Optional.of(scope.prototypeProviders.get(bindingObject).instanceOf());
            }
        }

        return Optional.ofNullable(null);
    }

    public void withProxies(boolean first, boolean second) {
        return;
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
