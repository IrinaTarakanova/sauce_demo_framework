package tarakanova.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import tarakanova.base.BaseTest;
import tarakanova.page.LoginPage;
import tarakanova.page.ProductPage;
import tarakanova.utils.User;

public class LoginTest extends BaseTest {


    @Test
    public void  loginWithValidCredentials() {
        LoginPage loginPage = new LoginPage(driver);
        Assert.assertEquals(loginPage.getLoginLogo(), "Swag Labs");
      loginPage.login(User.STANDARD.getUsername(), User.STANDARD.getPassword());

      Assert.assertTrue(driver.getCurrentUrl().contains("inventory"));

    }
}
