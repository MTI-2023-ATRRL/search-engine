package org.mti.hivers.server;

import java.util.function.Consumer;

public interface Extension {
    interface Method {}
    Extension register(Method verb, String path, Consumer<Context> callback);
    void start();
    void shutdown();
}