package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.ArrayList;
import java.util.List;

public class ComparePage extends BasePage {

    By productNames = By.xpath("//tr[contains(@class,'product-name')]//a");

    public ComparePage(WebDriver driver) {
        super(driver);
    }

    public List<String> getProductNames() {
        List<String> names = new ArrayList<>();
        for (WebElement element : driver.findElements(productNames)) {
            names.add(element.getText().trim());
        }
        return names;
    }
}