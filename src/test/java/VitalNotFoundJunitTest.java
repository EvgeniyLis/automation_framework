import com.utils.SpreadsheetData;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.By;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized.Parameters;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.List;

import static com.utils.TestUtils.initchromeDriverWithProxy;

@RunWith(value = Parameterized.class)
public class VitalNotFoundJunitTest {

    static WebDriver driver;
    private StringBuffer verificationErrors = new StringBuffer();
    private  String isbn;

    @Parameterized.Parameters
    public static Collection spreadsheetData() throws IOException {
        InputStream spreadsheet = new FileInputStream("src/testdata/IsbnForNotFound.xlsx");
        return new SpreadsheetData(spreadsheet).getData();
    }

    public  VitalNotFoundJunitTest(String isbn){
        this.isbn = isbn;
    }


    @BeforeClass
    public static void setUp(){
        driver = initchromeDriverWithProxy();
        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
    }

    @Test
    public void notFoundTestVitalSourse() throws Exception{
        driver.get("https://www.vitalsource.com/textbooks");

        driver.findElement(By.id("term")).sendKeys(isbn);
        driver.findElement(By.xpath("//*[@id=\"product_search\"]/button")).click();

        List<WebElement> myElements = driver.findElements(By.className("unit-small")); // создаёт список Веб Элементов
        List<WebElement> searchResultsBlock = driver.findElements(By.className("product-search-result__link")); // finds elements with results of search

        if (myElements.size() > 0){
            Assert.assertTrue(driver.findElement(By.className("unit-small")).getText().contains("unable to locate"));
        } else if (searchResultsBlock.size() > 0){
            for (int i=0; i<searchResultsBlock.size(); i++){
                searchResultsBlock.get(i).click();
                Assert.assertFalse(driver.findElement(By.className("product-overview__title-block")).getText().contains(isbn));
            }
        } else {
            Assert.assertFalse(driver.findElement(By.className("product-overview__title-block")).getText().contains(isbn));
        }
    }

    @AfterClass
    public static void taerDown(){
        if (driver!=null){
            driver.quit();
        }
    }

}