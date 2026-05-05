package tarakanova.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import tarakanova.base.BaseTest;
import tarakanova.page.CartPage;
import tarakanova.page.CheckoutPage;
import tarakanova.page.LoginPage;
import tarakanova.page.ProductPage;
import tarakanova.utils.User;

import java.util.List;

public class CheckoutPageTest extends BaseTest {

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
        public void shouldFillTheFormAndClickCheckoutButton() {
            ProductPage productPage = addItemsFromProductPage();
            CartPage cartPage = productPage.goToCartPage();
            CheckoutPage checkoutPage = cartPage.clickCheckoutButton();
            checkoutPage.fillTheForm("Anna", "Mane", "12345");
            Assert.assertTrue(driver.getCurrentUrl().contains("checkout-step-two"));
        }

        @Test
        public void totalItemsCheckoutAndPriceClickFinish() {
            ProductPage productPage = addItemsFromProductPage();
            CartPage cartPage = productPage.goToCartPage();
            CheckoutPage checkoutPage = cartPage.clickCheckoutButton();
            checkoutPage.fillTheForm("Anna", "Mane", "12345");
            Assert.assertTrue(driver.getCurrentUrl().contains("checkout-step-two"));
            Assert.assertTrue(checkoutPage.isProductInCart("Sauce Labs Backpack"));
            Assert.assertTrue(checkoutPage.isProductInCart("Sauce Labs Bike Light"));
            Assert.assertTrue(checkoutPage.isProductInCart("Sauce Labs Bolt T-Shirt"));
            Assert.assertEquals(checkoutPage.getCartItemsCount(), 3);
            double calculated = checkoutPage.getAddedTotalPrice();
            double actual = checkoutPage.getItemTotalPriceFromPage();

            Assert.assertEquals(actual, calculated, 0.01);

        }

}
