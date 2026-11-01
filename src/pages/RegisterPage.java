package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class RegisterPage extends BasePage {

    By male = By.id("gender-male");
    By female = By.id("gender-female");
    By firstName = By.id("FirstName");
    By lastName = By.id("LastName");
    By email = By.id("Email");
    By password = By.id("Password");
    By confirmPassword = By.id("ConfirmPassword");
    By registerButton = By.id("register-button");
    By continueButton = By.xpath("//input[contains(@class,'register-continue-button')]");
    By successMessage = By.xpath("//div[@class='result']");

    By firstNameError = By.xpath("//span[@data-valmsg-for='FirstName']");
    By lastNameError = By.xpath("//span[@data-valmsg-for='LastName']");
    By emailError = By.xpath("//span[@data-valmsg-for='Email']");
    By passwordError = By.xpath("//span[@data-valmsg-for='Password']");
    By confirmPasswordError = By.xpath("//span[@data-valmsg-for='ConfirmPassword']");
    By summaryError = By.xpath("//div[contains(@class,'validation-summary-errors')]");

    public RegisterPage(WebDriver driver) {
        super(driver);
    }

    public void register(String gender, String fName, String lName, String mail, String pwd, String confirmPwd) {
        if (gender.equalsIgnoreCase("Female")) {
            click(female);
        } else {
            click(male);
        }
        type(firstName, fName);
        type(lastName, lName);
        type(email, mail);
        type(password, pwd);
        type(confirmPassword, confirmPwd);
        click(registerButton);
        sleep();          // extra wait after clicking register
    }

    public void clickRegisterButton() {
        click(registerButton);
    }

    public void clickContinue() {
        click(continueButton);
    }

    public String getSuccessMessage() {
        return getText(successMessage);
    }

    public String getFirstNameError() {
        return getText(firstNameError);
    }

    public String getLastNameError() {
        return getText(lastNameError);
    }

    public String getEmailError() {
        return getText(emailError);
    }

    public String getPasswordError() {
        return getText(passwordError);
    }

    public String getConfirmPasswordError() {
        return getText(confirmPasswordError);
    }

    public String getSummaryError() {
        return getText(summaryError);
    }

    public boolean isContinueButtonDisplayed() {
        return driver.findElements(continueButton).size() > 0;
    }
}