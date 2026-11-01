package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage extends BasePage {

    By registerLink = By.className("ico-register");
    By loginLink = By.className("ico-login");
    By logoutLink = By.className("ico-logout");
    By accountLink = By.xpath("//a[contains(@class,'account')]");
    By searchBox = By.id("small-searchterms");
    By searchButton = By.xpath("//input[contains(@class,'search-box-button')]");
    By cartCount = By.className("cart-qty");
    By wishlistCount = By.className("wishlist-qty");

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public void clickRegister() {
        click(registerLink);
    }

    public void clickLogin() {
        click(loginLink);
    }

    public void clickLogout() {
        click(logoutLink);
    }

    public boolean isLogoutVisible() {
        return isVisible(logoutLink);
    }

    public boolean isLoginVisible() {
        return isVisible(loginLink);
    }

    public boolean isRegisterVisible() {
        return isVisible(registerLink);
    }

    public String getAccountEmail() {
        return getText(accountLink);
    }

    public void search(String text) {
        type(searchBox, text);
        click(searchButton);
    }

    public String getCartCount() {
        return getText(cartCount);
    }

    public String getWishlistCount() {
        return getText(wishlistCount);
    }
}