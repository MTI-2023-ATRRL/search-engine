package org.mti.hivers;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

public class Prototype<T> {
    Supplier<T> boundSupplier;
    Class<T> bindingClass;

    Prototype(Class<T> bindingClass, Supplier<T> boundSupplier) {
        this.boundSupplier = boundSupplier;
        this.bindingClass = bindingClass;
    }

    public T instanceOf() {
        return this.boundSupplier.get();
    }
}
