
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import vitalsource.BaseVitalSourseTest;

import static com.utils.TestUtils.initchromeDriverWithProxy;

public class AmazonAsinPairsTest extends BaseVitalSourseTest {

    WebDriver driver;

    @BeforeClass
    public void setUp(){
        driver = initchromeDriverWithProxy();
        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
    }

    @Test(dataProvider = "get_Asin_For_Asin_Isbn_Pair_Test")
    public void noDataTestVitalSourse(String url){
        driver.get(url);
        Assert.assertFalse(driver.findElement(By.xpath("//*[@id='mediaTab_content_landing']/div")).getText().contains("Buy new"));
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
