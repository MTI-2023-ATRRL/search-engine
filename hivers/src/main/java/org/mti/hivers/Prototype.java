package org.mti.hivers;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class Prototype<T> {
    static private Map<Class<Object>, Supplier<Object>> prototypes = new HashMap<>();
    Prototype(Class<T> bindingClass, Supplier<T> boundSupplier) {
        if (prototypes.containsKey(bindingClass)) {
            return;
        }

        prototypes.put((Class<Object>) bindingClass, (Supplier<Object>) boundSupplier);
    }

    public T instanceOf(Class<T> bindingClass) {
        return (T) prototypes.get(bindingClass).get();
    }
}
