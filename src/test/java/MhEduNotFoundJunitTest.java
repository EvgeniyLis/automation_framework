import com.utils.SpreadsheetData;
import org.junit.*;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.utils.TestUtils.initchromeDriverWithProxy;

@RunWith(value = Parameterized.class)
public class MhEduNotFoundJunitTest {

    static WebDriver driver;
    private StringBuffer verificationErrors = new StringBuffer();
    private  String isbn;
    String info;
    List<String> bookInfo = new ArrayList<>();
    static List<String> failedIsbn = new ArrayList<>(); // list with books info

    @Parameterized.Parameters
    public static Collection spreadsheetData() throws IOException {
        InputStream spreadsheet = new FileInputStream("src/testdata/IsbnForNotFound.xlsx");
        return new SpreadsheetData(spreadsheet).getData();
    }

    public MhEduNotFoundJunitTest(String isbn){
        this.isbn = isbn;
    }

    @Rule
    public TestWatcher watcher = new TestWatcher()  {

        @Override
        protected void failed(Throwable e, Description description) {
          failedIsbn.add(isbn);
        }
    };

    @BeforeClass
    public static void setUp(){
        driver = initchromeDriverWithProxy();
        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
    }

    @Test
    public void notFoundTestVitalSourse() throws Exception{
        driver.get("https://www.mheducation.com/highered/home-guest.html");
        driver.findElement(By.id("higherEdQuery")).sendKeys(isbn, Keys.ENTER);
        //driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
        List<WebElement> noMessage = driver.findElements(By.className("none-msg"));
        List<WebElement> searchResultsBlock = driver.findElements(By.cssSelector("p[class*=bcard-isbn]"));

        if (searchResultsBlock.size() > 0){
            for (WebElement el:searchResultsBlock
                 ) {
                bookInfo.add(el.getText());
            }
            for (String el:bookInfo) {
                if (el.contains(isbn)){
                    Assert.assertTrue(false);
                } else {
                    Assert.assertTrue(true);
                }
            }
        }
        else if (noMessage.size()>0){
            Assert.assertTrue(true);
        }
    }

    @AfterClass
    public static void taerDown(){
        try {
            Files.write(Paths.get("src/testdata/isbn.txt"), failedIsbn, StandardOpenOption.CREATE);
        } catch (IOException e1){
            e1.printStackTrace();
        }
        if (driver!=null){
            driver.quit();
        }
    }

}