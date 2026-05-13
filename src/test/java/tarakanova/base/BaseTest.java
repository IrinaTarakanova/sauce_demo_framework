package tarakanova.base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

/**
 * BaseTest class provides common setup and teardown functionality for all test classes.
 * Uses ThreadLocal for thread-safe WebDriver management in parallel test execution.
 *
 * @author Irina Tarakanova
 * @version 1.0
 */
public class BaseTest {
    private static final Logger logger = LoggerFactory.getLogger(BaseTest.class);
    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    /**
     * Gets the WebDriver instance for the current thread.
     * @return WebDriver instance
     */
    public WebDriver getDriver() {
        return driver.get();
    }

    /**
     * Sets the WebDriver instance for the current thread.
     * @param webDriver WebDriver instance to set
     */
    private void setDriver(WebDriver webDriver) {
        driver.set(webDriver);
    }

    /**
     * Removes the WebDriver instance for the current thread.
     * Should be called in teardown to prevent memory leaks.
     */
    private void removeDriver() {
        driver.remove();
    }

    /**
     * Setup method executed before each test method.
     * Initializes WebDriver with Chrome browser and configures browser settings.
     */
    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        logger.info("Starting test setup...");

        // Setup ChromeDriver using WebDriverManager
        WebDriverManager.chromedriver().setup();
        logger.debug("ChromeDriver setup completed");

        // Configure Chrome options
        ChromeOptions options = new ChromeOptions();
        logger.debug("Configuring Chrome options...");

        // Disable credential services to prevent browser prompts
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("credentials_enable_service", false);
        prefs.put("profile.password_manager_enabled", false);
        prefs.put("profile.password_manager_leak_detection", false);

        options.setExperimentalOption("prefs", prefs);

        // Create WebDriver instance
        setDriver(new ChromeDriver(options));
        logger.info("ChromeDriver instance created successfully");

        // Configure browser window and timeouts
        getDriver().manage().window().maximize();
        logger.debug("Browser window maximized");

        getDriver().manage().deleteAllCookies();
        logger.debug("Browser cookies cleared");

        getDriver().manage().timeouts()
                .implicitlyWait(Duration.ofSeconds(5));
        logger.debug("Implicit wait set to 5 seconds");

        // Navigate to application URL
        getDriver().get("https://www.saucedemo.com/");
        logger.info("Navigated to SauceDemo application: https://www.saucedemo.com/");

        logger.info("Test setup completed successfully");
    }

    /**
     * Teardown method executed after each test method.
     * Closes the browser and cleans up WebDriver instance.
     */
    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        logger.info("Starting test teardown...");

        if (getDriver() != null) {
            getDriver().quit();
            logger.info("Browser closed successfully");
        }

        removeDriver();
        logger.info("WebDriver instance removed from ThreadLocal");
        logger.info("Test teardown completed");
    }
}
