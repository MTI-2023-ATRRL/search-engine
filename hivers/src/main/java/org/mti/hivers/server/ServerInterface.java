package org.mti.hivers.server;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public interface ServerInterface {

    enum Method {}

    ServerInterface register(Method verb, String path);
}