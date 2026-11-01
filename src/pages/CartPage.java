package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.ArrayList;
import java.util.List;

public class CartPage extends BasePage {

    By itemNames = By.xpath("//table[contains(@class,'cart')]//a[contains(@class,'product-name')]");
    By quantityBox = By.xpath("//table[contains(@class,'cart')]//input[contains(@class,'qty-input')]");
    By unitPrice = By.xpath("//table[contains(@class,'cart')]//span[contains(@class,'product-unit-price')]");
    By subtotal = By.xpath("//table[contains(@class,'cart')]//span[contains(@class,'product-subtotal')]");
    By removeCheckbox = By.xpath("//input[@name='removefromcart']");
    By updateButton = By.xpath("//input[@name='updatecart']");
    By emptyMessage = By.xpath("//div[contains(@class,'order-summary-content')]");
    By termsCheckbox = By.id("termsofservice");
    By checkoutButton = By.id("checkout");
    By termsWarning = By.id("terms-of-service-warning-box");

    public CartPage(WebDriver driver) {
        super(driver);
    }

    public void open() {
        openPage("cart");
    }

    public List<String> getItemNames() {
        List<String> names = new ArrayList<>();
        for (WebElement element : driver.findElements(itemNames)) {
            names.add(element.getText().trim());
        }
        return names;
    }

    public String getQuantity() {
        return driver.findElement(quantityBox).getAttribute("value");
    }

    public String getUnitPrice() {
        return getText(unitPrice);
    }

    public String getSubtotal() {
        return getText(subtotal);
    }

    public void updateQuantity(int qty) {
        type(quantityBox, String.valueOf(qty));
        click(updateButton);
    }

    public void removeFirstItem() {
        click(removeCheckbox);
        click(updateButton);
    }

    public String getEmptyMessage() {
        return getText(emptyMessage);
    }

    public void acceptTerms() {
        click(termsCheckbox);
    }

    public void clickCheckout() {
        click(checkoutButton);
    }

    public String getTermsWarning() {
        return getText(termsWarning);
    }
}