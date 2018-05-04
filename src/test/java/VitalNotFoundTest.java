
import org.openqa.selenium.*;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.utils.ConfigProperties;

import java.util.List;

import static com.utils.TestUtils.initchromeDriverWithProxy;

public class VitalNotFoundTest extends BaseVitalSourseTest {

    //static WebDriver driver = new ChromeDriver();
    //TestUtils testUtils = new TestUtils(); // создание экземпляра класса TestUtils для вызова метода testDataForNotFoundFromMySql() - считывания данных из MySql

    WebDriver driver;

    @BeforeClass
    public void setUp(){
        driver = initchromeDriverWithProxy();
        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
    }

    @Test(dataProvider = "getTestDataforNotFound")
    public void notFoundTestVitalSourse(String isbn){
        driver.get("https://www.vitalsource.com/textbooks");
        //driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
        driver.findElement(By.id("term")).sendKeys(isbn, Keys.ENTER);

        List<WebElement> myElements = driver.findElements(By.className("unit-small")); // создаёт список Веб Элементов

        if (myElements.size() > 0){
            Assert.assertTrue(driver.findElement(By.className("unit-small")).getText().contains("unable to locate"));
        } else {
            Assert.assertFalse(driver.findElement(By.className("product-overview__title-block")).getText().contains(isbn));
        }
    }

    @DataProvider
    public Object[][] getTestDataforNotFound() throws Exception{
        Object[][] data = excelToDataProvider.testData("src/testdata/IsbnForNotFound.xlsx", "TestData");
        return data;
    }

    @AfterClass
    public void taerDown(){
        if (driver!=null){
            driver.quit();
        }
    }

}
