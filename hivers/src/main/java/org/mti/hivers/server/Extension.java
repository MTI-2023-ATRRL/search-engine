package org.mti.hivers.server;

import java.util.function.Consumer;
import java.util.function.Function;

public interface Extension {

    interface Method {}

    //Extension register(Method verb, String path,Function<Context, Context>  callback);
    Extension register(Method verb, String path, Consumer<Context> callback);
    void start();
    void shutdown();
}