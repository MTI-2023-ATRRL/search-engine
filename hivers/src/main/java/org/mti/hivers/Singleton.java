package org.mti.hivers;

import javax.management.ObjectInstance;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class Singleton<T> {
    static private Map<Class<Object>, Object> createdSingletons = new HashMap<>();

    Singleton(Class<T> bindingObject, Supplier<T> fn) {
        if (createdSingletons.containsKey(bindingObject)) {
            return;
        }
        createdSingletons.put((Class<Object>) bindingObject, fn.get());
    }

    Singleton(Class<? extends T> bindingObject, T boundInstance) {
        if (createdSingletons.containsKey(bindingObject)) {
            return;
        }
        createdSingletons.put((Class<Object>) bindingObject, boundInstance);
    }

    public Optional<T> instanceOf(Class<T> classObject) {
        if (createdSingletons.containsKey(classObject)) {
            return (Optional<T>) Optional.of((T)createdSingletons.get(classObject));
        }

        return Optional.empty();
    }
}
