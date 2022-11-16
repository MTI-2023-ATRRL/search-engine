package org.mti.hivers;

import javax.management.ObjectInstance;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class Singleton<T> {
    T boundInstance;
    Class<T> bindingObject;

    Singleton(Class<T> bindingObject, Supplier<T> fn) {
       this.bindingObject = bindingObject;
       this.boundInstance = fn.get();
    }

    Singleton(Class<? extends T> bindingObject, T boundInstance) {
        this.bindingObject = (Class<T>) bindingObject;
        this.boundInstance = boundInstance;
    }

    public T instanceOf() {
        return boundInstance;
    }

    public Class<T> getBindingObject() {
        return bindingObject;
    }
}
