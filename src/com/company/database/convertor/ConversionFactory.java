package com.company.database.convertor;

import com.company.database.annotation.BeanName;
import com.company.database.context.BeanContext;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

public class ConversionFactory {
    private ConversionFactory(){}
    public static DaoConvertor getConvertor (String convertorBeanName) throws NoSuchMethodException {
          Class<?>[] classes =  DaoConvertor.class.getClasses();
        for (Class<?> clazz: classes) {
            if(clazz.isAnnotationPresent(BeanName.class)){
                Map<String, Class<?>> clazzMap =  BeanContext.getBeanCache();
                Annotation anno = clazz.getDeclaredAnnotation(BeanName.class);
                Class<?> value  = clazzMap.get(anno.getClass().getName());
                if(value == null){
                   return generateObject(clazz, convertorBeanName);
                }else{
                    return generateObject(clazz);
                }
            }else{
                generateObject(clazz, convertorBeanName);
            }
        }
        return null;
    }

    private static DaoConvertor generateObject(Class<?> clazz) {
        try {
            return (DaoConvertor) clazz.getConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static DaoConvertor generateObject(Class<?> clazz, String convertorBeanName) {
        if(clazz.getName().equalsIgnoreCase(convertorBeanName)){
           return generateObject(clazz);
        }
        return null;
    }
}
