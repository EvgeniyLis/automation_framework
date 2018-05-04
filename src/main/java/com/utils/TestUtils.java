package com.utils;

import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TestUtils {

    public static WebDriver initchromeDriverWithProxy(){
        System.setProperty("webdriver.chrome.driver", ConfigProperties.getTestProperty("chromedriver"));
        Proxy proxy = new Proxy();
        proxy.setHttpProxy(ConfigProperties.getTestProperty("proxy2"));
        proxy.setFtpProxy(ConfigProperties.getTestProperty("proxy2"));
        proxy.setSslProxy(ConfigProperties.getTestProperty("proxy2"));
        DesiredCapabilities cp = new DesiredCapabilities();
        cp.setCapability(CapabilityType.PROXY, proxy);
        WebDriver webDriver = new ChromeDriver(cp);
        webDriver.manage().window().maximize();
        return webDriver;
    }
    //--------------------------------------------------------------------------------------------------------------

    // метод забора тестовых данных (в данном случае исбнов) из базы данных MySQL и дальнейшее их использование в тесте в аннотации DataProvider
    MysqlConnect mysqlConnect = new MysqlConnect();
    String query = "select ISBN13 from mheducation_test";

    public ArrayList<Object[]> testDataForNotFoundFromMySql(){

        ArrayList<Object[]> myData = new ArrayList<Object[]>();

        ResultSet rs = null;
        try {
            PreparedStatement statement = mysqlConnect.connect().prepareStatement(query);
            rs = statement.executeQuery(query);
            while (rs.next()) { // executing of query
                String isbn = rs.getString("ISBN13");
                Object ob[] = {isbn};
                myData.add(ob);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            mysqlConnect.disconnect();
        }

        return myData;
    }
    //--------------------------------------------------------------------------------------------------------------------

    String queryForNoData = "select * from user_test.vitalsourse";

    // метод забора тестовых данных (в данном случае рентал тёрмов и урлей) из базы данных MySQL и дальнейшее их использование в тесте в аннотации DataProvider
    public ArrayList<Object[]> testDataForNoDataFromMySql(){

        ArrayList<Object[]> myData = new ArrayList<Object[]>();

        ResultSet rs = null;
        try {
            PreparedStatement statement = mysqlConnect.connect().prepareStatement(queryForNoData);
            rs = statement.executeQuery(queryForNoData);
            while (rs.next()) { // executing of query
                String rental_term = rs.getString("RENTAL_TERM");
                String url = rs.getString("URL");
                Object ob[] = {rental_term, url};
                myData.add(ob);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            mysqlConnect.disconnect();
        }

        return myData;
    }
    //-------------------------------------------------------------------------------------------------------------

}

