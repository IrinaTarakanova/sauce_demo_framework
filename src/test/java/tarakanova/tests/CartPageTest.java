package tarakanova.tests;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;
import tarakanova.base.HelperBaseTest;
import tarakanova.page.CartPage;
import tarakanova.page.ProductPage;

/**
 * CartPageTest class contains test cases for cart page functionality.
 * Tests include verifying cart contents and navigation to checkout.
 *
 * @author Irina Tarakanova
 * @version 1.0
 */
public class CartPageTest extends HelperBaseTest {
    private static final Logger logger = LoggerFactory.getLogger(CartPageTest.class);

    /**
     * Test case: Verify that products added to cart are visible in cart page.
     * This is a smoke test to ensure cart functionality works correctly.
     *
     * Test Steps:
     * 1. Add items to cart from product page
     * 2. Navigate to cart page
     * 3. Verify all added items are visible in cart
     * 4. Verify cart item count matches expected
     * 5. Navigate to checkout and verify URL
     */
    @Test(groups = "smoke")
    public void productsShouldBeVisibleInCart() {
        logger.info("Starting test: productsShouldBeVisibleInCart");

        // Add 3 items to cart and navigate to cart page
        logger.debug("Adding items to cart and navigating to cart page");
        ProductPage productPage = addItemsFromProductPage();
        CartPage cartPage = productPage.goToCartPage();

        // Verify all items are present in cart
        logger.debug("Verifying all items are present in cart");
        for (String item : items) {
            boolean itemPresent = cartPage.isProductInCart(item);
            Assert.assertTrue(itemPresent, "Item not found in cart: " + item);
            logger.debug("Verified item in cart: {}", item);
        }

        // Verify cart item count
        int cartItemCount = cartPage.getCartItemsCount();
        Assert.assertEquals(cartItemCount, 3, "Cart should contain 3 items");
        logger.info("Verified cart contains {} items", cartItemCount);

        // Navigate to checkout and verify navigation
        logger.debug("Navigating to checkout from cart");
        cartPage.clickCheckoutButton();

        boolean isOnCheckoutStepOne = getDriver().getCurrentUrl().contains("checkout-step-one");
        Assert.assertTrue(isOnCheckoutStepOne, "Should navigate to checkout step one");
        logger.info("Successfully navigated to checkout step one");

        logger.info("Test completed: productsShouldBeVisibleInCart - PASSED");
    }
}
