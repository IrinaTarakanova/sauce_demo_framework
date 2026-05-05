package tarakanova.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class CheckoutPage {
    private WebDriver driver;
    public CheckoutPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(id="first-name")
    private WebElement firstName;
    @FindBy(id="last-name")
    private WebElement lastName;
    @FindBy(id="postal-code")
    private WebElement postalCode;
    @FindBy(id="continue")
    private WebElement continueButton;
    @FindBy(css=".cart_item_label")
    private List<WebElement> cartItems;
    @FindBy(css=".inventory_item_price")
    private List<WebElement> inventoryItemsPrice;
    @FindBy(css=".summary_subtotal_label")
    private WebElement itemTotalPrice;


    private By itemsName = By.cssSelector(".inventory_item_name");

    public void fillTheForm(String firstName, String lastName, String postalCode) {
        this.firstName.sendKeys(firstName);
        this.lastName.sendKeys(lastName);
        this.postalCode.sendKeys(postalCode);
        this.continueButton.click();
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

}
