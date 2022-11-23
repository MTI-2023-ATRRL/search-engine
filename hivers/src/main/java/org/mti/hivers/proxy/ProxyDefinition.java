package org.mti.hivers.proxy;

import org.mti.hivers.aspect.Aspect;

public class ProxyDefinition {

    public static ProxyDefinition around(String bindingFunction, Aspect aspect) {
        return new ProxyDefinition(bindingFunction, aspect);
    }

    public static boolean init(Runnable runnable ) {
        return true;
    }

    public String bindingFunctionName;
    public Aspect aspect;

    ProxyDefinition(String bindingFunctionName, Aspect aspect) {
        this.bindingFunctionName = bindingFunctionName;
        this.aspect = aspect;
    }
}
