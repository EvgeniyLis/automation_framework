import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.utils.GetScreenshot.capture;
import static com.utils.TestUtils.initchromeDriverWithProxy;

public class VitalNotFoundTest extends BaseVitalSourseTest {

    //TestUtils testUtils = new TestUtils(); // создание экземпляра класса TestUtils для вызова метода testDataForNotFoundFromMySql() - считывания данных из MySql

    WebDriver driver;
    ExtentReports extent;
    ExtentTest test;
    String info;
    List<String> bookInfo = new ArrayList<>(); // list with books info

    @BeforeClass
    public void setUp(){
        //RecordDataForVitalsourceNoData.getTestData();
        driver = initchromeDriverWithProxy();
        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
    }

    @BeforeTest
    public void init(){
        extent = new ExtentReports(System.getProperty("user.dir")+"/test-output/Extent.html");
    }

    @Test(dataProvider = "getTestDataforNotFound")
    public void notFoundTestVitalSourse(String isbn) throws Exception {
        test = extent.startTest("captureScreenshot");
        driver.get("https://www.vitalsource.com/textbooks");
        driver.findElement(By.id("term")).sendKeys(isbn, Keys.ENTER);

        List<WebElement> myElements = driver.findElements(By.className("unit-small")); // finds WebElements with results of search

        List<WebElement> searchResultsBlock = driver.findElements(By.className("product-search-result__link")); // finds elements with results of search
        String searchResults[] = new String[searchResultsBlock.size()]; // array of links with books after search
        for (int i = 0; i < searchResultsBlock.size(); i++){
            searchResults[i] = searchResultsBlock.get(i).getText();
        }

        if (myElements.size() > 0){
            Assert.assertTrue(driver.findElement(By.className("unit-small")).getText().contains("unable to locate"));
        }
        else if (searchResultsBlock.size() > 0){
            for (int i = 0; i < searchResults.length; i++){
                driver.findElement(By.linkText(searchResults[i])).click();
                info = driver.findElement(By.className("product-overview__title-block")).getText();
                bookInfo.add(info);
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
        }
        else{
            Assert.assertFalse(driver.findElement(By.className("product-overview__title-block")).getText().contains(isbn));
        }
    }

    @DataProvider
    public Object[][] getTestDataforNotFound() throws Exception{
        Object[][] data = excelToDataProvider.testData("src/testdata/IsbnForNotFound.xlsx", "TestData");
        //testUtils.testDataForJunitNotFoundFromMySql().iterator();
        return data;
    }

    @AfterMethod
    public void getResult(ITestResult result) throws IOException {
        if (result.getStatus() == ITestResult.FAILURE){
            String screenshotPath = capture(driver, "screenshotForExtentReport");
            test.log(LogStatus.FAIL, result.getThrowable());
            test.log(LogStatus.FAIL, "Screenshot Below: "+ test.addScreenCapture(screenshotPath));
        }
        extent.endTest(test);
    }

    @AfterClass
    public void taerDown(){
        if (driver!=null){
            driver.quit();
        }
    }

}
