package org.mti.hivers.aspect;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class LoggerAspect implements Aspect {
    public LoggerAspect() {
    }

    @Override
    public Object apply(Object target, Method method, Object[] args) throws InvocationTargetException, IllegalAccessException {
        System.out.println("Method have been called");
        return method.invoke(target, args);
    }
}
