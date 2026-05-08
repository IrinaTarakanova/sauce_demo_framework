package tarakanova.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import tarakanova.base.BaseTest;
import tarakanova.page.LoginPage;
import tarakanova.utils.User;
import tarakanova.page.ProductPage;

public class LoginTest extends BaseTest {

    private LoginPage loginPage;

    @BeforeMethod(alwaysRun = true)
    public void setUpPage() {
        loginPage = new LoginPage(getDriver());
    }

    @Test(groups = "smoke")
    public void  validUserShouldLoginSuccessfully() {
        Assert.assertEquals(loginPage.getLoginLogo(), "Swag Labs");
      loginPage.login(User.STANDARD.getUsername(), User.STANDARD.getPassword());
      ProductPage productPage = new ProductPage(getDriver());

        Assert.assertTrue(productPage.isOnProductsPage());
    }

    @Test(groups = "negative")
    public void  lockedUserShouldSeeErrorMessage() {
        loginPage.login(User.LOCKED.getUsername(), User.LOCKED.getPassword());

        Assert.assertTrue(loginPage.isErrorMessageDisplayed());
       Assert.assertEquals(loginPage.getErrorMessage()
          , "Epic sadface: Sorry, this user has been locked out." );
    }

    @Test(groups = "negative")
    public void  emptyFieldsLoginShouldShowErrorMessage() {
        loginPage.login("", "");
        Assert.assertTrue(loginPage.isErrorMessageDisplayed());

        Assert.assertEquals(loginPage.getErrorMessage()
         , "Epic sadface: Username is required" );
    }
}
