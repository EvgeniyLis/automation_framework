package com.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class MysqlConnectStatic {

    // init connection object
    private static Connection connection;
    // init properties object
    private static Properties properties;

    // create properties
    private static Properties getProperties(){
        if (properties == null){
            properties = new Properties();
            properties.setProperty("user", ConfigProperties.getTestProperty("mysqlLocalLogin"));
            properties.setProperty("password", ConfigProperties.getTestProperty("mysqlLocalPassword"));
        }
        return properties;
    }

    // connect database
    public static Connection connect() {
        if (connection == null){
            try {
                Class.forName(ConfigProperties.getTestProperty("databaseDriver"));
                connection = DriverManager.getConnection(ConfigProperties.getTestProperty("mysqlLocalUrl"), getProperties());
            } catch (SQLException e){
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }

    // disconnect database
    public static void disconnect(){
        if (connection != null){
            try{
                connection.close();
                connection = null;
            } catch (SQLException e){
                e.printStackTrace();
            }
        }
    }
}
