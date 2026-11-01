package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CategoryPage extends BasePage {

    By heading = By.xpath("//div[contains(@class,'page-title')]/h1");

    public CategoryPage(WebDriver driver) {
        super(driver);
    }

    public void open(String slug) {
        openPage(slug);
    }

    public String getHeading() {
        return getText(heading);
    }
}