package org.mti.hivers;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class Scope {
    Map<Class<Object>, Singleton> singletonProviders;
    Map<Class<Object>, Prototype> prototypeProviders;

    Scope() {
        singletonProviders = new HashMap<>();
        prototypeProviders = new HashMap<>();
    }

}
