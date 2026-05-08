package tarakanova.tests;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import tarakanova.base.HelperBaseTest;
import tarakanova.page.CartPage;
import tarakanova.page.CheckoutPage;
import tarakanova.page.ProductPage;
import tarakanova.utils.JsonReader;

import java.util.Map;


public class CheckoutPageTest extends HelperBaseTest {

    private CheckoutPage goToCheckoutPage() {
        ProductPage productPage = addItemsFromProductPage();
        CartPage cartPage = productPage.goToCartPage();
        return cartPage.clickCheckoutButton();
    }

    @Test(groups = "smoke")
    public void shouldFillTheFormAndClickCheckoutButton() {

        CheckoutPage checkoutPage = goToCheckoutPage();
        checkoutPage.fillCheckoutInformation("Anna", "Mane", "12345");
        Assert.assertTrue(getDriver().getCurrentUrl().contains("checkout-step-two"));
    }

    @Test(groups = "smoke")
    public void totalItemsCheckoutAndPriceClickFinish() {
        CheckoutPage checkoutPage = goToCheckoutPage();
        checkoutPage.fillCheckoutInformation("Anna", "Mane", "12345");
        Assert.assertTrue(getDriver().getCurrentUrl().contains("checkout-step-two"));
        for (String item : items) {
            Assert.assertTrue(checkoutPage.isProductInCart(item), "Item not found: " + item);
        }
        Assert.assertEquals(checkoutPage.getCartItemsCount(), 3);
        double calculated = checkoutPage.getAddedTotalPrice();
        double actual = checkoutPage.getItemTotalPriceFromPage();

        Assert.assertEquals(actual, calculated, 0.01);
    }

    @Test(dataProvider = "checkoutValidationData", groups = "negative")
    public void shouldShowErrorForInvalidCheckoutData(Map<String, String> data) {

        CheckoutPage checkoutPage = goToCheckoutPage();

        checkoutPage.fillCheckoutInformation(
                data.get("firstName"),
                data.get("lastName"),
                data.get("postalCode")
        );

        Assert.assertEquals(
                checkoutPage.getErrorMessage(),
                data.get("expectedErrorMessage")
        );
    }

    @DataProvider(name = "checkoutValidationData")
    public Object[][] checkoutValidationData() {
        return JsonReader.getCheckoutValidationData();
    }
}

     /*  @Test(dataProvider = "checkoutValidationData", groups = "negative")
    public void emptyFirstNameShouldShowError() {
           CheckoutPage checkoutPage = goToCheckoutPage();
            checkoutPage.fillCheckoutInformation("", "Mane", "12345");

            Assert.assertEquals(checkoutPage.getErrorMessage()
                    , "Error: First Name is required");
    }
    @Test(dataProvider = "checkoutValidationData", groups = "negative")
    public void emptyLastNameShouldShowError() {
        CheckoutPage checkoutPage = goToCheckoutPage();
        checkoutPage.fillCheckoutInformation("Anna", "", "12345");

        Assert.assertEquals(checkoutPage.getErrorMessage()
                , "Error: Last Name is required");
    }

    @Test(dataProvider = "checkoutValidationData", groups = "negative")
    public void emptyPostalCodeShouldShowError() {
        CheckoutPage checkoutPage = goToCheckoutPage();
        checkoutPage.fillCheckoutInformation("Anna", "Mane", "");

        Assert.assertEquals(checkoutPage.getErrorMessage()
                , "Error: Postal Code is required");
    }
    @Test(dataProvider = "checkoutValidationData", groups = "negative")
    public void allFieldsEmptyShouldShowFirstNameError() {
        CheckoutPage checkoutPage = goToCheckoutPage();
        checkoutPage.fillCheckoutInformation("", "", "");

        Assert.assertEquals(checkoutPage.getErrorMessage()
                , "Error: First Name is required");
    }

    @DataProvider(name = "checkoutValidationData")
    public Object[][] checkoutValidationData() {
        return new Object[][]{
                {"", "Mane", "12345", "Error: First Name is required"},
                {"Anna", "", "12345", "Error: Last Name is required"},
                {"Anna", "Mane", "", "Error: Postal Code is required"},
                {"", "", "", "Error: First Name is required"}
        };
    } */



