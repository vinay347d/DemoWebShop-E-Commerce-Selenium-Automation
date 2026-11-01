package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

public class CheckoutPage extends BasePage {

    By country = By.id("BillingNewAddress_CountryId");
    By city = By.id("BillingNewAddress_City");
    By address = By.id("BillingNewAddress_Address1");
    By zip = By.id("BillingNewAddress_ZipPostalCode");
    By phone = By.id("BillingNewAddress_PhoneNumber");

    By billingContinue = By.xpath("//div[@id='billing-buttons-container']//input[contains(@class,'next-step-button')]");
    By shippingAddressContinue = By.xpath("//div[@id='shipping-buttons-container']//input[contains(@class,'next-step-button')]");
    By shippingContinue = By.xpath("//div[@id='shipping-method-buttons-container']//input[contains(@class,'next-step-button')]");
    By paymentMethodContinue = By.xpath("//div[@id='payment-method-buttons-container']//input[contains(@class,'next-step-button')]");
    By paymentInfoContinue = By.xpath("//div[@id='payment-info-buttons-container']//input[contains(@class,'next-step-button')]");
    By confirmButton = By.xpath("//div[@id='confirm-order-buttons-container']//input[contains(@class,'next-step-button')]");
    By successMessage = By.xpath("//div[contains(@class,'order-completed')]//strong");

    public CheckoutPage(WebDriver driver) {
        super(driver);
    }

    public void fillBillingAddress(String countryName, String cityName, String addressText, String zipCode, String phoneNumber) {
        Select select = new Select(driver.findElement(country));
        select.selectByVisibleText(countryName);
        type(city, cityName);
        type(address, addressText);
        type(zip, zipCode);
        type(phone, phoneNumber);
    }

    public void completeCheckout(String countryName, String cityName, String addressText, String zipCode, String phoneNumber) {
        fillBillingAddress(countryName, cityName, addressText, zipCode, phoneNumber);
        click(billingContinue);
        try {
            click(shippingAddressContinue);
        } catch (Exception e) {
        }
        click(shippingContinue);
        click(paymentMethodContinue);
        click(paymentInfoContinue);
        click(confirmButton);
    }

    public String getSuccessMessage() {
        return getText(successMessage);
    }
}