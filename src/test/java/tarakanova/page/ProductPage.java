package tarakanova.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tarakanova.utils.WaitUtils;

import java.util.List;

/**
 * ProductPage class represents the products page of the SauceDemo application.
 * Provides methods for interacting with products, managing cart items,
 * and navigating to the cart page.
 *
 * @author Irina Tarakanova
 * @version 1.0
 */
public class ProductPage {
    private static final Logger logger = LoggerFactory.getLogger(ProductPage.class);
    private WebDriver driver;
    private WaitUtils wait;

    // Element locators
    private By addToCartButton = By.cssSelector(".btn_inventory");
    private By cartBadgeNumber = By.cssSelector(".shopping_cart_badge");
    private By removeFromCartButton = By.cssSelector(".btn_secondary");
    private By listOfProducts = By.cssSelector(".inventory_item");
    private By itemName = By.cssSelector(".inventory_item_name");
    private By goToCartButton = By.cssSelector(".shopping_cart_link");

    /**
     * Constructor for ProductPage.
     * Initializes WebDriver and WaitUtils.
     *
     * @param driver WebDriver instance
     */
    public ProductPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WaitUtils(driver);
        logger.debug("ProductPage initialized");
    }

    /**
     * Finds a product element by its name from the products list.
     * Uses stream API to filter products by name (case-insensitive).
     *
     * @param productName Name of the product to find
     * @return WebElement of the found product
     * @throws RuntimeException if product is not found
     */
    private WebElement getProductByName(String productName) {
        logger.debug("Searching for product: {}", productName);

        List<WebElement> products = wait.waitForAllVisible(listOfProducts);
        logger.debug("Found {} products on page", products.size());

        WebElement product = products.stream()
                .filter(p -> p.findElement(itemName)
                        .getText().equalsIgnoreCase(productName))
                .findFirst()
                .orElseThrow(() -> {
                    logger.error("Product not found: {}", productName);
                    return new RuntimeException("Product not found: " + productName);
                });

        logger.debug("Product '{}' found successfully", productName);
        return product;
    }

    /**
     * Gets the current cart badge count showing number of items in cart.
     * Returns 0 if cart is empty (no badge displayed).
     *
     * @return Number of items in cart (0 if empty)
     */
    public int getCartBadgeCount() {
        logger.debug("Checking cart badge count");

        // Use findElements to avoid NoSuchElementException if badge is not present
        List<WebElement> badges = driver.findElements(cartBadgeNumber);

        if (badges.isEmpty()) {
            logger.debug("Cart is empty - no badge displayed");
            return 0;
        }

        int count = Integer.parseInt(badges.get(0).getText());
        logger.debug("Cart badge shows {} items", count);
        return count;
    }

    /**
     * Adds a product to the cart by product name.
     * Finds the product and clicks the "Add to Cart" button.
     *
     * @param productName Name of the product to add to cart
     */
    public void addItemToCart(String productName) {
        logger.info("Adding product to cart: {}", productName);

        WebElement product = getProductByName(productName);
        product.findElement(addToCartButton).click();

        logger.info("Product '{}' added to cart successfully", productName);
    }

    /**
     * Removes a product from the cart by product name.
     * Finds the product and clicks the "Remove" button.
     *
     * @param productName Name of the product to remove from cart
     */
    public void removeFromCart(String productName) {
        logger.info("Removing product from cart: {}", productName);

        WebElement product = getProductByName(productName);
        product.findElement(removeFromCartButton).click();

        logger.info("Product '{}' removed from cart successfully", productName);
    }

    /**
     * Navigates to the cart page by clicking the cart icon.
     *
     * @return CartPage instance for cart page interactions
     */
    public CartPage goToCartPage() {
        logger.info("Navigating to cart page");

        driver.findElement(goToCartButton).click();

        logger.info("Successfully navigated to cart page");
        return new CartPage(driver);
    }

    /**
     * Checks if the current page is the products page.
     * Verifies by checking if URL contains "inventory".
     *
     * @return true if on products page, false otherwise
     */
    public boolean isOnProductsPage() {
        String currentUrl = driver.getCurrentUrl();
        boolean isOnProductsPage = currentUrl.contains("inventory");

        logger.debug("Current URL: {}, Is on products page: {}", currentUrl, isOnProductsPage);
        return isOnProductsPage;
    }
}
