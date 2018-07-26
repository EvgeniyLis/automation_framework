import com.utils.ConfigProperties;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.utils.TestUtils.initchromeDriverWithProxy;

public class CengageFamilyLinkCollectorTest {

    static WebDriver driver;
    static String title;
    static String isbn;
    List<String> familyLinkList = new LinkedList<>();
    List<String> isbnList = new LinkedList<>();

    List<String> currentFamilyLinkList = new LinkedList<>();
    List<String> currentIsbnList = new LinkedList<>();

    List<String> previousFamilyLinkList = new LinkedList<>();
    List<String> previousIsbnLinkList = new LinkedList<>();

    List<String> familyLinkListTemp = new LinkedList<>();
    List<String> isbnListTemp = new LinkedList<>();

    static Connection con;
    static Statement stmt;

    @BeforeClass
    public static void setUp(){
        driver = initchromeDriverWithProxy();
        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
    }

    @Test
    public void cengageCollectorTest() throws InterruptedException {
        driver.get("https://www.cengage.com");
        driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
        driver.findElement(By.xpath("//*[@id='ceng-header']/div[1]/div[2]/div/nav/ul/li[1]/a")).click();
        driver.get("https://www.cengage.com/s");

        for (int j=1; j<=258; j++){

            List<WebElement> titleList = driver.findElements(By.cssSelector("a[class*='ceng-searchResults_searchListingsItemTitle']"));

            for (int i = 0; i < titleList.size(); i++) {
                title = titleList.get(i).getAttribute("href");
                familyLinkList.add(title);
                //isbn = titleList.get(i).findElement(By.tagName("img")).getAttribute("src");
                //isbnList.add(converted(isbn));
            }

            System.out.println(j);

            familyLinkListTemp.addAll(familyLinkList);
            //isbnListTemp.addAll(isbnList);

            familyLinkList.removeAll(previousFamilyLinkList);
            //isbnList.removeAll(previousIsbnLinkList);

            currentFamilyLinkList.addAll(familyLinkList);
            //currentIsbnList.addAll(isbnList);

            previousFamilyLinkList.addAll(familyLinkListTemp);
            //previousIsbnLinkList.addAll(isbnListTemp);

            try {
                con = DriverManager.getConnection(ConfigProperties.getTestProperty("mysqlLocalUrl"), ConfigProperties.getTestProperty("mysqlLocalLogin"), ConfigProperties.getTestProperty("mysqlLocalPassword"));
                stmt=con.createStatement();
                for (String title:currentFamilyLinkList){
                    //for (String currentIsbn:currentIsbnList){
                        stmt.addBatch("insert into user_test.cengage_new (`title`,`isbn`, `page`) values ('"+title+"','null', '"+j+"');");
                        //break;
                    //}
                }
                stmt.executeBatch();
            } catch (SQLException e){
                e.printStackTrace();
            }

            familyLinkList.clear();
            //isbnList.clear();

            familyLinkListTemp.clear();
            //isbnListTemp.clear();

            currentFamilyLinkList.clear();
            //currentIsbnList.clear();

            driver.findElement(By.className("ceng-searchResults_loadMore")).click();
            Thread.sleep(3000);
        }

    }

    //--- methods

    public static String converted(String isbn) {
        String isbnNotConv;
        StringBuilder sb = new StringBuilder(isbn);
        isbnNotConv = sb.reverse().substring(2,15).toString();
        StringBuilder sb1 = new StringBuilder(isbnNotConv);
        return sb1.reverse().toString();


    }

    //---
    public static void outList(List<String> list){
        for (String el:list) { System.out.println(el); }
    }

    @AfterClass
    public static void taerDown(){
        /*if (driver!=null){
            driver.quit();
        }*/

    }

}