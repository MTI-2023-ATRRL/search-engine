package org.mti.hivers.provider;

public interface Provider<BOUND_TYPE> {
    BOUND_TYPE getValue();

    Class<BOUND_TYPE> getBoundClass();
}
