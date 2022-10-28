package com.company.database.convertor;

import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OracleDaoConvertor implements DaoConvertor {
    @Override
    public <E> E getClass(ResultSet resultSet, Class<E> tClass) throws SQLException, InvocationTargetException, IllegalAccessException, ClassNotFoundException {
        return null;
    }

    @Override
    public String generateMethodFromColumn(String columnName) {
        return null;
    }
}
