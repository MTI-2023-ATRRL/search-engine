package org.mti.hivers.provider;

import org.mti.hivers.provider.Provider;

import javax.management.ObjectInstance;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class Singleton<BOUND_TYPE> implements Provider<BOUND_TYPE> {
    Class<BOUND_TYPE> bindingObject;
    Supplier<BOUND_TYPE> boundSupplier;

    private BOUND_TYPE instance;

    public Singleton(Class<BOUND_TYPE> bindingObject, Supplier<BOUND_TYPE> boundSupplier) {
       this.bindingObject = bindingObject;
       this.boundSupplier = boundSupplier;
    }

    public Singleton(Class<? extends BOUND_TYPE> bindingObject, BOUND_TYPE boundInstance) {
        this.bindingObject = (Class<BOUND_TYPE>) bindingObject;
        this.boundSupplier = () -> boundInstance;
    }

    @Override
    public BOUND_TYPE getValue() {
        if (instance == null) {
            instance = boundSupplier.get();
        }
        return instance;
    }

    @Override
    public Class<BOUND_TYPE> getBoundClass() {
        return this.bindingObject;
    }
}