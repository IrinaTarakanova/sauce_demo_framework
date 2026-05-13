package tarakanova.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.List;

/**
 * WaitUtils provides explicit wait methods for WebDriver elements and conditions.
 * Uses WebDriverWait with configurable timeout for reliable element interactions.
 *
 * @author Irina Tarakanova
 * @version 1.0
 */
public class WaitUtils {
    private static final Logger logger = LoggerFactory.getLogger(WaitUtils.class);
    private WebDriver driver;
    private WebDriverWait wait;

    /**
     * Constructor for WaitUtils.
     * Initializes WebDriverWait with 10-second timeout.
     *
     * @param driver WebDriver instance
     */
    public WaitUtils(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        logger.debug("WaitUtils initialized with 10-second timeout");
    }

    /**
     * Waits for an element located by the given locator to be visible.
     *
     * @param locator By locator of the element
     * @return WebElement once it becomes visible
     */
    public WebElement waitForVisible(By locator) {
        logger.debug("Waiting for element to be visible: {}", locator);
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        logger.debug("Element is now visible: {}", locator);
        return element;
    }

    /**
     * Waits for an element located by the given locator to be clickable.
     *
     * @param locator By locator of the element
     * @return WebElement once it becomes clickable
     */
    public WebElement waitForClickable(By locator) {
        logger.debug("Waiting for element to be clickable: {}", locator);
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
        logger.debug("Element is now clickable: {}", locator);
        return element;
    }

    /**
     * Waits for the current URL to contain the specified text.
     *
     * @param text Text that should be contained in the URL
     * @return true if URL contains the text within timeout
     */
    public boolean waitForUrlContains(String text) {
        logger.debug("Waiting for URL to contain: {}", text);
        boolean result = wait.until(ExpectedConditions.urlContains(text));
        logger.debug("URL now contains '{}': {}", text, result);
        return result;
    }

    /**
     * Waits for all elements located by the given locator to be visible.
     *
     * @param locator By locator of the elements
     * @return List of WebElements once all are visible
     */
    public List<WebElement> waitForAllVisible(By locator) {
        logger.debug("Waiting for all elements to be visible: {}", locator);
        List<WebElement> elements = wait.until(
                ExpectedConditions.visibilityOfAllElementsLocatedBy(locator)
        );
        logger.debug("All {} elements are now visible: {}", elements.size(), locator);
        return elements;
    }

    /**
     * Waits for the given WebElement to be visible.
     *
     * @param element WebElement to wait for
     * @return WebElement once it becomes visible
     */
    public WebElement waitForVisible(WebElement element) {
        logger.debug("Waiting for WebElement to be visible");
        WebElement visibleElement = wait.until(ExpectedConditions.visibilityOf(element));
        logger.debug("WebElement is now visible");
        return visibleElement;
    }

    /**
     * Waits for the given WebElement to be clickable.
     *
     * @param element WebElement to wait for
     * @return WebElement once it becomes clickable
     */
    public WebElement waitForClickable(WebElement element) {
        logger.debug("Waiting for WebElement to be clickable");
        WebElement clickableElement = wait.until(ExpectedConditions.elementToBeClickable(element));
        logger.debug("WebElement is now clickable");
        return clickableElement;
    }
}
