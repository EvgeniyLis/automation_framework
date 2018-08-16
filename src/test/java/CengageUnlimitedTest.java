import com.utils.ExcelToDataProvider;
import org.openqa.selenium.By;
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
import java.util.concurrent.TimeUnit;

import static com.utils.TestUtils.initchromeDriverWithProxy;

public class CengageUnlimitedTest {

    static WebDriver driver;
    ExcelToDataProvider excelToDataProvider = new ExcelToDataProvider();
    ArrayList<String> failedLinks = new ArrayList<>();
    String failedFamilyLink;


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
    public void cengageFamilyLinkCheckTest(String url) {
        driver.get(url);
        driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
        failedFamilyLink = url;

        List<WebElement> unlimitedTitle = driver.findElements(By.cssSelector("div[class*='ceng-studentProductPricing_description']"));
        List<WebElement> noContent = driver.findElements(By.xpath("/html/body/main/div/div[1]/student-product-pricing/div/div/div[2]/div/p")); // /html/body/main/div/div[1]/student-product-pricing/div/div/div[2]/div/p
        if (noContent.size() > 0) {
            Assert.assertTrue(true);
        } else if (unlimitedTitle.size()>0) {
            Assert.assertFalse(driver.findElement(By.cssSelector("div[class*='ceng-studentProductPricing_description']")).getText().contains("Subscribe and Save with Cengage Unlimited"));
        } else if (noContent.size() == 0 & unlimitedTitle.size() ==0){
            Assert.assertTrue(true);
        }
    }

    @AfterMethod
    public void getResult(ITestResult result){
        if (result.getStatus() == ITestResult.FAILURE){
            failedLinks.add(failedFamilyLink);
            for (String el:failedLinks) {
                System.out.println(el);
            }
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
