package org.mti.hivers.aspect;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public interface Aspect {
    Object apply(Object target, Method method, Object[] args) throws InvocationTargetException, IllegalAccessException;

}
