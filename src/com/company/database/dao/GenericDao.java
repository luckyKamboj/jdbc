package com.company.database.dao;

import com.company.database.config.DBUtil;
import com.company.database.util.LoggerUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

public class GenericDao {

    private static final Logger LOGGER = LoggerUtil.getLogger(GenericDao.class.getName());

    protected static final int BATCH_SIZE = 99;
    protected static final String SELECT_ALL_FROM = "SELECT * FROM ";
    protected static final String DELETE_FROM = "DELETE FROM ";
    protected static final String WHERE_ID = " WHERE ID = ";
    protected static final String WHERE_ID_IN = " WHERE ID in (";
    protected static final String CLOSING_BRACKET = ")";

    private static Statement statement = null;
    private static Connection connection = null;
    protected GenericDao(){}

    protected static Statement getStatement() {
        connection = DBUtil.getConnectionWithProperties();
        try {
            assert connection != null;
            statement = connection.createStatement();
        } catch (SQLException e) {
            LOGGER.severe("Sql state : " + e.getSQLState());
            LOGGER.severe("Error Message : " + e.getMessage());
            LOGGER.severe("Error code : " + e.getErrorCode());
            LOGGER.severe("Cause of error : " + e.getCause());
        }
        return statement;
    }

    protected List<String> getBatchIds(Collection<Integer> ids) {
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

    protected void closeConnection(){
        if(connection != null){
            try {
                connection.close();
            } catch (SQLException e) {
                LOGGER.info("SQL Exception :"+ e.getMessage());
            }
        }
    }
}
