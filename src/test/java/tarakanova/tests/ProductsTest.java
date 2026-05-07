package tarakanova.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import tarakanova.base.BaseTest;
import tarakanova.page.LoginPage;
import tarakanova.page.ProductPage;
import tarakanova.utils.User;

import java.util.List;

public class ProductsTest extends BaseTest {


    private final List<String> items = List.of( "Sauce Labs Backpack",
            "Sauce Labs Bike Light",
            "Sauce Labs Bolt T-Shirt");

    private ProductPage loginAndMoveToProductPage(){
        LoginPage loginPage = new LoginPage(getDriver());
      loginPage.login(User.STANDARD.getUsername(), User.STANDARD.getPassword());
      return new ProductPage(getDriver());
    }

    private ProductPage addItemsFromProductPage(){
        ProductPage productPage = loginAndMoveToProductPage();
        for (String item : items) {
            productPage.addItemToCart(item);
        }
        return productPage;
    }

    @Test
    public void addItemsToCart() {
        ProductPage productPage = addItemsFromProductPage();
        Assert.assertEquals(productPage.getCartBadgeCount(), 3);
        productPage.goToCartPage();
        Assert.assertEquals(getDriver().getCurrentUrl(), "https://www.saucedemo.com/cart.html");
    }

    @Test
    public void removeItemsFromCart() {
        ProductPage productPage = addItemsFromProductPage();
        Assert.assertEquals(productPage.getCartBadgeCount(), 3);

        productPage.removeFromCart("Sauce Labs Backpack");

        Assert.assertEquals(productPage.getCartBadgeCount(), 2);

    }

}
