package vitalsource;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.utils.TestUtils.initchromeDriverWithProxy;

public class VitalPriceMatchTest extends vitalsource.BaseVitalSourseTest {

    WebDriver driver;

    @BeforeClass
    public void setUp(){
        driver = initchromeDriverWithProxy();
        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
    }

    @Test(dataProvider = "getTestDataforPriceMatch")
    public void priceMatchTestVitalSourse(String rental_term, String retail_price, String url){
        driver.get(url);
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);

        List<WebElement> priceBlocksElements = driver.findElements(By.cssSelector("div[class*='vs-box__selection']"));

        if (priceBlocksElements.size() == 1){
            Assert.assertTrue(driver.findElement(By.cssSelector("div[class*='vs-box__selection']")).getText().contains(retail_price));
        } else {
            Assert.assertTrue(getFindingBlock(priceBlocksElements, rental_term).getText().contains(retail_price));
        }
    }

    @DataProvider
    public Object[][] getTestDataforPriceMatch() throws Exception{
        Object[][] data = excelToDataProvider.testData("src/testdata/PriceMatchIsbns.xlsx", "TestData");
        return data;
    }

    @AfterClass
    public void taerDown(){
        if (driver!=null){
            driver.quit();
        }
    }
}
