
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;

import static com.utils.GetScreenshot.capture;
import static com.utils.TestUtils.initchromeDriverWithProxy;

public class CapturingScreenshotTest {

    ExtentReports extent;
    ExtentTest test;
    WebDriver driver;

    @BeforeTest
    public void init(){
        extent = new ExtentReports(System.getProperty("user.dir")+"/test-output/Extent.html");
    }

    @Test
    public void captureScreenshot(){
        test = extent.startTest("captureScreenshot");
        driver = initchromeDriverWithProxy();
        driver.get("http://google.com");
        String title = driver.getTitle();
        Assert.assertEquals("Home - Automation Test", title);
        test.log(LogStatus.PASS, "Test Passed and titles are equal");
    }

    @AfterMethod
    public void getResult(ITestResult result) throws IOException{
        if (result.getStatus() == ITestResult.FAILURE){
            String screenshotPath = capture(driver, "screenshotForExtentReport");
            test.log(LogStatus.FAIL, result.getThrowable());
            test.log(LogStatus.FAIL, "Screenshot Below: "+ test.addScreenCapture(screenshotPath));
        }
        extent.endTest(test);
    }

    @AfterTest
    public void endReport(){
        driver.close();
        extent.flush();
        extent.close();
    }
}
