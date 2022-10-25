package com.company.database.dao;

import com.company.database.annotation.Table;
import com.company.database.convertor.Convertor;

import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class GenericDaoImpl<E> extends GenericDao implements BasicDao{

    public E getById(Class<E> tClass, Integer id) {
        ResultSet resultSet;
        try {
            String sqlQuery = SELECT_ALL_FROM + getTable(tClass) + WHERE_ID + id;
            resultSet = statement.executeQuery(sqlQuery);
            if(resultSet.next()){
                return Convertor.getClass(resultSet, tClass);
            }
        } catch (SQLException | InvocationTargetException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Collection<E> getAllByIdIn(Class<E> tClass, Collection<Integer> ids) {
        List<String> batchIds = getBatchIds(ids);
        ResultSet resultSet;
        List<E> records = new ArrayList<>();
        try {
            for (String batch: batchIds) {
                String sqlQuery = SELECT_ALL_FROM + getTable(tClass) + WHERE_ID_IN + batch + CLOSING_BRACKET;
                resultSet = statement.executeQuery(sqlQuery);
                records.addAll(populateResult(new ArrayList<>(), resultSet, tClass));
            }
        } catch (SQLException | InvocationTargetException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return records;
    }

    public Collection<E> getPaginatedRecords(Class<E> tClass, int pageNumber, int numberOfRecords) {
        ResultSet resultSet;
        List<E> records = new ArrayList<>(numberOfRecords);
        try {
            String sqlQuery = SELECT_ALL_FROM + getTable(tClass) + " LIMIT " + pageNumber + "," + numberOfRecords;
            resultSet = statement.executeQuery(sqlQuery);
           return populateResult(records, resultSet, tClass);
        } catch (SQLException | InvocationTargetException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return records;
    }

    public String getTable(Class<E> clazz) {
        if(clazz.isAnnotationPresent(Table.class)){
            Table table = clazz.getAnnotation(Table.class);
            return table.name();
        }else{
            return clazz.getSimpleName();
        }
    }

    public boolean deleteById(Class<E> clazz, Integer id) {
        String sqlQuery = DELETE_FROM + getTable(clazz) + WHERE_ID + id;
        try {
            int deleted = statement.executeUpdate(sqlQuery);
            return deleted ==1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteAll(Class<E> clazz) {
        String sqlQuery = DELETE_FROM + getTable(clazz);
        try {
            int count = statement.executeUpdate(sqlQuery);
            return count == 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteAllByIdIn(Class<E> tClass, Collection<Integer> ids) {
        List<String> batchSize = getBatchIds(ids);
        for (String batchIds: batchSize) {
            String sqlQuery = DELETE_FROM + getTable(tClass) + WHERE_ID_IN + batchIds + CLOSING_BRACKET ;
            try {
              int deleted = statement.executeUpdate(sqlQuery);
              return deleted == 1;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    private List<String> getBatchIds(Collection<Integer> ids) {
        List<String> batchList = new ArrayList<>();
        StringBuilder idsList = new StringBuilder("");
        int count = 0;
        for (Integer id: ids) {
            if(count == BATCH_SIZE){
                batchList.add(idsList.toString());
                idsList = new StringBuilder("");
                count = 0;
            }
            idsList.append(id).append(",");
            count++;
        }
        String idsString = idsList.toString();
        batchList.add(idsString.substring(0, idsString.length() - 1));
        return batchList;
    }

    private List<E> populateResult(List<E> records, ResultSet resultSet, Class<E> tClass) throws SQLException, ClassNotFoundException, InvocationTargetException, IllegalAccessException {
        while(resultSet.next()){
            records.add(Convertor.getClass(resultSet, tClass));
        }
        return records;
    }

}
