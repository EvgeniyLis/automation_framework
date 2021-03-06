import com.utils.ExcelToDataProvider;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

import static com.utils.TestUtils.initchromeDriverWithProxy;



public class MhEducationNotFoundTest {

    static WebDriver driver;
    String isbnToList;
    ExcelToDataProvider excelToDataProvider = new ExcelToDataProvider();
    ArrayList<String> failedIsbn = new ArrayList<>();

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
        isbnToList = isbn;
        driver.get("https://www.mheducation.com/");
        driver.findElement(By.id("higherEdQuery")).sendKeys(isbn, Keys.ENTER);
        List<WebElement> noMessage = driver.findElements(By.className("none-msg"));
        List<WebElement> blockWithIsbn = driver.findElements(By.className("bottom-button"));

        if (blockWithIsbn.size() > 0){
            Assert.assertFalse(driver.findElement(By.className("bottom-button")).getText().contains(isbn));
        }
        else if (noMessage.size()>0){
            Assert.assertTrue(driver.findElement(By.className("none-msg")).getText().contains("0 Results"));
        }
    }

    @AfterMethod
    public void getResult(ITestResult result){
        if (result.getStatus() == ITestResult.FAILURE){
            failedIsbn.add(isbnToList);
        }
    }

    @AfterClass
    public void taerDown(){
        if (driver!=null){
            driver.quit();
        }
        try {
            Files.write(Paths.get("src/testdata/isbn.txt"), failedIsbn, StandardOpenOption.CREATE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
