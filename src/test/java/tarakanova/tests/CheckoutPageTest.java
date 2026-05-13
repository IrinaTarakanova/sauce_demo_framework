package tarakanova.tests;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import tarakanova.base.HelperBaseTest;
import tarakanova.page.CartPage;
import tarakanova.page.CheckoutPage;
import tarakanova.page.ProductPage;
import tarakanova.utils.JsonReader;

import java.util.Map;

/**
 * CheckoutPageTest class contains test cases for checkout page functionality.
 * Tests include form filling, validation, price calculations, and order completion.
 *
 * @author Irina Tarakanova
 * @version 1.0
 */
public class CheckoutPageTest extends HelperBaseTest {
    private static final Logger logger = LoggerFactory.getLogger(CheckoutPageTest.class);

    /**
     * Helper method to navigate to checkout page.
     * Sets up test preconditions by adding items to cart and navigating through cart.
     *
     * @return CheckoutPage instance ready for testing
     */
    private CheckoutPage goToCheckoutPage() {
        logger.debug("Navigating to checkout page");

        ProductPage productPage = addItemsFromProductPage();
        CartPage cartPage = productPage.goToCartPage();
        CheckoutPage checkoutPage = cartPage.clickCheckoutButton();

        logger.debug("Successfully navigated to checkout page");
        return checkoutPage;
    }

    /**
     * Test case: Verify that checkout form can be filled successfully.
     * This is a smoke test to ensure basic checkout navigation works.
     *
     * Test Steps:
     * 1. Navigate to checkout page
     * 2. Fill checkout information form
     * 3. Verify navigation to checkout step two
     */
    @Test(groups = "smoke")
    public void shouldFillTheFormAndClickCheckoutButton() {
        logger.info("Starting test: shouldFillTheFormAndClickCheckoutButton");

        CheckoutPage checkoutPage = goToCheckoutPage();
        logger.debug("Filling checkout form with test data");
        checkoutPage.fillCheckoutInformation("Anna", "Mane", "12345");

        boolean isOnCheckoutStepTwo = getDriver().getCurrentUrl().contains("checkout-step-two");
        Assert.assertTrue(isOnCheckoutStepTwo, "Should navigate to checkout step two after filling form");
        logger.info("Successfully navigated to checkout step two");

        logger.info("Test completed: shouldFillTheFormAndClickCheckoutButton - PASSED");
    }

    /**
     * Test case: Verify complete checkout flow including price calculations and order completion.
     * This is a comprehensive smoke test covering the entire checkout process.
     *
     * Test Steps:
     * 1. Navigate to checkout and fill form
     * 2. Verify navigation to step two
     * 3. Verify all cart items are present
     * 4. Verify cart item count
     * 5. Verify price calculations match
     * 6. Complete checkout and verify confirmation
     */
    @Test(groups = "smoke")
    public void totalItemsCheckoutAndPriceClickFinish() {
        logger.info("Starting test: totalItemsCheckoutAndPriceClickFinish");

        CheckoutPage checkoutPage = goToCheckoutPage();
        logger.debug("Filling checkout form with test data");
        checkoutPage.fillCheckoutInformation("Anna", "Mane", "12345");

        // Verify navigation to step two
        boolean isOnCheckoutStepTwo = getDriver().getCurrentUrl().contains("checkout-step-two");
        Assert.assertTrue(isOnCheckoutStepTwo, "Should navigate to checkout step two");
        logger.debug("Confirmed navigation to checkout step two");

        // Verify cart contents
        logger.debug("Verifying cart items are present in checkout");
        for (String item : items) {
            boolean itemPresent = checkoutPage.isProductInCart(item);
            Assert.assertTrue(itemPresent, "Item not found in checkout: " + item);
            logger.debug("Verified item in checkout: {}", item);
        }

        int cartItemCount = checkoutPage.getCartItemsCount();
        Assert.assertEquals(cartItemCount, 3, "Checkout should show 3 items");
        logger.info("Verified {} items in checkout", cartItemCount);

        // Verify price calculations
        logger.debug("Verifying price calculations");
        double calculatedTotal = checkoutPage.getAddedTotalPrice();
        double pageTotal = checkoutPage.getItemTotalPriceFromPage();

        Assert.assertEquals(pageTotal, calculatedTotal, 0.01, "Calculated total should match page total");
        logger.info("Price verification successful - Calculated: ${}, Page: ${}", calculatedTotal, pageTotal);

        // Complete checkout
        logger.debug("Completing checkout process");
        checkoutPage.clickFinishButton();

        String confirmationMessage = checkoutPage.getConfirmationMessage();
        Assert.assertEquals(confirmationMessage, "Thank you for your order!",
                "Should display order confirmation message");
        logger.info("Checkout completed successfully with confirmation: {}", confirmationMessage);

        logger.info("Test completed: totalItemsCheckoutAndPriceClickFinish - PASSED");
    }

    /**
     * Test case: Verify checkout form validation with invalid data.
     * Uses data-driven testing with JSON data provider for multiple validation scenarios.
     *
     * Test Steps:
     * 1. Navigate to checkout page
     * 2. Fill form with invalid data from data provider
     * 3. Verify appropriate error message is displayed
     */
    @Test(dataProvider = "checkoutValidationData", groups = "negative")
    public void shouldShowErrorForInvalidCheckoutData(Map<String, String> data) {
        logger.info("Starting validation test with data: {}", data);

        CheckoutPage checkoutPage = goToCheckoutPage();

        logger.debug("Filling checkout form with validation test data");
        checkoutPage.fillCheckoutInformation(
                data.get("firstName"),
                data.get("lastName"),
                data.get("postalCode")
        );

        String actualErrorMessage = checkoutPage.getErrorMessage();
        String expectedErrorMessage = data.get("expectedErrorMessage");

        Assert.assertEquals(actualErrorMessage, expectedErrorMessage,
                "Error message should match expected validation message");
        logger.info("Validation test passed - Error message: {}", actualErrorMessage);

        logger.info("Test completed: shouldShowErrorForInvalidCheckoutData - PASSED");
    }

    /**
     * Data provider for checkout validation test cases.
     * Reads test data from JSON file for data-driven testing.
     *
     * @return Object array containing test data maps
     */
    @DataProvider(name = "checkoutValidationData")
    public Object[][] checkoutValidationData() {
        logger.debug("Loading checkout validation data from JSON");
        Object[][] data = JsonReader.getCheckoutValidationData();
        logger.debug("Loaded {} validation test cases", data.length);
        return data;
    }
}

/*
 * Commented out individual validation tests - replaced with data-driven approach above
 * These tests are now covered by the shouldShowErrorForInvalidCheckoutData method
 * which uses JSON data provider for better maintainability and scalability.
 *
 * @Test(dataProvider = "checkoutValidationData", groups = "negative")
 * public void emptyFirstNameShouldShowError() { ... }
 *
 * @Test(dataProvider = "checkoutValidationData", groups = "negative")
 * public void emptyLastNameShouldShowError() { ... }
 *
 * @Test(dataProvider = "checkoutValidationData", groups = "negative")
 * public void emptyPostalCodeShouldShowError() { ... }
 *
 * @Test(dataProvider = "checkoutValidationData", groups = "negative")
 * public void allFieldsEmptyShouldShowFirstNameError() { ... }
 */
