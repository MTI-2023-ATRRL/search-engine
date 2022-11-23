package org.mti.hivers.proxy;

import org.mti.hivers.proxy.ProxyDefinition;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class ProxyHandler implements InvocationHandler {
    private final Object target;
    public ProxyDefinition proxyDefinition;

    public ProxyHandler(Object target, ProxyDefinition proxyDefinition) {
        this.target = target;
        this.proxyDefinition = proxyDefinition;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String name = method.getName();

        if (name.equals(proxyDefinition.bindingFunctionName)) {
            return proxyDefinition.aspect.apply(target, method, args);
        }

        return method.invoke(target, args);
    }
}
