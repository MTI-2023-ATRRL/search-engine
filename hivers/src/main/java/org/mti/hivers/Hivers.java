package org.mti.hivers;

import java.util.*;

public class Hivers {
    private Singleton singleton;
    private Prototype prototype;

    Hivers() {
        return;
    }

    public void provider(Prototype prototype) {
        this.prototype = prototype;
    }

    public void provider(Singleton singleton) {
        this.singleton = singleton;
    }

    public<T> Optional<T> instanceOf(Class<T> bindingClass) {
        if (singleton != null && singleton.instanceOf(bindingClass).isPresent()) {
            return singleton.instanceOf(bindingClass);
        }
        if (prototype != null && prototype.instanceOf(bindingClass).isPresent()) {
            return prototype.instanceOf(bindingClass);
        }

        return Optional.ofNullable(null);
    }
}
