package tarakanova.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tarakanova.page.LoginPage;
import tarakanova.page.ProductPage;
import tarakanova.utils.User;

import java.util.List;

/**
 * HelperBaseTest provides common helper methods for test classes that require
 * pre-configured test state (logged in user with items in cart).
 * Extends BaseTest to inherit WebDriver setup and teardown functionality.
 *
 * This class is designed to reduce code duplication across test classes
 * that need similar setup procedures.
 *
 * @author Irina Tarakanova
 * @version 1.0
 */
public class HelperBaseTest extends BaseTest {
    private static final Logger logger = LoggerFactory.getLogger(HelperBaseTest.class);

    // Test data: Standard set of products used across multiple tests
    protected final List<String> items = List.of(
            "Sauce Labs Backpack",
            "Sauce Labs Bike Light",
            "Sauce Labs Bolt T-Shirt"
    );

    /**
     * Helper method to perform login and navigate to product page.
     * Used as a foundation for tests that require authenticated state.
     *
     * @return ProductPage instance after successful login
     */
    protected ProductPage loginAndMoveToProductPage() {
        logger.debug("Performing login and navigating to product page");

        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.login(User.STANDARD.getUsername(), User.STANDARD.getPassword());

        ProductPage productPage = new ProductPage(getDriver());
        logger.debug("Successfully logged in and navigated to product page");
        return productPage;
    }

    /**
     * Helper method to login and add standard test items to cart.
     * Sets up a consistent cart state for tests that need items in cart.
     *
     * @return ProductPage instance with standard items added to cart
     */
    protected ProductPage addItemsFromProductPage() {
        logger.debug("Setting up cart with standard test items");

        ProductPage productPage = loginAndMoveToProductPage();

        for (String item : items) {
            logger.debug("Adding item to cart: {}", item);
            productPage.addItemToCart(item);
        }

        logger.info("Successfully added {} standard items to cart", items.size());
        return productPage;
    }
}
