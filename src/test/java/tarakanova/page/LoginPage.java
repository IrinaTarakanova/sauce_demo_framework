package tarakanova.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tarakanova.utils.WaitUtils;

/**
 * LoginPage class represents the login page of the SauceDemo application.
 * Provides methods for user authentication, error message handling,
 * and navigation to the products page after successful login.
 *
 * @author Irina Tarakanova
 * @version 1.0
 */
public class LoginPage {
    private static final Logger logger = LoggerFactory.getLogger(LoginPage.class);
    private WebDriver driver;
    private WaitUtils wait;

    // Element locators for login page
    private By usernameField = By.id("user-name");
    private By passwordField = By.id("password");
    private By loginButton = By.id("login-button");
    private By loginLogo = By.cssSelector(".login_logo");
    private By errorMessage = By.cssSelector("h3[data-test='error']");

    /**
     * Constructor for LoginPage.
     * Initializes WebDriver and WaitUtils for element interactions.
     *
     * @param driver WebDriver instance
     */
    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WaitUtils(driver);
        logger.debug("LoginPage initialized");
    }

    /**
     * Gets the text of the login logo element.
     * Used to verify that the login page is properly loaded.
     *
     * @return Login logo text
     */
    public String getLoginLogo() {
        String logoText = driver.findElement(loginLogo).getText();
        logger.debug("Login logo text: {}", logoText);
        return logoText;
    }

    /**
     * Performs login with the provided username and password.
     * Enters credentials and clicks login button, then returns ProductPage.
     *
     * @param username User's username
     * @param password User's password
     * @return ProductPage instance after successful login
     */
    public ProductPage login(String username, String password) {
        logger.info("Attempting login with username: {}", username);

        // Using explicit wait to ensure the username field is visible before interacting
        logger.debug("Waiting for username field to be visible");
        wait.waitForVisible(usernameField).sendKeys(username);
        logger.debug("Username entered: {}", username);

        driver.findElement(passwordField).sendKeys(password);
        logger.debug("Password entered");

        logger.debug("Clicking login button");
        driver.findElement(loginButton).click();

        logger.info("Login attempt completed, returning ProductPage");
        return new ProductPage(driver);
    }

    /**
     * Gets the error message text displayed on login failures.
     *
     * @return Error message text
     */
    public String getErrorMessage() {
        String errorMsg = driver.findElement(errorMessage).getText();
        logger.warn("Login error message: {}", errorMsg);
        return errorMsg;
    }

    /**
     * Checks if the error message is currently displayed on the page.
     *
     * @return true if error message is displayed, false otherwise
     */
    public boolean isErrorMessageDisplayed() {
        boolean isDisplayed = driver.findElement(errorMessage).isDisplayed();
        logger.debug("Error message displayed: {}", isDisplayed);
        return isDisplayed;
    }
}
