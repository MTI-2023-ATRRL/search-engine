package org.mti.hivers.provider;

import org.mti.hivers.proxy.ProxyDefinition;
import org.mti.hivers.proxy.ProxyHandler;

import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

public interface Provider<BOUND_TYPE> {
    List<ProxyDefinition> proxys = new ArrayList<>();

    BOUND_TYPE getValue();

    Class<BOUND_TYPE> getBoundClass();

    default Provider withProxies(ProxyDefinition... proxyList) {
        for (var proxy: proxyList) {
            this.proxys.add(proxy);
        }
        return this;
    }

    default BOUND_TYPE applyProxies(BOUND_TYPE instance, Class<BOUND_TYPE> bindingObject) {
        for (ProxyDefinition proxy: this.proxys) {
            if (proxy.proxyType == ProxyDefinition.PROXY_TYPE.AROUND) {
                instance = (BOUND_TYPE) Proxy.newProxyInstance(
                        bindingObject.getClassLoader(),
                        new Class<?>[] { bindingObject },
                        new ProxyHandler(instance, proxy)
                );
            } else if (proxy.proxyType == ProxyDefinition.PROXY_TYPE.INIT) {
                proxy.initRunnable.run();
            }
        }
        return instance;
    }
}