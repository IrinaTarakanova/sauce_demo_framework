package tarakanova.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import tarakanova.utils.WaitUtils;

import java.util.List;

public class CheckoutPage {
    private WebDriver driver;
    private WaitUtils wait;
    public CheckoutPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WaitUtils(driver);
        PageFactory.initElements(driver, this);
    }

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


    private By itemsName = By.cssSelector(".inventory_item_name");

    public void fillCheckoutInformation(String firstName, String lastName, String postalCode) {
        wait.waitForVisible(firstNameField).sendKeys(firstName);
        lastNameField.sendKeys(lastName);
        postalCodeField.sendKeys(postalCode);
        wait.waitForClickable(continueButton).click();
    }
    public boolean isProductInCart(String productName) {
        return cartItems.stream().anyMatch(item -> item.findElement(itemsName)
                .getText().equalsIgnoreCase(productName));
    }

    public int getCartItemsCount() {
        return cartItems.size();
    }
    public double getAddedTotalPrice() {
        return inventoryItemsPrice.stream()
                .map(e -> e.getText().replace("$", ""))
                .mapToDouble(Double::parseDouble)
                .sum();
    }
    public double getItemTotalPriceFromPage() {
        String text = itemTotalPrice.getText();
        // keep only numbers and dot
        String value = text.replaceAll("[^0-9.]", "");
        return Double.parseDouble(value);
    }
    public boolean isErrorMessageDisplayed() {
        return errorMessage.isDisplayed();
    }

    public String getErrorMessage() {
        return errorMessage.getText();
    }

}
