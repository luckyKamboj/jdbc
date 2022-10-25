package com.company.database.convertor;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.*;

public class Convertor {

    private Convertor(){}

    public static <T> T getClass(ResultSet resultSet, Class<T> clazz) throws SQLException, InvocationTargetException, IllegalAccessException, ClassNotFoundException {
        T dynamicClass = null;
        try {
           dynamicClass = clazz.getConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        Map<String, Method> methodMap = getMethodName(clazz);
        ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
        for (int i = 1; i <= resultSetMetaData.getColumnCount(); i++) {
            String columnName = resultSetMetaData.getColumnLabel(i);
            String generatedMethod =  generateMethodFromColumn(columnName);
            if(methodMap.containsKey(generatedMethod)){
                Method method = methodMap.get(generatedMethod);
                method.setAccessible(true);
                Parameter parameter = method.getParameters()[0];
                method.invoke(dynamicClass,Class.forName(parameter.getType().getName()).cast(resultSet.getObject(columnName)));
            }
        }
        return dynamicClass;
    }

    private static String generateMethodFromColumn(String columnName) {
        String[] strSplit = columnName.split("_");
        StringBuilder sb = new StringBuilder("set")
                .append(generateMethodByPart(strSplit[0]));
        for (int i = 1; i < strSplit.length; i++) {
            sb.append(generateMethodByPart(strSplit[i]));
        }
        return sb.toString();
    }

    private static String generateMethodByPart(String indexedString) {
            return indexedString.substring(0,1).toUpperCase()
                    + indexedString.substring(1).toLowerCase();
    }

    private static <T> Map<String, Method> getMethodName(Class<T> clazz) {
        Map<String, Method> methodMap = new HashMap<>();
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method: methods) {
            if(method.getName().startsWith("set"))
                methodMap.put(method.getName(), method);
        }
        return methodMap;
    }
}
