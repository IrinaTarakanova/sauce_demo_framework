package tarakanova.utils;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

/**
 * ScreenshotUtil provides utility methods for capturing and saving screenshots
 * during test execution, typically used for failure analysis and reporting.
 *
 * @author Irina Tarakanova
 * @version 1.0
 */
public class ScreenshotUtil {
    private static final Logger logger = LoggerFactory.getLogger(ScreenshotUtil.class);

    /**
     * Captures a screenshot of the current browser window and saves it to the screenshots directory.
     * Uses timestamp and test name to create unique filenames.
     *
     * @param driver WebDriver instance to capture screenshot from
     * @param testName Name of the test method (used in filename)
     * @return Absolute path to the saved screenshot file
     * @throws IOException if screenshot capture or file save fails
     */
    public static String takeScreenshot(WebDriver driver, String testName) throws IOException {
        logger.info("Capturing screenshot for test: {}", testName);

        try {
            // Capture screenshot as file
            File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            logger.debug("Screenshot captured from browser");

            // Generate unique filename with timestamp to avoid overwriting
            String timestamp = String.valueOf(System.currentTimeMillis());
            String sanitizedTestName = testName.replaceAll("[^a-zA-Z0-9]", "_");
            logger.debug("Generated timestamp: {}, sanitized test name: {}", timestamp, sanitizedTestName);

            // Ensure screenshots directory exists
            String screenshotsDir = System.getProperty("user.dir") + "/screenshots";
            new File(screenshotsDir).mkdirs();
            logger.debug("Screenshots directory verified/created: {}", screenshotsDir);

            // Create full file path
            String filePath = screenshotsDir + "/" + sanitizedTestName + "_" + timestamp + ".png";
            File screenshot = new File(filePath);
            logger.debug("Screenshot will be saved to: {}", filePath);

            // Save screenshot to file
            FileUtils.copyFile(src, screenshot);
            logger.info("Screenshot saved successfully: {}", screenshot.getAbsolutePath());

            return screenshot.getAbsolutePath();

        } catch (IOException e) {
            logger.error("Failed to capture or save screenshot for test '{}': {}", testName, e.getMessage(), e);
            throw e;
        }
    }
}
