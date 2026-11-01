package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage {

    By email = By.id("Email");
    By password = By.id("Password");
    By loginButton = By.xpath("//input[contains(@class,'login-button')]");
    By errorMessage = By.xpath("//div[contains(@class,'validation-summary-errors')]");
    By heading = By.xpath("//div[contains(@class,'page-title')]/h1");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public void login(String mail, String pwd) {
        type(email, mail);
        type(password, pwd);
        click(loginButton);
    }

    public void clickLoginButton() {
        click(loginButton);
    }

    public String getErrorMessage() {
        return getText(errorMessage);
    }

    public String getHeading() {
        return getText(heading);
    }
}