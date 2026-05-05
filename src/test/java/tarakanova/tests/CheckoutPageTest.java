package tarakanova.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import tarakanova.base.HelperBaseTest;
import tarakanova.page.CartPage;
import tarakanova.page.CheckoutPage;
import tarakanova.page.ProductPage;


public class CheckoutPageTest extends HelperBaseTest {



       @Test
        public void shouldFillTheFormAndClickCheckoutButton() {
            ProductPage productPage = addItemsFromProductPage();
            CartPage cartPage = productPage.goToCartPage();
            CheckoutPage checkoutPage = cartPage.clickCheckoutButton();
            checkoutPage.fillCheckoutInformation("Anna", "Mane", "12345");
            Assert.assertTrue(driver.getCurrentUrl().contains("checkout-step-two"));
        }

        @Test
        public void totalItemsCheckoutAndPriceClickFinish() {
            ProductPage productPage = addItemsFromProductPage();
            CartPage cartPage = productPage.goToCartPage();
            CheckoutPage checkoutPage = cartPage.clickCheckoutButton();
            checkoutPage.fillCheckoutInformation("Anna", "Mane", "12345");
            Assert.assertTrue(driver.getCurrentUrl().contains("checkout-step-two"));
            for (String item : items) {
                Assert.assertTrue(checkoutPage.isProductInCart(item), "Item not found: " + item);
            }
            Assert.assertEquals(checkoutPage.getCartItemsCount(), 3);
            double calculated = checkoutPage.getAddedTotalPrice();
            double actual = checkoutPage.getItemTotalPriceFromPage();

            Assert.assertEquals(actual, calculated, 0.01);

        }

}
