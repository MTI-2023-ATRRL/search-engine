package org.mti.hivers.provider;

import org.mti.hivers.provider.Provider;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

public class Prototype<BOUND_TYPE> implements Provider<BOUND_TYPE> {
    Supplier<BOUND_TYPE> boundSupplier;
    Class<BOUND_TYPE> bindingObject;

    public Prototype(Class<BOUND_TYPE> bindingObject, Supplier<BOUND_TYPE> boundSupplier) {
        this.boundSupplier = boundSupplier;
        this.bindingObject = bindingObject;
    }

    @Override
    public BOUND_TYPE getValue() {
        return this.boundSupplier.get();
    }

    @Override
    public Class<BOUND_TYPE> getBoundClass() {
        return this.bindingObject;
    }
}
