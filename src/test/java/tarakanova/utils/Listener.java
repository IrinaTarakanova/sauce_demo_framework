package tarakanova.utils;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import tarakanova.base.BaseTest;

import static tarakanova.utils.ScreenshotUtil.takeScreenshot;

public class Listener  implements ITestListener  {
    @Override
    public void onTestStart(ITestResult result) {
        System.out.println("========================================");
        System.out.println("Test Started: " + result.getMethod().getMethodName());
        System.out.println("========================================");
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        System.out.println("✓ Test Passed: " + result.getMethod().getMethodName());
        System.out.println("Execution time: " + (result.getEndMillis() - result.getStartMillis()) + "ms");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        System.out.println("✗ Test Failed: " + result.getMethod().getMethodName());
        System.out.println("Exception: " + result.getThrowable().getMessage());
        
        BaseTest baseTest = (BaseTest) result.getInstance();
        WebDriver driver = baseTest.getDriver();
        String testName = result.getMethod().getMethodName();
        
        try {
            String screenshotPath = takeScreenshot(driver, testName);
            System.out.println("Screenshot saved at: " + screenshotPath);
        } catch (Exception e) {
            System.out.println("Failed to take screenshot: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        System.out.println("⊘ Test Skipped: " + result.getMethod().getMethodName());
        if (result.getThrowable() != null) {
            System.out.println("Reason: " + result.getThrowable().getMessage());
        }
    }

    @Override
    public void onFinish(ITestContext context) {
        System.out.println("========================================");
        System.out.println("Test Suite Finished");
        System.out.println("Total tests: " + context.getAllTestMethods().length);
        System.out.println("Passed: " + context.getPassedTests().size());
        System.out.println("Failed: " + context.getFailedTests().size());
        System.out.println("Skipped: " + context.getSkippedTests().size());
        System.out.println("========================================");
    }
}
