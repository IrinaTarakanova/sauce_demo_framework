package tarakanova.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import tarakanova.utils.WaitUtils;

import java.util.List;

public class ProductPage {

    private WebDriver driver;
    private WaitUtils wait;
    public ProductPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WaitUtils(driver);
    }

    private By addToCartButton = By.cssSelector(".btn_inventory");
    private By cartBadgeNumber = By.cssSelector(".shopping_cart_badge");
    private By removeFromCartButton = By.cssSelector(".btn_secondary");
    private By listOfProducts = By.cssSelector(".inventory_item");
    private By itemName = By.cssSelector(".inventory_item_name");
    private By goToCartButton = By.cssSelector(".shopping_cart_link");


    //adding items in cart by items name
    private WebElement getProductByName(String productName) {
        List<WebElement> products = wait.waitForAllVisible(listOfProducts);

        return products.stream()
                .filter(p -> p.findElement(itemName)
                        .getText().equalsIgnoreCase(productName))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Product not found: " + productName));
    }

    //checking how many items added in cart
    // using List to not create NoSuchElementException
    public int getCartBadgeCount() {
        List<WebElement> badges = driver.findElements(cartBadgeNumber);

        if (badges.isEmpty()) {
            return 0;
        }

        return Integer.parseInt(badges.get(0).getText());
    }

    //adding items in cart
    public void addItemToCart(String productName) {
        WebElement product = getProductByName(productName);
        product.findElement(addToCartButton).click();
    }

    //removing items from cart
    public void removeFromCart(String productName) {
        WebElement product = getProductByName(productName);
        product.findElement(removeFromCartButton).click();
    }
    public CartPage goToCartPage() {
        driver.findElement(goToCartButton).click();
        return new CartPage(driver);
    }

    public boolean isOnProductsPage() {
        return driver.getCurrentUrl().contains("inventory");
    }


}
