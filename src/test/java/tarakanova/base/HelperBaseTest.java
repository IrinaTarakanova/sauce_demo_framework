package tarakanova.base;

import tarakanova.page.LoginPage;
import tarakanova.page.ProductPage;
import tarakanova.utils.User;

import java.util.List;

public class HelperBaseTest extends BaseTest {

    protected final List<String> items = List.of( "Sauce Labs Backpack",
            "Sauce Labs Bike Light",
            "Sauce Labs Bolt T-Shirt");

    protected ProductPage loginAndMoveToProductPage(){
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(User.STANDARD.getUsername(), User.STANDARD.getPassword());
        return new ProductPage(driver);
    }

    protected ProductPage addItemsFromProductPage(){
        ProductPage productPage = loginAndMoveToProductPage();
        for (String item : items) {
            productPage.addItemToCart(item);
        }
        return productPage;
    }
}
