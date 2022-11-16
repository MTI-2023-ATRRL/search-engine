package org.mti.hivers;

import java.util.HashMap;
import java.util.Map;

public class Singleton<T> {
    static private Map<Class<Object>, Object> createdSingletons = new HashMap<>();

    Singleton(Class<T> bindingObject, T boundInstance) {
        if (Singleton.createdSingletons.containsKey(bindingObject)) {
            return;
        }
        Singleton.createdSingletons.put((Class<Object>) bindingObject, boundInstance);
    }

    public T instanceOf(Class<T> classObject) {
        return (T) Singleton.createdSingletons.get(classObject);
    }
}
