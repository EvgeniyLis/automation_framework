
import com.utils.ExcelToDataProvider;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized.Parameters;
import org.junit.runners.Parameterized;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.Assert.*;


import java.util.Arrays;
import java.util.Collection;

import static com.utils.TestUtils.initchromeDriverWithProxy;

@RunWith(value = Parameterized.class)
public class DataDrivenJunitTest {

    static WebDriver webDriver = new ChromeDriver();
    ExcelToDataProvider excelToDataProvider = new ExcelToDataProvider();
    private StringBuffer verificationErrors = new StringBuffer();
    private String height;
    private String weight;
    private String bmi;

    //@Parameterized.Parameters


    public  DataDrivenJunitTest(String height, String weight, String bmi){
        this.height = height;
        this.weight = weight;
        this.bmi = bmi;
    }

    @BeforeClass
    public static void setUp(){

        webDriver.get("https://www.nhlbi.nih.gov/health/educational/lose_wt/BMI/bmi-m.htm");
        webDriver.manage().window().maximize();
    }

    @Test
    public void bmiCalcTest(){
        try {
            webDriver.findElement(By.id("htc")).clear();
            webDriver.findElement(By.id("htc")).sendKeys(height);

            webDriver.findElement(By.id("kg")).clear();
            webDriver.findElement(By.id("kg")).sendKeys(weight);

            webDriver.findElement(By.cssSelector("input[type='button']")).click();

            Assert.assertEquals(bmi,webDriver.findElement(By.id("yourbmi")).getAttribute("value"));
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

    @AfterClass
    public static void taerDown(){
        if (webDriver!=null){
            webDriver.quit();
        }
    }


}
