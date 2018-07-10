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
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static com.utils.TestUtils.initchromeDriverWithProxy;

@RunWith(value = Parameterized.class)
public class CengageNewNotFoundJunitTest {

    static WebDriver driver;
    private  String isbn;
    String info;
    List<String> bookInfo = new ArrayList<>();
    static List<String> failedIsbn = new ArrayList<>(); // list with books info

    @Parameterized.Parameters
    public static Collection spreadsheetData() throws IOException {
        InputStream spreadsheet = new FileInputStream("src/testdata/IsbnForNotFound.xlsx");
        return new SpreadsheetData(spreadsheet).getData();
    }

    public CengageNewNotFoundJunitTest(String isbn){
        this.isbn = isbn;
    }

    @Rule
    public TestWatcher watcher = new TestWatcher() {

        @Override
        protected void failed(Throwable e, Description description) {
            failedIsbn.add(isbn);
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
    public void notFoundTestVitalSourse() throws Exception {
        driver.get("https://www.cengage.com/student/");

        driver.findElement(By.id("searchForm-terms-UNIQUEID")).sendKeys(isbn, Keys.ENTER);
        driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);

        List<WebElement> noResylts = driver.findElements(By.className("ceng-banner_inner"));
        List<WebElement> searchResultsBlock = driver.findElements(By.cssSelector("a[class*='ceng-searchResults_searchListingsItemTitle']"));
        List<WebElement> findingBook = driver.findElements(By.className("ceng-studentProductPricing_entryBuyingOption"));
        //---
        List<WebElement> titleTab = driver.findElements(By.className("ceng-studentProductHeader_title"));
        List<WebElement> pricingTab  =driver.findElements(By.cssSelector("div[class*='ceng-studentProductPricing_tabs']"));
        //--

        String searchResults[] = new String[searchResultsBlock.size()];
        for (int i = 0; i < searchResultsBlock.size(); i++){
            searchResults[i] = searchResultsBlock.get(i).getAttribute("href");
        }
        if (noResylts.size() > 0) {
            Assert.assertTrue(true); // проверяет наличие блока No results
        }
        else if (searchResultsBlock.size() > 0){ // если результатов поиска несколько - идёт по каждому и проверяет на наличие искомого исбна на каждой вкладке
            for (int i = 0; i < searchResults.length; i++){
                driver.get(searchResults[i]);
                info = driver.findElement(By.className("ceng-studentProductPricing_entryBuyingOption")).getText();
                bookInfo.add(info);
                driver.navigate().back();
            }

            for (int i = 0; i < bookInfo.size(); i++){
                if (bookInfo.get(i).contains(isbn)){
                    Assert.assertTrue(false);
                } else {
                    Assert.assertTrue(true);
                }
            }
        }
        else if (findingBook.size() > 0){
            Assert.assertFalse(driver.findElement(By.className("ceng-studentProductPricing_entryBuyingOption")).getText().contains(isbn)); // если результат поиска - страница книги с одной вкладкой - проверяет на наличие искомого исбна
        }
        else if (titleTab.size()==1 && pricingTab.size()==0){
                takeScreenShot();
                Assert.assertTrue(true); // делает скриншот, если результат поиска - только тайтл или белая страница
        }
    }

    @AfterClass
    public static void taerDown(){
        if (driver!=null){
            driver.quit();
        }
        try {
            Files.write(Paths.get("src/testdata/isbn.txt"), failedIsbn, StandardOpenOption.CREATE);
        } catch (IOException e1){
            e1.printStackTrace();
        }
    }

}