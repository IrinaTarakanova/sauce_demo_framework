package tarakanova.utils;

import com.aventstack.extentreports.ExtentTest;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import tarakanova.base.BaseTest;

import java.util.Arrays;

import static tarakanova.utils.ScreenshotUtil.takeScreenshot;

public class Listener  implements ITestListener  {

    private static final ThreadLocal<ExtentTest> test = new ThreadLocal<>();

    @Override
    public void onTestStart(ITestResult result) {
        test.set(ExtentReportManager.getReport()
                .createTest(result.getName()));
        test.get().info("Test started");
        test.get().info("Parameters: " + Arrays.toString(result.getParameters()));

    }

    @Override
    public void onTestSuccess(ITestResult result) {
        test.get().pass("Test passed");
        test.remove();
    }

    @Override
    public void onTestFailure(ITestResult result) {
        test.get().fail(result.getThrowable());

        BaseTest baseTest = (BaseTest) result.getInstance();
        WebDriver driver = baseTest.getDriver();
        String testName = result.getMethod().getMethodName();
        
        try {
            String screenshotPath = takeScreenshot(driver, testName);
            test.get().addScreenCaptureFromPath(screenshotPath);
            System.out.println("Screenshot saved at: " + screenshotPath);
        } catch (Exception e) {
            System.out.println("Failed to take screenshot: " + e.getMessage());
            e.printStackTrace();
        }
        test.remove();
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        test.get().skip("Test skipped");

        if (result.getThrowable() != null) {
            test.get().skip(result.getThrowable());
            test.remove();
        }
    }

    @Override
    public void onFinish(ITestContext context) {
        ExtentReportManager.getReport().flush();
       
    }
}
