package com.exam.ioc;

public class MyBeanWrapper {
    private Object wrappedInstance;
    private Class<?> wrappedClass;
    public MyBeanWrapper(Object object) {
        wrappedInstance = object;
        wrappedClass = object.getClass();
    }

    public Object getWrappedInstance() {
        return wrappedInstance;
    }

    public Class<?> getWrappedClass() {
        return wrappedClass;
    }
}
