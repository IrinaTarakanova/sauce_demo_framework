package tarakanova.utils;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;

public class ScreenshotUtil {

    public static String takeScreenshot(WebDriver driver, String testName) throws IOException {
        File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

       //avoid overwriting files
        String timestamp = String.valueOf(System.currentTimeMillis());
        testName = testName.replaceAll("[^a-zA-Z0-9]", "_");

        //creating directory for screenshots
        new File(System.getProperty("user.dir") + "/screenshots").mkdirs();

        String path = System.getProperty("user.dir") +
                "/screenshots/" + testName + "_" + timestamp + ".png";
        File screenshot = new File(path);
        FileUtils.copyFile(src, screenshot);

        return screenshot.getAbsolutePath();
    }
}
