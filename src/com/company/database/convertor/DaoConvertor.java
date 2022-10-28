package com.company.database.convertor;

import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface DaoConvertor {

    <E> E getClass(ResultSet resultSet, Class<E> tClass) throws SQLException, InvocationTargetException, IllegalAccessException, ClassNotFoundException;

    String generateMethodFromColumn(String columnName);
}
