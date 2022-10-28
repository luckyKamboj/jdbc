package com.company.database.dao;

import com.company.database.annotation.Table;
import com.company.database.convertor.ConversionFactory;
import com.company.database.convertor.MySqlDaoConvertor;

import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class GenericDaoImpl<E> extends GenericDao implements BasicDao{

    public E getById(Class<E> tClass, Integer id) {
        ResultSet resultSet;
        try(Statement state = getStatement()) {
            String sqlQuery = SELECT_ALL_FROM + getTable(tClass) + WHERE_ID + id;
            resultSet = state.executeQuery(sqlQuery);
            if(resultSet.next()){
                closeConnection();
                return Objects.requireNonNull(ConversionFactory.getConvertor("mySqlDaoConvertor")).getClass(resultSet, tClass);
            }

        } catch (SQLException | InvocationTargetException | IllegalAccessException | ClassNotFoundException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        closeConnection();
        return null;
    }

    public Collection<E> getAllByIdIn(Class<E> tClass, Collection<Integer> ids) {
        List<String> batchIds = getBatchIds(ids);
        ResultSet resultSet;
        List<E> records = new ArrayList<>();
        try(Statement state = getStatement()) {
            for (String batch: batchIds) {
                String sqlQuery = SELECT_ALL_FROM + getTable(tClass) + WHERE_ID_IN + batch + CLOSING_BRACKET;
                resultSet = state.executeQuery(sqlQuery);
                records.addAll(populateResult(new ArrayList<>(), resultSet, tClass));
            }
        } catch (SQLException | InvocationTargetException | IllegalAccessException | ClassNotFoundException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        closeConnection();
        return records;
    }

    public Collection<E> getPaginatedRecords(Class<E> tClass, int pageNumber, int numberOfRecords) {
        ResultSet resultSet;
        List<E> records = new ArrayList<>(numberOfRecords);
        try(Statement state = getStatement()) {
            String sqlQuery = SELECT_ALL_FROM + getTable(tClass) + " LIMIT " + pageNumber + "," + numberOfRecords;
            resultSet = state.executeQuery(sqlQuery);
           return populateResult(records, resultSet, tClass);
        } catch (SQLException | InvocationTargetException | IllegalAccessException | ClassNotFoundException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        closeConnection();
        return records;
    }

    protected String getTable(Class<E> clazz) {
        if(clazz.isAnnotationPresent(Table.class)){
            Table table = clazz.getAnnotation(Table.class);
            return table.name();
        }else{
            return clazz.getSimpleName();
        }
    }

    public boolean deleteById(Class<E> clazz, Integer id) {
        String sqlQuery = DELETE_FROM + getTable(clazz) + WHERE_ID + id;
        try(Statement state = getStatement()) {
            int deleted = state.executeUpdate(sqlQuery);
            closeConnection();
            return deleted == 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeConnection();
        return false;
    }

    public boolean deleteAll(Class<E> clazz) {
        String sqlQuery = DELETE_FROM + getTable(clazz);
        try(Statement state = getStatement()) {
            int count = state.executeUpdate(sqlQuery);
            closeConnection();
            return count == 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeConnection();
        return false;
    }

    public boolean deleteAllByIdIn(Class<E> tClass, Collection<Integer> ids) {
        List<String> batchSize = getBatchIds(ids);
        for (String batchIds : batchSize) {
            String sqlQuery = DELETE_FROM + getTable(tClass) + WHERE_ID_IN + batchIds + CLOSING_BRACKET;
            try (Statement state = getStatement()) {
                int deleted = state.executeUpdate(sqlQuery);
                return deleted == 1;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        closeConnection();
        return false;
    }

    private List<E> populateResult(List<E> records, ResultSet resultSet, Class<E> tClass)
            throws SQLException, ClassNotFoundException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {

        while(resultSet.next()){
            records.add(Objects.requireNonNull(ConversionFactory.getConvertor("mySqlDaoConvertor")).getClass(resultSet, tClass));
        }

        return records;
    }

}
