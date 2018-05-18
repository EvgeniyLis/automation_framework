
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.security.Key;
import java.util.List;

import static com.utils.TestUtils.initchromeDriverWithProxy;

public class AmazonIsbnAsinTest extends BaseVitalSourseTest {

    WebDriver driver;
    int count = 0;


    @BeforeClass
    public void setUp(){
        driver = initchromeDriverWithProxy();
        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
    }

    @Test(dataProvider = "get_Isbn_For_Asin_Isbn_Pair_Test")
    public void noDataTestVitalSourse(String isbn){
        driver.get("https://www.amazon.com");
        driver.findElement(By.name("field-keywords")).sendKeys(isbn, Keys.ENTER);

        driver.findElement(By.className("s-item-container"));
        List<WebElement> searchResults = driver.findElements(By.className("a-fixed-left-grid-inner"));
        //List<WebElement> noResults = driver.findElements(By.id("noResultsTitle"));

        if (searchResults.size() > 1){
            count++;
            System.out.println(count);
            Assert.assertTrue(false);
        }
        else if (searchResults.size() == 1){
            Assert.assertTrue(true);
        }
    }

    @DataProvider
    public Object[][] get_Isbn_For_Asin_Isbn_Pair_Test() throws Exception{
        Object[][] data = excelToDataProvider.testData("src/testdata/ISBN13-ASIN.xlsx", "TestData");
        return data;
    }

    @AfterClass
    public void taerDown(){
        if (driver!=null){
            driver.quit();
        }
    }

}
