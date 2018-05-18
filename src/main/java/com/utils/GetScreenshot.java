package com.utils;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;

public class GetScreenshot {

    public static String capture(WebDriver driver, String screenshotName) throws IOException{
        TakesScreenshot ts = (TakesScreenshot)driver;
        File source = ts.getScreenshotAs(OutputType.FILE);
        String dest = System.getProperty("user.dir")+"/ErrorScreenshot/"+screenshotName+".png";
        File destination = new File(dest);
        FileUtils.copyFile(source, destination);
        return dest;
    }

}
