import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import vitalsource.BaseVitalSourseTest;

import java.util.concurrent.TimeUnit;

import static com.utils.TestUtils.initchromeDriverWithProxy;

public class MhprofessionalPriceMatchTest extends BaseVitalSourseTest {

    WebDriver driver;

    @BeforeClass
    public void setUp(){
        driver = initchromeDriverWithProxy();
        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
    }

    @Test(dataProvider = "getTestDataforPriceMatch")
    public void MhEduPriceMatchTest(String isbn, String price_net, String url){
        driver.get(url);
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);

        WebElement blockWithIsbn = driver.findElement(By.className("top-attr"));

        if (isbnMatch(blockWithIsbn, isbn)) {
            Assert.assertTrue(driver.findElement(By.className("price")).getText().contains(convertedPriceNet(price_net)));
        }else {
            driver.findElement(By.xpath("//*[@id='cti-config-variants-145']/div/ul/li[2]/div/label")).click();
            driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
            Assert.assertTrue(driver.findElement(By.className("price")).getText().contains(convertedPriceNet(price_net)));
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
