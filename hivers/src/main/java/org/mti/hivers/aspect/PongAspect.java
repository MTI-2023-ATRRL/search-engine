package org.mti.hivers.aspect;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class PongAspect implements Aspect {
    public String newReturnValue;

    public PongAspect(String newReturnValue) {
         this.newReturnValue = newReturnValue;
    }

    @Override
    public Object apply(Object target, Method method, Object[] args) throws InvocationTargetException, IllegalAccessException {
        return newReturnValue;
    }
}
