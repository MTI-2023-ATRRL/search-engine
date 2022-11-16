package org.mti.hivers;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

public class Prototype<T> {
    Supplier<T> boundSupplier;
    Class<T> bindingObject;

    Prototype(Class<T> bindingObject, Supplier<T> boundSupplier) {
        this.boundSupplier = boundSupplier;
        this.bindingObject = bindingObject;
    }

    public T instanceOf() {
        return this.boundSupplier.get();
    }
}
