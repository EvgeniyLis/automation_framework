import com.utils.ExcelToDataProvider;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static com.utils.TestUtils.initchromeDriverWithProxy;

public class WileyPriceMatchTest {

    WebDriver driver;
    ExcelToDataProvider excelToDataProvider = new ExcelToDataProvider();

    @BeforeClass
    public void setUp(){
        driver = initchromeDriverWithProxy();
        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
    }

    @Test(dataProvider = "getTestDataforPriceMatch")
    public void WileyPriceMatchTest(String retail_price, String url){
        driver.get(url);
        Assert.assertTrue(driver.findElement(By.className("pr-price")).getText().contains(retail_price));
    }

    @DataProvider
    public Object[][] getTestDataforPriceMatch() throws Exception{
        Object[][] data = excelToDataProvider.testData("src/testdata/PriceMatchWiley.xlsx", "TestData");
        return data;
    }

    @AfterClass
    public void taerDown(){
        if (driver!=null){
            driver.quit();
        }
    }

}
