package tarakanova.utils;

import com.aventstack.extentreports.ExtentTest;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import tarakanova.base.BaseTest;

import java.util.Arrays;

import static tarakanova.utils.ScreenshotUtil.takeScreenshot;

/**
 * Listener class implements TestNG ITestListener interface to provide
 * comprehensive test execution monitoring and reporting capabilities.
 * Integrates with ExtentReports for detailed HTML reports and automatic
 * screenshot capture on test failures.
 *
 * @author Irina Tarakanova
 * @version 1.0
 */
public class Listener implements ITestListener {
    private static final Logger logger = LoggerFactory.getLogger(Listener.class);
    private static final ThreadLocal<ExtentTest> test = new ThreadLocal<>();

    /**
     * Called when a test method starts execution.
     * Creates a new ExtentTest instance and logs test initialization.
     *
     * @param result Test result containing test information
     */
    @Override
    public void onTestStart(ITestResult result) {
        logger.info("============================================");
        logger.info("Test Started: {}", result.getMethod().getMethodName());
        logger.info("============================================");

        // Create ExtentTest for reporting
        test.set(ExtentReportManager.getReport().createTest(result.getName()));
        test.get().info("Test started");
        test.get().info("Parameters: " + Arrays.toString(result.getParameters()));

        logger.debug("ExtentTest created for test: {}", result.getName());
    }

    /**
     * Called when a test method passes successfully.
     * Logs success and cleans up test instance.
     *
     * @param result Test result containing test information
     */
    @Override
    public void onTestSuccess(ITestResult result) {
        logger.info("✓ Test Passed: {}", result.getMethod().getMethodName());
        logger.info("Execution time: {}ms", (result.getEndMillis() - result.getStartMillis()));

        test.get().pass("Test passed");
        test.remove();

        logger.debug("Test success logged and ExtentTest cleaned up");
    }

    /**
     * Called when a test method fails.
     * Captures screenshot, logs failure details, and updates ExtentReport.
     *
     * @param result Test result containing failure information
     */
    @Override
    public void onTestFailure(ITestResult result) {
        logger.error("✗ Test Failed: {}", result.getMethod().getMethodName());
        logger.error("Exception: {}", result.getThrowable().getMessage());

        // Log failure to ExtentReport
        test.get().fail(result.getThrowable());

        try {
            // Get WebDriver instance and capture screenshot
            BaseTest baseTest = (BaseTest) result.getInstance();
            WebDriver driver = baseTest.getDriver();
            String testName = result.getMethod().getMethodName();

            logger.debug("Capturing screenshot for failed test: {}", testName);
            String screenshotPath = takeScreenshot(driver, testName);

            // Add screenshot to ExtentReport
            test.get().addScreenCaptureFromPath(screenshotPath);
            logger.info("Screenshot saved and added to report: {}", screenshotPath);

        } catch (Exception e) {
            logger.error("Failed to capture screenshot: {}", e.getMessage(), e);
            System.out.println("Failed to take screenshot: " + e.getMessage());
        }

        test.remove();
        logger.debug("Test failure logged and ExtentTest cleaned up");
    }

    /**
     * Called when a test method is skipped.
     * Logs skip reason and updates ExtentReport.
     *
     * @param result Test result containing skip information
     */
    @Override
    public void onTestSkipped(ITestResult result) {
        logger.warn("⊘ Test Skipped: {}", result.getMethod().getMethodName());

        test.get().skip("Test skipped");

        if (result.getThrowable() != null) {
            logger.warn("Skip reason: {}", result.getThrowable().getMessage());
            test.get().skip(result.getThrowable());
        }

        test.remove();
        logger.debug("Test skip logged and ExtentTest cleaned up");
    }

    /**
     * Called when the entire test suite finishes execution.
     * Generates final ExtentReport and logs suite summary.
     *
     * @param context Test context containing suite information
     */
    @Override
    public void onFinish(ITestContext context) {
        logger.info("============================================");
        logger.info("Test Suite Finished");
        logger.info("Total tests: {}", context.getAllTestMethods().length);
        logger.info("Passed: {}", context.getPassedTests().size());
        logger.info("Failed: {}", context.getFailedTests().size());
        logger.info("Skipped: {}", context.getSkippedTests().size());
        logger.info("============================================");

        // Generate final ExtentReport
        ExtentReportManager.getReport().flush();
        logger.info("ExtentReport generated and saved");

        logger.info("Test suite execution completed");
    }
}
