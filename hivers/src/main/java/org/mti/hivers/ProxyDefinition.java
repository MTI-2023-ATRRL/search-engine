package org.mti.hivers;

import org.mti.hivers.aspect.Aspect;
import org.mti.hivers.aspect.LoggerAspect;

import java.util.function.Supplier;

public class ProxyDefinition {
    public static boolean around(String bindingFunction, Aspect aspect) {
        return true;
    }

    public static boolean init(Runnable runnable ) {
        return true;
    }

}
