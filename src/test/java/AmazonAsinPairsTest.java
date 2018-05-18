
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;

import static com.utils.TestUtils.initchromeDriverWithProxy;

public class AmazonAsinPairsTest extends BaseVitalSourseTest {

    WebDriver driver;
    int response200 = 0;
    int response400 = 0;

    @BeforeClass
    public void setUp(){
        driver = initchromeDriverWithProxy();
        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
    }

    @Test(dataProvider = "get_Asin_For_Asin_Isbn_Pair_Test")
    public void noDataTestVitalSourse(String asin){
        driver.get("https://www.amazon.com/dp/"+asin);

        List<WebElement> content = driver.findElements(By.id("dp"));
        List<WebElement> dog = driver.findElements(By.id("g"));

        Assert.assertTrue(driver.findElement(By.id("detail-bullets")).getText().contains(asin));

        if (content.size() > 0){
            response200++;
        }
        else if (dog.size() > 0) {
            response400++;
        }

        System.out.println("200 : "+response200);
        System.out.println("400 : "+response400);
    }

    @DataProvider
    public Object[][] get_Asin_For_Asin_Isbn_Pair_Test() throws Exception{
        Object[][] data = excelToDataProvider.testData("src/testdata/ISBN13-ASIN.xlsx", "TestData");
        return data;
    }

   /* @AfterClass
    public void taerDown(){
        if (driver!=null){
            driver.quit();
        }
    }*/

}
