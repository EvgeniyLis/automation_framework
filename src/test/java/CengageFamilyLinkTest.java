import com.utils.ExcelToDataProvider;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class CengageFamilyLinkTest {

    static WebDriver driver;
    ExcelToDataProvider excelToDataProvider = new ExcelToDataProvider();
    ArrayList<String> failedLinks = new ArrayList<>();
    String failedFamilyLink;


    @BeforeClass
    public void setUp(){
        driver = new ChromeDriver() /*initchromeDriverWithProxy()*/;
        driver.manage().window().maximize();
    }

    @DataProvider
    public Object[][] getTestDataforNotFound() throws Exception{
        Object[][] data = excelToDataProvider.testData("src/testdata/IsbnForNotFound.xlsx", "TestData");
        return data;
    }

    @Test(dataProvider = "getTestDataforNotFound")
    public void cengageFamilyLinkCheckTest(String url){
        driver.get(url);
        failedFamilyLink = url;
        List<WebElement> noResult = driver.findElements(By.className("ceng-banner_inner"));
        List<WebElement> yesResult = driver.findElements(By.cssSelector("div[class*='ceng-studentProduct_section']"));
        if (noResult.size() > 0){
            Assert.assertTrue(true);
        }else if (yesResult.size() > 0){
            Assert.assertTrue(false);
        }
    }

    @AfterMethod
    public void getResult(ITestResult result){
        if (result.getStatus() == ITestResult.FAILURE){
            failedLinks.add(failedFamilyLink);
        }
    }

    @AfterClass
    public void taerDown(){
        /*if (driver!=null){
            driver.quit();
        }*/
        try {
            Files.write(Paths.get("src/testdata/isbn.txt"), failedLinks, StandardOpenOption.CREATE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
