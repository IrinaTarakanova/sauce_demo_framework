package tarakanova.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import tarakanova.base.HelperBaseTest;
import tarakanova.page.CartPage;
import tarakanova.page.ProductPage;

public class CartPageTest extends HelperBaseTest {

    @Test(groups = "smoke")
    public void productsShouldBeVisibleInCart() {
        ProductPage productPage = addItemsFromProductPage();

        CartPage cartPage = productPage.goToCartPage();

        for (String item : items) {
            Assert.assertTrue(cartPage.isProductInCart(item), "Item not found: " + item);
        }
        Assert.assertEquals(cartPage.getCartItemsCount(), 3);
        cartPage.clickCheckoutButton();
        Assert.assertTrue(getDriver().getCurrentUrl().contains("checkout-step-one"));
    }


}
