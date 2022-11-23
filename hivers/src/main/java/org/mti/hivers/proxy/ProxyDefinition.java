package org.mti.hivers.proxy;

import org.mti.hivers.aspect.Aspect;

public class ProxyDefinition {
    public enum PROXY_TYPE {
        INIT,
        AROUND,
    }

    public static ProxyDefinition around(String bindingFunction, Aspect aspect) {
        return new ProxyDefinition(PROXY_TYPE.AROUND, bindingFunction, aspect);
    }

    public static ProxyDefinition init(Runnable runnable ) {
        return new ProxyDefinition(PROXY_TYPE.INIT, runnable);
    }

    public String bindingFunctionName;
    public Aspect aspect;
    public PROXY_TYPE proxyType;

    public Runnable initRunnable;

    ProxyDefinition(PROXY_TYPE proxyType, Runnable runnable) {
        this.proxyType = proxyType;
        this.initRunnable = runnable;
    }

    ProxyDefinition(PROXY_TYPE proxyType, String bindingFunctionName, Aspect aspect) {
        this.proxyType = proxyType;
        this.bindingFunctionName = bindingFunctionName;
        this.aspect = aspect;
    }
}
