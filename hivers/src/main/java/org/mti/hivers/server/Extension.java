package org.mti.hivers.server;

import java.util.function.Function;

public interface Extension {

    interface Method {}

    Extension register(Method verb, String path, Function<Context, Context> callback);
    void start();
    void shutdown();
}