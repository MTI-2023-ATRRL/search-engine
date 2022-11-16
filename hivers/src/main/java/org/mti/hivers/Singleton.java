package org.mti.hivers;

import java.util.HashMap;
import java.util.Map;

public class Singleton<T> {
    static private Map<Class<Object>, Object> createdSingletons = new HashMap<>();

    Singleton(Class<T> classObject, T classInstance) {
        if (Singleton.createdSingletons.containsKey(classObject)) {
            return;
        }
        Singleton.createdSingletons.put((Class<Object>) classObject, classInstance);
    }

    public T instanceOf(Class<T> classObject) {
        return (T) Singleton.createdSingletons.get(classObject);
    }
}
