package com.company.database.convertor;

import com.company.database.annotation.BeanName;

import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;

@BeanName
public class SqlDaoConvertor  implements DaoConvertor{
    @Override
    public <E> E getClass(ResultSet resultSet, Class<E> tClass) throws SQLException, InvocationTargetException, IllegalAccessException, ClassNotFoundException {
        return null;
    }

    @Override
    public String generateMethodFromColumn(String columnName) {
        return null;
    }
}
