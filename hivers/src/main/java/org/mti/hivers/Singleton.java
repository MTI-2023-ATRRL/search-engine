package org.mti.hivers;

import javax.management.ObjectInstance;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class Singleton<T> {
    static private Map<Class<Object>, Object> createdSingletons = new HashMap<>();

    Singleton(Class<T> bindingObject, Supplier<T> fn) {
        if (Singleton.createdSingletons.containsKey(bindingObject)) {
            return;
        }
        Singleton.createdSingletons.put((Class<Object>) bindingObject, fn.get());
    }

    Singleton(Class<? extends T> bindingObject, T boundInstance) {
        if (Singleton.createdSingletons.containsKey(bindingObject)) {
            return;
        }
        Singleton.createdSingletons.put((Class<Object>) bindingObject, boundInstance);
    }

    public T instanceOf(Class<T> classObject) {
        return (T) Singleton.createdSingletons.get(classObject);
    }
}
