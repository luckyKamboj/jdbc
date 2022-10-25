package com.company.database.dao;

import com.company.database.config.DBUtil;
import com.company.database.util.LoggerUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;

public class GenericDao {

    private static final Logger LOGGER = LoggerUtil.getLogger(GenericDao.class.getName());

    protected static final int BATCH_SIZE = 99;
    protected static final String SELECT_ALL_FROM = "SELECT * FROM ";
    protected static final String DELETE_FROM = "DELETE FROM ";
    protected static final String WHERE_ID = " WHERE ID = ";
    protected static final String WHERE_ID_IN = " WHERE ID in (";
    protected static final String CLOSING_BRACKET = ")";

    protected static Statement statement = null;
    protected GenericDao(){}
    static {
        Connection connection = DBUtil.getConnectionWithProperties();
        try {
            assert connection != null;
            statement = connection.createStatement();
        } catch (SQLException e) {
            LOGGER.severe("Sql state : " + e.getSQLState());
            LOGGER.severe("Error Message : " + e.getMessage());
            LOGGER.severe("Error code : " + e.getErrorCode());
            LOGGER.severe("Cause of error : " + e.getCause());
        }
    }

}
