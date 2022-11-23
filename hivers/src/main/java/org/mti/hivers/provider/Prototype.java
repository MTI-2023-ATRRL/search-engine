package org.mti.hivers.provider;

import org.mti.hivers.proxy.ProxyDefinition;
import org.mti.hivers.proxy.ProxyHandler;

import java.lang.reflect.Proxy;
import java.util.function.Supplier;

public class Prototype<BOUND_TYPE> implements Provider<BOUND_TYPE> {
    private final Supplier<BOUND_TYPE> boundSupplier;
    private final Class<BOUND_TYPE> bindingObject;

    public Prototype(final Class<BOUND_TYPE> bindingObject, final Supplier<BOUND_TYPE> boundSupplier) {
        this.boundSupplier = boundSupplier;
        this.bindingObject = bindingObject;
    }

    @Override
    public BOUND_TYPE getValue() {
        var instance = this.boundSupplier.get();
        return applyProxies(instance, bindingObject);
    }

    @Override
    public Class<BOUND_TYPE> getBoundClass() {
        return this.bindingObject;
    }
}
