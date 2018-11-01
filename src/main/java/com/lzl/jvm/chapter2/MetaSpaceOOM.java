package com.lzl.jvm.chapter2;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Map;

/**
 * -Xmx2g -Xms2g -Xmn1g -XX:+PrintGCDetails -XX:MaxMetaspaceSize=1m
 */
public class MetaSpaceOOM {


    public interface MetaspaceFacade {
        void method(String input);
    }

    static class MetaspaceFacadeImpl implements MetaspaceFacade {

        public void method(String name) {
        }
    }

    static class MetaspaceFacadeInvocationHandler implements InvocationHandler {

        private Object classAImpl;

        public MetaspaceFacadeInvocationHandler(Object impl) {
            this.classAImpl = impl;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

            return method.invoke(classAImpl, args);
        }
    }

    private static Map<String, MetaspaceFacade> classLeakingMap = new HashMap<String, MetaspaceFacade>();
    private final static int NB_ITERATIONS_DEFAULT = 50000;


    public static void main(String[] args) {
        System.out.println("Class metadata leak simulator");

        int nbIterations = NB_ITERATIONS_DEFAULT;

        try {

            for (int i = 0; i < nbIterations; i++) {

                String fictiousClassloaderJAR = "file:" + i + ".jar";

                URL[] fictiousClassloaderURL = new URL[] { new URL(fictiousClassloaderJAR) };

                URLClassLoader newClassLoader = new URLClassLoader(fictiousClassloaderURL);

                MetaspaceFacade t = (MetaspaceFacade) Proxy.newProxyInstance(newClassLoader,
                        new Class<?>[] { MetaspaceFacade.class },
                        new MetaspaceFacadeInvocationHandler(new MetaspaceFacadeImpl()));

                classLeakingMap.put(fictiousClassloaderJAR, t);
            }
        }
        catch (Throwable any) {
            System.out.println("ERROR: " + any);
        }

        System.out.println("Done!");
    }
}
