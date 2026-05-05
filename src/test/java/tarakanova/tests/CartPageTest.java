package tarakanova.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import tarakanova.base.BaseTest;
import tarakanova.page.CartPage;
import tarakanova.page.LoginPage;
import tarakanova.page.ProductPage;
import tarakanova.utils.User;

import java.util.List;

public class CartPageTest extends BaseTest {

    private final List<String> items = List.of( "Sauce Labs Backpack",
            "Sauce Labs Bike Light",
            "Sauce Labs Bolt T-Shirt");

    private ProductPage loginAndMoveToProductPage(){
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(User.STANDARD.getUsername(), User.STANDARD.getPassword());
        return new ProductPage(driver);
    }

    private ProductPage addItemsFromProductPage(){
        ProductPage productPage = loginAndMoveToProductPage();
        for (String item : items) {
            productPage.addItemToCart(item);
        }
        return productPage;
    }

    @Test
    public void productsShouldBeVisibleInCart() {
        ProductPage productPage = addItemsFromProductPage();

        CartPage cartPage = productPage.goToCartPage();

        Assert.assertTrue(cartPage.isProductInCart("Sauce Labs Backpack"));
        Assert.assertTrue(cartPage.isProductInCart("Sauce Labs Bike Light"));
        Assert.assertTrue(cartPage.isProductInCart("Sauce Labs Bolt T-Shirt"));
        Assert.assertEquals(cartPage.getCartItemsCount(), 3);
        cartPage.clickCheckoutButton();
        Assert.assertTrue(driver.getCurrentUrl().contains("checkout-step-one"));
    }


}
