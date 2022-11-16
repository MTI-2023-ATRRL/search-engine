package org.mti.hivers;

import org.mti.hivers.provider.Provider;
import org.mti.hivers.provider.Singleton;

import java.util.HashMap;
import java.util.Map;

public class Scope {
    Map<Class<Object>, Provider> providers;

    Scope() {
        providers = new HashMap<>();
    }

}
