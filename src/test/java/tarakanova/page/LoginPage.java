package tarakanova.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import tarakanova.utils.WaitUtils;

public class LoginPage {

    private WebDriver driver;
    private WaitUtils wait;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WaitUtils(driver);
    }

    private By usernameField = By.id("user-name");
    private By passwordField = By.id("password");
    private By loginButton = By.id("login-button");
    private By loginLogo = By.cssSelector(".login_logo");
    private By errorMessage = By.cssSelector("h3[data-test='error']");

    public String getLoginLogo() {
        return driver.findElement(loginLogo).getText();
    }

    public ProductPage login(String username, String password) {

        //using explicit wait to ensure the username field is visible before interacting with it
        wait.waitForVisible(usernameField).sendKeys(username);
        driver.findElement(passwordField).sendKeys(password);
        driver.findElement(loginButton).click();
        return new ProductPage(driver);
    }

    public String getErrorMessage() {
        return driver.findElement(errorMessage).getText();
    }

    public boolean isErrorMessageDisplayed() {
        return driver.findElement(errorMessage).isDisplayed();
    }
}

