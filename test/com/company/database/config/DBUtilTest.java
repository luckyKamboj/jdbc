package com.company.database.config;

import com.company.database.annotations.UnitTest;
import com.company.database.annotations.UnitTester;
import org.assertj.core.api.Assertions;

import java.sql.Connection;

@UnitTester(beanName = "DBUtil")
public class DBUtilTest {

    public DBUtilTest(){

    }
    @UnitTest
    public void testGetConnectionWithStringArgConstructor() {
       Connection connection = DBUtil.getConnectionWithStringArgConstructor();
        Assertions.assertThat(connection).as("Connection should not be null").isNotNull();
    }

    @UnitTest
    public void testGetConnectionWithURLOnly() {
        Connection connection = DBUtil.getConnectionWithURLOnly();
        Assertions.assertThat(connection).as("Connection should not be null").isNotNull();
    }

    @UnitTest
    public void testGetConnectionWithPropertiesFile() {
        Connection connection = DBUtil.getConnectionWithPropertiesFile();
        Assertions.assertThat(connection).as("Connection should not be null").isNotNull();
    }

    @UnitTest
    public void testGetConnectionWithProperties() {
        Connection connection = DBUtil.getConnectionWithProperties();
        Assertions.assertThat(connection).as("Connection should not be null").isNotNull();
    }

}
