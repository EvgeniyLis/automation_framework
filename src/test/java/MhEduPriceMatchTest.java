import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import vitalsource.BaseVitalSourseTest;

import java.util.List;

import static com.utils.TestUtils.initchromeDriverWithProxy;

public class MhEduPriceMatchTest extends BaseVitalSourseTest {

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

        List<WebElement> blockWithIsbn = driver.findElements(By.className("bottom-button"));

        Assert.assertTrue(getFindingBlock(blockWithIsbn, isbn).getText().contains(price_net));
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
