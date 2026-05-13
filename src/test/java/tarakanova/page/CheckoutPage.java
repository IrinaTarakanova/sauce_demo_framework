package tarakanova.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import tarakanova.utils.WaitUtils;

import java.util.List;

/**
 * CheckoutPage class represents the checkout page of the SauceDemo application.
 * Provides methods for filling checkout information, verifying cart contents,
 * calculating prices, and completing checkout process.
 *
 * @author Irina Tarakanova
 * @version 1.0
 */
public class CheckoutPage {
    private static final Logger logger = LoggerFactory.getLogger(CheckoutPage.class);
    private WebDriver driver;
    private WaitUtils wait;

    /**
     * Constructor for CheckoutPage.
     * Initializes WebDriver and PageFactory elements.
     * @param driver WebDriver instance
     */
    public CheckoutPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WaitUtils(driver);
        PageFactory.initElements(driver, this);
        logger.debug("CheckoutPage initialized");
    }

    // WebElement declarations using PageFactory
    @FindBy(id="first-name")
    private WebElement firstNameField;

    @FindBy(id="last-name")
    private WebElement lastNameField;

    @FindBy(id="postal-code")
    private WebElement postalCodeField;

    @FindBy(id="continue")
    private WebElement continueButton;

    @FindBy(css=".cart_item_label")
    private List<WebElement> cartItems;

    @FindBy(css=".inventory_item_price")
    private List<WebElement> inventoryItemsPrice;

    @FindBy(css=".summary_subtotal_label")
    private WebElement itemTotalPrice;

    @FindBy(css="h3[data-test='error']")
    private WebElement errorMessage;

    @FindBy(id="finish")
    private WebElement finishButton;

    @FindBy(xpath="//h2[@class='complete-header']")
    private WebElement completeHeader;

    // Locators
    private By itemsName = By.cssSelector(".inventory_item_name");

    /**
     * Fills out the checkout information form and clicks continue.
     * @param firstName Customer's first name
     * @param lastName Customer's last name
     * @param postalCode Customer's postal code
     */
    public void fillCheckoutInformation(String firstName, String lastName, String postalCode) {
        logger.info("Filling checkout information - FirstName: {}, LastName: {}, PostalCode: {}",
                   firstName, lastName, postalCode);

        wait.waitForVisible(firstNameField).sendKeys(firstName);
        logger.debug("Entered first name: {}", firstName);

        lastNameField.sendKeys(lastName);
        logger.debug("Entered last name: {}", lastName);

        postalCodeField.sendKeys(postalCode);
        logger.debug("Entered postal code: {}", postalCode);

        wait.waitForClickable(continueButton).click();
        logger.info("Clicked continue button, proceeding to checkout overview");
    }

    /**
     * Checks if a specific product is present in the cart.
     * @param productName Name of the product to check
     * @return true if product is found, false otherwise
     */
    public boolean isProductInCart(String productName) {
        logger.debug("Checking if product '{}' is in cart", productName);
        boolean isPresent = cartItems.stream().anyMatch(item -> item.findElement(itemsName)
                .getText().equalsIgnoreCase(productName));

        logger.info("Product '{}' {} in cart", productName, isPresent ? "found" : "not found");
        return isPresent;
    }

    /**
     * Gets the count of items currently in the cart.
     * @return Number of items in cart
     */
    public int getCartItemsCount() {
        int count = cartItems.size();
        logger.debug("Cart contains {} items", count);
        return count;
    }

    /**
     * Calculates the total price of all items in the cart.
     * @return Sum of all item prices
     */
    public double getAddedTotalPrice() {
        logger.debug("Calculating total price of items in cart");
        double total = inventoryItemsPrice.stream()
                .map(e -> e.getText().replace("$", ""))
                .mapToDouble(Double::parseDouble)
                .sum();

        logger.info("Calculated total price: ${}", total);
        return total;
    }

    /**
     * Gets the item total price displayed on the checkout page.
     * @return Item total price from the page
     */
    public double getItemTotalPriceFromPage() {
        logger.debug("Getting item total price from checkout page");
        String text = itemTotalPrice.getText();
        // Extract only numbers and dot from the text
        String value = text.replaceAll("[^0-9.]", "");
        double price = Double.parseDouble(value);

        logger.debug("Item total price from page: ${}", price);
        return price;
    }

    /**
     * Gets the error message displayed on the checkout page.
     * @return Error message text
     */
    public String getErrorMessage() {
        String errorMsg = errorMessage.getText();
        logger.warn("Checkout error message: {}", errorMsg);
        return errorMsg;
    }

    /**
     * Clicks the finish button to complete the checkout process.
     */
    public void clickFinishButton() {
        logger.info("Clicking finish button to complete checkout");
        finishButton.click();
        logger.info("Checkout completion initiated");
    }

    /**
     * Gets the confirmation message displayed after successful checkout.
     * @return Confirmation message text
     */
    public String getConfirmationMessage() {
        logger.debug("Waiting for checkout confirmation message");
        String message = wait.waitForVisible(completeHeader).getText();
        logger.info("Checkout confirmation message: {}", message);
        return message;
    }
}
