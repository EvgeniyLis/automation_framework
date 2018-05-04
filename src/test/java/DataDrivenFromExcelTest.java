
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.utils.ConfigProperties;
import com.utils.ExcelToDataProvider;

import static com.utils.TestUtils.initchromeDriverWithProxy;

public class DataDrivenFromExcelTest {

    WebDriver webDriver;
    ExcelToDataProvider excelToDataProvider = new ExcelToDataProvider();
    private StringBuffer verificationErrors = new StringBuffer();


    @BeforeClass
    public void setUp(){
        webDriver = initchromeDriverWithProxy();
        webDriver.get("https://www.nhlbi.nih.gov/health/educational/lose_wt/BMI/bmi-m.htm");
        webDriver.manage().window().maximize();
    }



    @Test(dataProvider = "getTestData")
    public void testBmiCalc(String height, String weight, String bmi){
        try {
            webDriver.findElement(By.id("htc")).clear();
            webDriver.findElement(By.id("htc")).sendKeys(height);

            webDriver.findElement(By.id("kg")).clear();
            webDriver.findElement(By.id("kg")).sendKeys(weight);

            webDriver.findElement(By.cssSelector("input[type='button']")).click();

            Assert.assertEquals(webDriver.findElement(By.id("yourbmi")).getAttribute("value"), bmi);
            System.out.println(height);
            System.out.println(weight);
            System.out.println(bmi);

        }catch (Error e) {
            //Capture and append Exceptions/Errors
            verificationErrors.append(e.toString());
            System.err.println("Assertion Fail "+ verificationErrors.
                    toString());
        }
    }

    @DataProvider
    public Object[][] getTestData() throws Exception{
        Object[][] data = excelToDataProvider.testData(ConfigProperties.getTestProperty("bmitestpath"), "TestDataForBMI");
        return data;
    }

    @AfterClass
    public void taerDown(){
        if (webDriver!=null){
            webDriver.quit();
        }
    }


}
