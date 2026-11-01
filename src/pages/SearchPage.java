package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.ArrayList;
import java.util.List;

public class SearchPage extends BasePage {

    By resultTitles = By.xpath("//h2[contains(@class,'product-title')]/a");
    By noResultMessage = By.xpath("//div[contains(@class,'search-results')]//strong");
    By warningMessage = By.xpath("//div[contains(@class,'search-results')]");

    public SearchPage(WebDriver driver) {
        super(driver);
    }

    public List<String> getResultTitles() {
        List<String> titles = new ArrayList<>();
        for (WebElement element : driver.findElements(resultTitles)) {
            titles.add(element.getText().trim());
        }
        return titles;
    }

    public String getNoResultMessage() {
        return getText(noResultMessage);
    }

    public String getWarningMessage() {
        return getText(warningMessage);
    }
}