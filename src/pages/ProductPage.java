package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ProductPage extends BasePage {

    By name = By.xpath("//div[contains(@class,'product-name')]/h1");
    By price = By.xpath("//div[contains(@class,'product-price')]/span");
    By description = By.xpath("//div[contains(@class,'short-description')]");
    By availability = By.xpath("//div[contains(@class,'stock')]//span[contains(@class,'value')]");
    By addToCartButton = By.xpath("//input[contains(@class,'add-to-cart-button')]");
    By addToCompareButton = By.xpath("//input[contains(@class,'add-to-compare-list-button')]");
    By notification = By.xpath("//div[@id='bar-notification']//p");

    public ProductPage(WebDriver driver) {
        super(driver);
    }

    public void open(String slug) {
        openPage(slug);
    }

    public String getName() {
        return getText(name);
    }

    public String getPrice() {
        return getText(price);
    }

    public String getDescription() {
        return getText(description);
    }

    public String getAvailability() {
        return getText(availability);
    }

    public void addToCart() {
        click(addToCartButton);
    }

    public void addToCompare() {
        click(addToCompareButton);
    }

    public String getNotification() {
        return getText(notification);
    }
}