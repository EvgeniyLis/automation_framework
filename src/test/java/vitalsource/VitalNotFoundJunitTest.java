package vitalsource;

import com.utils.SpreadsheetData;
import org.apache.commons.io.FileUtils;
import org.junit.*;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import static com.utils.TestUtils.initchromeDriverWithProxy;

@RunWith(value = Parameterized.class)
public class VitalNotFoundJunitTest {

    static WebDriver driver;
    private StringBuffer verificationErrors = new StringBuffer();
    private  String isbn;
    String info;
    List<String> bookInfo = new ArrayList<>(); // list with books info

    @Parameterized.Parameters
    public static Collection spreadsheetData() throws IOException {
        InputStream spreadsheet = new FileInputStream("src/testdata/IsbnForNotFound.xlsx");
        return new SpreadsheetData(spreadsheet).getData();
    }

    public  VitalNotFoundJunitTest(String isbn){
        this.isbn = isbn;
    }

    @Rule
    public TestWatcher watcher = new TestWatcher() {

        @Override
        protected void failed(Throwable e, Description description) {
            try {
                takeScreenShot();
            }catch (IOException e1){
                e1.printStackTrace();
            }
        }
    };

    public void takeScreenShot() throws IOException {
        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String fileName = (isbn + "_" + UUID.randomUUID()).toString();
        File targetFile = new File("target//failed_tests//" + fileName + ".png");
        FileUtils.copyFile(scrFile, targetFile);
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

        List<WebElement> myElements = driver.findElements(By.className("unit-small")); // finds WebElements with results of search

        List<WebElement> searchResultsBlock = driver.findElements(By.className("product-search-result__link")); // finds WebElements with results of search
        String searchResults[] = new String[searchResultsBlock.size()]; // array of links with books after search
        for (int i = 0; i < searchResultsBlock.size(); i++){
            searchResults[i] = searchResultsBlock.get(i).getAttribute("href");
        }
        if (myElements.size() > 0){
            Assert.assertTrue(driver.findElement(By.className("unit-small")).getText().contains("unable to locate"));
        } else if (searchResultsBlock.size() > 0){
            for (int i = 0; i < searchResults.length; i++){
                driver.get(searchResults[i]);
                info = driver.findElement(By.className("product-overview__title-block")).getText();
                bookInfo.add(info);
                takeScreenShot();
                driver.navigate().back();
            }

            // check if the book contains a certain isbn
            for (int i = 0; i < bookInfo.size(); i++){
                if (bookInfo.get(i).contains(isbn)){
                    Assert.assertTrue(false);
                } else {
                    Assert.assertTrue(true);
                }
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