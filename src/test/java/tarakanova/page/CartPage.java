package tarakanova.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * CartPage class represents the shopping cart page of the SauceDemo application.
 * Provides methods for verifying cart contents and navigating to checkout.
 *
 * @author Irina Tarakanova
 * @version 1.0
 */
public class CartPage {
    private static final Logger logger = LoggerFactory.getLogger(CartPage.class);
    private WebDriver driver;

    // WebElement declarations using PageFactory
    @FindBy(css = ".cart_item")
    private List<WebElement> cartItems;

    @FindBy(id = "checkout")
    private WebElement checkoutButton;

    // Locator for item names within cart items
    private By itemName = By.cssSelector(".inventory_item_name");

    /**
     * Constructor for CartPage.
     * Initializes WebDriver and PageFactory elements.
     *
     * @param driver WebDriver instance
     */
    public CartPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        logger.debug("CartPage initialized");
    }

    /**
     * Checks if a specific product is present in the cart.
     * Uses stream API to search through cart items by product name (case-insensitive).
     *
     * @param productName Name of the product to check
     * @return true if product is found in cart, false otherwise
     */
    public boolean isProductInCart(String productName) {
        logger.debug("Checking if product '{}' is in cart", productName);
        boolean isPresent = cartItems.stream().anyMatch(item -> item.findElement(itemName)
                .getText().equalsIgnoreCase(productName));

        logger.info("Product '{}' {} in cart", productName, isPresent ? "found" : "not found");
        return isPresent;
    }

    /**
     * Gets the count of items currently in the cart.
     *
     * @return Number of items in cart
     */
    public int getCartItemsCount() {
        int count = cartItems.size();
        logger.debug("Cart contains {} items", count);
        return count;
    }

    /**
     * Clicks the checkout button to proceed to checkout page.
     *
     * @return CheckoutPage instance for checkout interactions
     */
    public CheckoutPage clickCheckoutButton() {
        logger.info("Clicking checkout button to proceed to checkout");
        checkoutButton.click();
        logger.info("Successfully navigated to checkout page");
        return new CheckoutPage(driver);
    }
}
