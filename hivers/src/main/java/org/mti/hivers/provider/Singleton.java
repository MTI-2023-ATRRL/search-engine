package org.mti.hivers.provider;

import org.mti.hivers.proxy.ProxyDefinition;
import org.mti.hivers.proxy.ProxyHandler;

import java.lang.reflect.Proxy;
import java.util.function.Supplier;

public class Singleton<BOUND_TYPE> implements Provider<BOUND_TYPE> {
    private final Class<BOUND_TYPE> bindingObject;
    private final Supplier<BOUND_TYPE> boundSupplier;

    private BOUND_TYPE instance;

    public Singleton(final Class<BOUND_TYPE> bindingObject, final Supplier<BOUND_TYPE> boundSupplier) {
       this.bindingObject = bindingObject;
       this.boundSupplier = boundSupplier;
    }

    public Singleton(final Class<? extends BOUND_TYPE> bindingObject, final BOUND_TYPE boundInstance) {
        this.bindingObject = (Class<BOUND_TYPE>) bindingObject;
        this.boundSupplier = () -> boundInstance;
    }

    @Override
    public BOUND_TYPE getValue() {
        if (instance == null) {
            instance = boundSupplier.get();
            for (ProxyDefinition proxy: this.proxys) {
                if (proxy.proxyType == ProxyDefinition.PROXY_TYPE.AROUND) {
                    instance = (BOUND_TYPE) Proxy.newProxyInstance(
                            this.bindingObject.getClassLoader(),
                            new Class<?>[] { this.bindingObject },
                            new ProxyHandler(instance, proxy)
                    );
                } else if (proxy.proxyType == ProxyDefinition.PROXY_TYPE.INIT) {
                    proxy.initRunnable.run();
                }
            }
        }
        return instance;
    }

    @Override
    public Class<BOUND_TYPE> getBoundClass() {
        return this.bindingObject;
    }

    @Override
    public Provider withProxies(ProxyDefinition... proxyList) {
        for (var proxy: proxyList) {
            this.proxys.add(proxy);
        }
        return this;
    }
}
