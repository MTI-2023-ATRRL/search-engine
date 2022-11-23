package org.mti.hivers.provider;

import org.mti.hivers.proxy.ProxyDefinition;

import java.util.ArrayList;
import java.util.List;

public interface Provider<BOUND_TYPE> {
    List<ProxyDefinition> proxys = new ArrayList<>();

    BOUND_TYPE getValue();

    Class<BOUND_TYPE> getBoundClass();

    Provider withProxies(ProxyDefinition ...proxyList);
}
