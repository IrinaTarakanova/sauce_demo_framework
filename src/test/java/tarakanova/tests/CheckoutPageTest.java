package tarakanova.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import tarakanova.base.HelperBaseTest;
import tarakanova.page.CartPage;
import tarakanova.page.CheckoutPage;
import tarakanova.page.ProductPage;


public class CheckoutPageTest extends HelperBaseTest {

    private CheckoutPage goToCheckoutPage() {
        ProductPage productPage = addItemsFromProductPage();
        CartPage cartPage = productPage.goToCartPage();
        return cartPage.clickCheckoutButton();
    }

       @Test(groups = "positive")
        public void shouldFillTheFormAndClickCheckoutButton() {

            CheckoutPage checkoutPage = goToCheckoutPage();
            checkoutPage.fillCheckoutInformation("Anna", "Mane", "12345");
            Assert.assertTrue(driver.getCurrentUrl().contains("checkout-step-two"));
        }

        @Test(groups = "positive")
        public void totalItemsCheckoutAndPriceClickFinish() {
            CheckoutPage checkoutPage = goToCheckoutPage();
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

       @Test(groups = "negative")
    public void emptyFirstNameShouldShowError() {
           CheckoutPage checkoutPage = goToCheckoutPage();
            checkoutPage.fillCheckoutInformation("", "Mane", "12345");

            Assert.assertEquals(checkoutPage.getErrorMessage()
                    , "Error: First Name is required");
    }
    @Test(groups = "negative")
    public void emptyLastNameShouldShowError() {
        CheckoutPage checkoutPage = goToCheckoutPage();
        checkoutPage.fillCheckoutInformation("Anna", "", "12345");

        Assert.assertEquals(checkoutPage.getErrorMessage()
                , "Error: Last Name is required");
    }

    @Test(groups = "negative")
    public void emptyPostalCodeShouldShowError() {
        CheckoutPage checkoutPage = goToCheckoutPage();
        checkoutPage.fillCheckoutInformation("Anna", "Mane", "");

        Assert.assertEquals(checkoutPage.getErrorMessage()
                , "Error: Postal Code is required");
    }
    @Test(groups = "negative")
    public void allFieldsEmptyShouldShowFirstNameError() {
        CheckoutPage checkoutPage = goToCheckoutPage();
        checkoutPage.fillCheckoutInformation("", "", "");

        Assert.assertEquals(checkoutPage.getErrorMessage()
                , "Error: First Name is required");
    }

}
