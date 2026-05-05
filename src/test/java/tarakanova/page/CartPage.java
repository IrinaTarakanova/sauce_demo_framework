package tarakanova.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class CartPage {
    private WebDriver driver;
    public CartPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(css = ".cart_item")
    private List<WebElement> cartItems;

    @FindBy(xpath = "//button[text()='Remove']")
    private WebElement removeButton;

    @FindBy(id ="checkout")
    private WebElement checkoutButton;

    private By itemName = By.cssSelector(".inventory_item_name");

    public boolean isProductInCart(String productName) {
        return cartItems.stream().anyMatch(item -> item.findElement(itemName)
                .getText().equalsIgnoreCase(productName));
    }

    public int getCartItemsCount() {
        return cartItems.size();
    }

    public CheckoutPage clickCheckoutButton()  {
        checkoutButton.click();
        return new CheckoutPage(driver);
    }

}
