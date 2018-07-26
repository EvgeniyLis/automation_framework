import com.utils.ExcelToDataProvider;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static com.utils.TestUtils.initchromeDriverWithProxy;

public class MhprofessionalNotFoundTest {

    static WebDriver driver;
    ExcelToDataProvider excelToDataProvider = new ExcelToDataProvider();

    @BeforeClass
    public void setUp(){
        driver = initchromeDriverWithProxy();
        driver.manage().window().maximize();
    }

    @DataProvider
    public Object[][] getTestDataforNotFound() throws Exception{
        Object[][] data = excelToDataProvider.testData("src/testdata/IsbnForNotFound.xlsx", "TestData");
        return data;
    }

    @Test(dataProvider = "getTestDataforNotFound")
    public void notFoundTestMacmillan(String isbn){
        driver.get("https://www.mhprofessional.com/");
        driver.findElement(By.name("q")).sendKeys(isbn, Keys.ENTER);
        Assert.assertTrue(driver.findElement(By.className("col-main")).getText().contains("no products"));
    }

    /*@Test(dataProvider = "getTestDataforNotFound")
    public void notFoundMhprofTest(String url){
        driver.get(url);
        Assert.assertTrue(driver.findElement(By.className("col-main")).getText().contains("Whoops"));
    }*/

    @AfterClass
    public void taerDown(){
        if (driver!=null){
            driver.quit();
        }
    }

}
