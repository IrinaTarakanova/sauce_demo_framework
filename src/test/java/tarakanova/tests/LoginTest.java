package tarakanova.tests;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import tarakanova.base.BaseTest;
import tarakanova.page.LoginPage;
import tarakanova.utils.User;
import tarakanova.page.ProductPage;

/**
 * LoginTest class contains test cases for login functionality of SauceDemo application.
 * Tests include valid login, locked user scenarios, and empty field validations.
 *
 * @author Irina Tarakanova
 * @version 1.0
 */
public class LoginTest extends BaseTest {
    private static final Logger logger = LoggerFactory.getLogger(LoginTest.class);
    private LoginPage loginPage;

    /**
     * Setup method executed before each test method.
     * Initializes the LoginPage object.
     */
    @BeforeMethod(alwaysRun = true)
    public void setUpPage() {
        logger.debug("Setting up LoginPage for test");
        loginPage = new LoginPage(getDriver());
        logger.debug("LoginPage initialized successfully");
    }

    /**
     * Test case: Verify that a valid user can login successfully.
     * This is a smoke test to ensure basic login functionality works.
     *
     * Test Steps:
     * 1. Verify login logo is displayed
     * 2. Enter valid credentials (standard_user)
     * 3. Verify user is redirected to products page
     */
    @Test(groups = "smoke")
    public void validUserShouldLoginSuccessfully() {
        logger.info("Starting test: validUserShouldLoginSuccessfully");

        // Verify login page is loaded
        String logoText = loginPage.getLoginLogo();
        Assert.assertEquals(logoText, "Swag Labs", "Login logo should display 'Swag Labs'");
        logger.debug("Login logo verified: {}", logoText);

        // Perform login with standard user
        logger.debug("Attempting login with standard user credentials");
        loginPage.login(User.STANDARD.getUsername(), User.STANDARD.getPassword());

        // Verify successful login by checking products page
        ProductPage productPage = new ProductPage(getDriver());
        boolean isOnProductsPage = productPage.isOnProductsPage();
        Assert.assertTrue(isOnProductsPage, "User should be redirected to products page after login");
        logger.info("Login successful - user redirected to products page");

        logger.info("Test completed: validUserShouldLoginSuccessfully - PASSED");
    }

    /**
     * Test case: Verify that a locked user cannot login and sees appropriate error message.
     * This is a negative test case for security validation.
     *
     * Test Steps:
     * 1. Attempt login with locked user credentials
     * 2. Verify error message is displayed
     * 3. Verify error message content
     */
    @Test(groups = "negative")
    public void lockedUserShouldSeeErrorMessage() {
        logger.info("Starting test: lockedUserShouldSeeErrorMessage");

        // Attempt login with locked user
        logger.debug("Attempting login with locked user credentials");
        loginPage.login(User.LOCKED.getUsername(), User.LOCKED.getPassword());

        // Verify error message is displayed
        boolean isErrorDisplayed = loginPage.isErrorMessageDisplayed();
        Assert.assertTrue(isErrorDisplayed, "Error message should be displayed for locked user");
        logger.debug("Error message is displayed");

        // Verify error message content
        String errorMessage = loginPage.getErrorMessage();
        Assert.assertEquals(errorMessage,
            "Epic sadface: Sorry, this user has been locked out.",
            "Error message should indicate user is locked out");
        logger.info("Locked user error message verified: {}", errorMessage);

        logger.info("Test completed: lockedUserShouldSeeErrorMessage - PASSED");
    }

    /**
     * Test case: Verify that empty login fields show appropriate error message.
     * This is a negative test case for input validation.
     *
     * Test Steps:
     * 1. Attempt login with empty username and password
     * 2. Verify error message is displayed
     * 3. Verify error message content
     */
    @Test(groups = "negative")
    public void emptyFieldsLoginShouldShowErrorMessage() {
        logger.info("Starting test: emptyFieldsLoginShouldShowErrorMessage");

        // Attempt login with empty fields
        logger.debug("Attempting login with empty username and password");
        loginPage.login("", "");

        // Verify error message is displayed
        boolean isErrorDisplayed = loginPage.isErrorMessageDisplayed();
        Assert.assertTrue(isErrorDisplayed, "Error message should be displayed for empty fields");
        logger.debug("Error message is displayed for empty fields");

        // Verify error message content
        String errorMessage = loginPage.getErrorMessage();
        Assert.assertEquals(errorMessage,
            "Epic sadface: Username is required",
            "Error message should indicate username is required");
        logger.info("Empty fields error message verified: {}", errorMessage);

        logger.info("Test completed: emptyFieldsLoginShouldShowErrorMessage - PASSED");
    }
}
