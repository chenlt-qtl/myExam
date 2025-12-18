package com.exam.o2_pattern.o1_singleton.o3_register;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ContainerSingleton {
    private ContainerSingleton() {
    }

    private static Map<String, Object> ioc = new ConcurrentHashMap<>();

    public static Object getInstance(String className) {
        if (!ioc.containsKey(className)) {
            synchronized (ioc) {
                if (!ioc.containsKey(className)) {
                    try {
                        Object o = Class.forName(className).newInstance();
                        ioc.put(className, o);
                        return o;
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
        return ioc.get(className);

    }
}
