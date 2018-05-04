
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.utils.ConfigProperties;

import static com.utils.TestUtils.initchromeDriverWithProxy;

public class VitalNoDataTest extends BaseVitalSourseTest {

    //static WebDriver driver = new ChromeDriver();
    //TestUtils testUtils = new TestUtils(); // создание экземпляра класса TestUtils для вызова метода testDataForNotFoundFromMySql() - считывания данных из MySql

    WebDriver driver;

    @BeforeClass
    public void setUp(){
        driver = initchromeDriverWithProxy();
        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
    }

    // в данном тестовом методе проверяется отсутствие цены для определённого rental term. В качестве параметра из Excel файла передаётся урля, и рентал тёрм
    // драйвер идёт по урле и проверяет, что на странице в блоке цен отсутствует рента, переданная в качестве параметра из Excel файла
    @Test(dataProvider = "get_Test_Data_for_NoData_Test")
    public void noDataTestVitalSourse(String rental_term, String url){
        driver.get(url);
        //driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        Assert.assertFalse(driver.findElement(By.id("price-section")).getText().contains(rental_term));
    }

    @DataProvider
    public Object[][] get_Test_Data_for_NoData_Test() throws Exception{
        Object[][] data = excelToDataProvider.testData("src/testdata/NoDataIsbns.xlsx", "TestData");
        return data;
    }

    @AfterClass
    public void taerDown(){
        if (driver!=null){
            driver.quit();
        }
    }

}
