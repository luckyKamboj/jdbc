package com.company.database.context;

import com.company.database.annotation.BeanName;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class BeanContext {
    private BeanContext(){}
    private static final Map<String, Class<?>>BEAN_CACHE = new HashMap<>();
    public static Map<String, Class<?>> getBeanCache() {
        Set<Class<?>> allClasses = getClassesOfPackage("com.company.database.convertor");
        for (Class<?> tClass: allClasses) {
            if(tClass.isAnnotationPresent(BeanName.class)){
                String value =  tClass.getAnnotation(BeanName.class).name();
                if(!value.isBlank()){
                    BEAN_CACHE.put(value, tClass);
                    continue;
                }
            }
            BEAN_CACHE.put(tClass.getSimpleName(), tClass);
        }
        return BEAN_CACHE;
    }

    private static Set<Class<?>> getClassesOfPackage(String packageName) {
            InputStream stream = ClassLoader.getSystemClassLoader()
                    .getResourceAsStream(packageName.replaceAll("[.]", "/"));
        assert stream != null;
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
            return reader.lines()
                    .filter(line -> line.endsWith(".class"))
                    .map(line -> getClass(line, packageName))
                    .collect(Collectors.toSet());
        }

        private static Class<?> getClass(String className, String packageName) {
            try {
                return Class.forName(packageName + "."
                        + className.substring(0, className.lastIndexOf('.')));
            } catch (ClassNotFoundException e) {
                // handle the exception
            }
            return null;
        }

    public static void main(String[] args) throws NoSuchMethodException {
        getBeanCache();
    }
}
