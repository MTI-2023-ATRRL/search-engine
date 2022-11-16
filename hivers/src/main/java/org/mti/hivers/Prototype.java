package org.mti.hivers;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

public class Prototype<T> {
    static private Map<Class<Object>, Supplier<Object>> prototypes = new HashMap<>();
    Prototype(Class<T> bindingClass, Supplier<T> boundSupplier) {
        if (prototypes.containsKey(bindingClass)) {
            return;
        }

        prototypes.put((Class<Object>) bindingClass, (Supplier<Object>) boundSupplier);
    }

    public Optional<T> instanceOf(Class<T> bindingClass) {
        if (prototypes.containsKey(bindingClass)) {
            return Optional.of((T)prototypes.get(bindingClass).get());
        }

        return Optional.empty();
    }
}
