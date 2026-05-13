package tarakanova.tests;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;
import tarakanova.base.BaseTest;
import tarakanova.page.LoginPage;
import tarakanova.page.ProductPage;
import tarakanova.utils.User;

import java.util.List;

/**
 * ProductsTest class contains test cases for product page functionality.
 * Tests include adding items to cart, removing items from cart,
 * and verifying cart badge updates.
 *
 * @author Irina Tarakanova
 * @version 1.0
 */
public class ProductsTest extends BaseTest {
    private static final Logger logger = LoggerFactory.getLogger(ProductsTest.class);

    // Test data: List of products to be used in cart operations
    private final List<String> items = List.of(
            "Sauce Labs Backpack",
            "Sauce Labs Bike Light",
            "Sauce Labs Bolt T-Shirt"
    );

    /**
     * Helper method to perform login and navigate to product page.
     * Used by multiple test methods to establish test preconditions.
     *
     * @return ProductPage instance after successful login
     */
    private ProductPage loginAndMoveToProductPage() {
        logger.debug("Performing login and navigating to product page");

        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.login(User.STANDARD.getUsername(), User.STANDARD.getPassword());

        ProductPage productPage = new ProductPage(getDriver());
        logger.debug("Successfully logged in and navigated to product page");
        return productPage;
    }

    /**
     * Helper method to login and add predefined items to cart.
     * Sets up cart state for removal and verification tests.
     *
     * @return ProductPage instance with items added to cart
     */
    private ProductPage addItemsFromProductPage() {
        logger.debug("Adding predefined items to cart");

        ProductPage productPage = loginAndMoveToProductPage();

        for (String item : items) {
            logger.debug("Adding item to cart: {}", item);
            productPage.addItemToCart(item);
        }

        logger.info("Successfully added {} items to cart", items.size());
        return productPage;
    }

    /**
     * Test case: Verify that items can be added to cart successfully.
     * This is a smoke test to ensure basic cart functionality works.
     *
     * Test Steps:
     * 1. Login and add 3 items to cart
     * 2. Verify cart badge shows correct count (3)
     * 3. Navigate to cart page
     * 4. Verify cart page URL
     */
    @Test(groups = "smoke")
    public void addItemsToCart() {
        logger.info("Starting test: addItemsToCart");

        // Add items to cart and verify badge count
        ProductPage productPage = addItemsFromProductPage();
        int badgeCount = productPage.getCartBadgeCount();
        Assert.assertEquals(badgeCount, 3, "Cart badge should show 3 items after adding 3 products");
        logger.info("Cart badge correctly shows {} items", badgeCount);

        // Navigate to cart and verify URL
        productPage.goToCartPage();
        String currentUrl = getDriver().getCurrentUrl();
        Assert.assertEquals(currentUrl, "https://www.saucedemo.com/cart.html",
                "Should be redirected to cart page");
        logger.info("Successfully navigated to cart page: {}", currentUrl);

        logger.info("Test completed: addItemsToCart - PASSED");
    }

    /**
     * Test case: Verify that items can be removed from cart successfully.
     * Tests cart badge updates when items are removed.
     *
     * Test Steps:
     * 1. Login and add 3 items to cart
     * 2. Verify initial cart badge count (3)
     * 3. Remove one item from cart
     * 4. Verify cart badge count updates (2)
     */
    @Test
    public void removeItemsFromCart() {
        logger.info("Starting test: removeItemsFromCart");

        // Add items and verify initial count
        ProductPage productPage = addItemsFromProductPage();
        int initialCount = productPage.getCartBadgeCount();
        Assert.assertEquals(initialCount, 3, "Initial cart should contain 3 items");
        logger.debug("Initial cart count verified: {}", initialCount);

        // Remove one item and verify count updates
        String itemToRemove = "Sauce Labs Backpack";
        logger.debug("Removing item: {}", itemToRemove);
        productPage.removeFromCart(itemToRemove);

        int updatedCount = productPage.getCartBadgeCount();
        Assert.assertEquals(updatedCount, 2, "Cart should contain 2 items after removing 1");
        logger.info("Cart count correctly updated to {} after removal", updatedCount);

        logger.info("Test completed: removeItemsFromCart - PASSED");
    }
}
