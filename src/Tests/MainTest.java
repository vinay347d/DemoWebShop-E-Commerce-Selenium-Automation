package Tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.*;
import pages.*;

import java.util.List;

public class MainTest {

    public static WebDriver driver;
    String PASSWORD = "123456";
    String LAPTOP = "141-inch-laptop";

    @BeforeMethod
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        driver = new ChromeDriver(options);
        driver.get(BasePage.URL);
    }

    @AfterMethod
    public void tearDown() {

        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }

    int emailNumber = 500;   // starting number

    String newEmail() {
        emailNumber--;
        return "vinaydudyala" + emailNumber + "@gmail.com";
    }

    String registerNewUser() {
        String email = newEmail();
        new HomePage(driver).clickRegister();
        RegisterPage register = new RegisterPage(driver);
        register.register("Male", "Vinay", "Dudyala", email, PASSWORD, PASSWORD);
        register.clickContinue();
        return email;
    }

    void addLaptopToCart() {
        ProductPage product = new ProductPage(driver);
        product.open(LAPTOP);
        product.addToCart();
        product.getNotification();
    }

    // ===================== REGISTRATION =====================

    @Test(priority = 1)
    public void tc01_validRegistration() {
        new HomePage(driver).clickRegister();
        RegisterPage register = new RegisterPage(driver);
        register.register("Male", "Vinay", "Dudyala", newEmail(), PASSWORD, PASSWORD);
        Assert.assertEquals(register.getSuccessMessage(), "Your registration completed");
    }

    @Test(priority = 2)
    public void tc02_mandatoryFields() {
        new HomePage(driver).clickRegister();
        RegisterPage register = new RegisterPage(driver);
        register.clickRegisterButton();
        Assert.assertEquals(register.getFirstNameError(), "First name is required.");
        Assert.assertEquals(register.getLastNameError(), "Last name is required.");
        Assert.assertEquals(register.getEmailError(), "Email is required.");
        Assert.assertEquals(register.getPasswordError(), "Password is required.");
    }

    @Test(priority = 3)
    public void tc03_duplicateEmail() {
        String email = registerNewUser();
        HomePage home = new HomePage(driver);
        home.clickLogout();
        home.clickRegister();
        RegisterPage register = new RegisterPage(driver);
        register.register("Male", "Vinay", "Dudyala", email, PASSWORD, PASSWORD);
        Assert.assertTrue(register.getSummaryError().contains("The specified email already exists"));
    }

    @Test(priority = 4)
    public void tc04_passwordMismatch() {
        new HomePage(driver).clickRegister();
        RegisterPage register = new RegisterPage(driver);
        register.register("Male", "Vinay", "Dudyala", newEmail(), PASSWORD, "65456786367");
        Assert.assertEquals(register.getConfirmPasswordError(),
                "The password and confirmation password do not match.");
    }

    @Test(priority = 5)
    public void tc05_shortPassword() {
        new HomePage(driver).clickRegister();
        RegisterPage register = new RegisterPage(driver);
        register.register("Male", "Vinay", "Dudyala", newEmail(), "12345", "12345");
        Assert.assertEquals(register.getPasswordError(),
                "The password should have at least 6 characters.");
    }

    @DataProvider(name = "badEmails")
    public Object[][] badEmails() {
        return new Object[][]{{"vinay"}, {"vinaydudyala@"}, {"vinaydudyala@google"}};
    }

    @Test(priority = 6, dataProvider = "badEmails")
    public void tc06_invalidEmailFormat(String badEmail) {
        new HomePage(driver).clickRegister();
        RegisterPage register = new RegisterPage(driver);
        register.register("Male", "Vinay", "Dudyala", badEmail, PASSWORD, PASSWORD);
        Assert.assertEquals(register.getEmailError(), "Wrong email");
    }

    // ===================== LOGIN =====================

    @Test(priority = 7)
    public void tc07_validLogin() {
        String email = registerNewUser();
        HomePage home = new HomePage(driver);
        home.clickLogout();
        home.clickLogin();
        new LoginPage(driver).login(email, PASSWORD);
        Assert.assertTrue(home.isLogoutVisible());
        Assert.assertEquals(home.getAccountEmail(), email);
    }

    @DataProvider(name = "wrongLogins")
    public Object[][] wrongLogins() {
        return new Object[][]{
                {"ViratKohli18@gmail.com", "kohliiiii"},
                {"lamine34@gmail.com", "Football"},
                {"JackieChan@gmail.com", "Kungfuu"}
        };
    }

    @Test(priority = 8, dataProvider = "wrongLogins")
    public void tc08_invalidLogin(String email, String password) {
        new HomePage(driver).clickLogin();
        LoginPage login = new LoginPage(driver);
        login.login(email, password);
        Assert.assertTrue(login.getErrorMessage().contains("Login was unsuccessful"));
    }

    @Test(priority = 9)
    public void tc09_emptyCredentials() {
        new HomePage(driver).clickLogin();
        LoginPage login = new LoginPage(driver);
        login.clickLoginButton();
        Assert.assertTrue(login.getErrorMessage().contains("Login was unsuccessful"));
    }

    @Test(priority = 10)
    public void tc10_wrongPasswordForRegisteredUser() {
        String email = registerNewUser();
        HomePage home = new HomePage(driver);
        home.clickLogout();
        home.clickLogin();
        LoginPage login = new LoginPage(driver);
        login.login(email, "YAAAHHHHOOOOO!!!!");
        Assert.assertTrue(login.getErrorMessage().contains("The credentials provided are incorrect"));
    }

    @Test(priority = 11)
    public void tc11_loginPageHeading() {
        new HomePage(driver).clickLogin();
        Assert.assertEquals(new LoginPage(driver).getHeading(), "Welcome, Please Sign In!");
    }

    // ===================== SEARCH =====================

    @DataProvider(name = "searchWords")
    public Object[][] searchWords() {
        return new Object[][]{{"laptop"}, {"fiction"}, {"smartphone"}};
    }

    @Test(priority = 12, dataProvider = "searchWords")
    public void tc12_validSearch(String word) {
        new HomePage(driver).search(word);
        List<String> titles = new SearchPage(driver).getResultTitles();
        Assert.assertTrue(titles.size() > 0);
    }

    @Test(priority = 13)
    public void tc13_invalidSearch() {
        new HomePage(driver).search("Virat Kohli");
        Assert.assertEquals(new SearchPage(driver).getNoResultMessage(),
                "No products were found that matched your criteria.");
    }

    @Test(priority = 14)
    public void tc14_shortSearchWord() {
        new HomePage(driver).search("ab");
        Assert.assertTrue(new SearchPage(driver).getWarningMessage()
                .contains("Search term minimum length is 3 characters"));
    }

    // ===================== CATEGORIES =====================

    @DataProvider(name = "categories")
    public Object[][] categories() {
        return new Object[][]{
                {"books", "Books"},
                {"computers", "Computers"},
                {"electronics", "Electronics"},
                {"apparel-shoes", "Apparel & Shoes"}
        };
    }

    @Test(priority = 15, dataProvider = "categories")
    public void tc15_categoryOpens(String slug, String expectedHeading) {
        CategoryPage category = new CategoryPage(driver);
        category.open(slug);
        Assert.assertEquals(category.getHeading(), expectedHeading);
    }

    // ===================== PRODUCT DETAILS =====================

    @Test(priority = 16)
    public void tc16_productName() {
        ProductPage product = new ProductPage(driver);
        product.open(LAPTOP);
        Assert.assertEquals(product.getName(), "14.1-inch Laptop");
    }

    @Test(priority = 17)
    public void tc17_productPrice() {
        ProductPage product = new ProductPage(driver);
        product.open(LAPTOP);
        Assert.assertFalse(product.getPrice().isEmpty());
    }

    @Test(priority = 18)
    public void tc18_productDescription() {
        ProductPage product = new ProductPage(driver);
        product.open(LAPTOP);
        Assert.assertFalse(product.getDescription().isEmpty());
    }

    @Test(priority = 19)
    public void tc19_productAvailability() {
        ProductPage product = new ProductPage(driver);
        product.open(LAPTOP);
        Assert.assertEquals(product.getAvailability(), "In stock");
    }

    // ===================== SHOPPING CART =====================

    @Test(priority = 20)
    public void tc20_addProductToCart() {
        ProductPage product = new ProductPage(driver);
        product.open(LAPTOP);
        product.addToCart();
        Assert.assertTrue(product.getNotification().contains("added to your shopping cart"));
        Assert.assertEquals(new HomePage(driver).getCartCount(), "(1)");
    }

    @Test(priority = 21)
    public void tc21_updateQuantity() {
        addLaptopToCart();
        CartPage cart = new CartPage(driver);
        cart.open();
        cart.updateQuantity(2);
        Assert.assertEquals(cart.getQuantity(), "2");
    }

    @Test(priority = 22)
    public void tc22_removeProduct() {
        addLaptopToCart();
        CartPage cart = new CartPage(driver);
        cart.open();
        cart.removeFirstItem();
        Assert.assertTrue(cart.getEmptyMessage().contains("Your Shopping Cart is empty!"));
    }

    @Test(priority = 23)
    public void tc23_emptyCartMessage() {
        CartPage cart = new CartPage(driver);
        cart.open();
        Assert.assertTrue(cart.getEmptyMessage().contains("Your Shopping Cart is empty!"));
    }

    @Test(priority = 24)
    public void tc24_addTwoProducts() {
        addLaptopToCart();
        ProductPage product = new ProductPage(driver);
        product.open("fiction");
        product.addToCart();
        product.getNotification();
        CartPage cart = new CartPage(driver);
        cart.open();
        Assert.assertEquals(cart.getItemNames().size(), 2);
    }

    // ===================== COMPARE =====================

    @Test(priority = 25)
    public void tc25_compareTwoProducts() {
        ProductPage product = new ProductPage(driver);
        product.open(LAPTOP);
        product.addToCompare();
        product.open("fiction");
        product.addToCompare();
        List<String> names = new ComparePage(driver).getProductNames();
        Assert.assertEquals(names.size(), 2);
        Assert.assertTrue(names.contains("14.1-inch Laptop"));
        Assert.assertTrue(names.contains("Fiction"));
    }

    // ===================== CHECKOUT =====================

    @Test(priority = 26)
    public void tc26_placeOrder() {
        registerNewUser();
        addLaptopToCart();
        CartPage cart = new CartPage(driver);
        cart.open();
        cart.acceptTerms();
        cart.clickCheckout();
        CheckoutPage checkout = new CheckoutPage(driver);
        checkout.completeCheckout("India", "Mumbai City", "Prabhadevi", "400025", "9876543210");
        Assert.assertEquals(checkout.getSuccessMessage(), "Your order has been successfully processed!");
    }

    @Test(priority = 27)
    public void tc27_termsMustBeAccepted() {
        addLaptopToCart();
        CartPage cart = new CartPage(driver);
        cart.open();
        cart.clickCheckout();
        Assert.assertTrue(cart.getTermsWarning().contains("Please accept the terms of service"));
    }

    @Test(priority = 28)
    public void tc28_guestGoesToLoginPage() {
        addLaptopToCart();
        CartPage cart = new CartPage(driver);
        cart.open();
        cart.acceptTerms();
        cart.clickCheckout();
        Assert.assertTrue(driver.getCurrentUrl().contains("login"));
    }

    // ===================== LOGOUT =====================

    @Test(priority = 29)
    public void tc29_successfulLogout() {
        registerNewUser();
        HomePage home = new HomePage(driver);
        Assert.assertTrue(home.isLogoutVisible());
        home.clickLogout();
        Assert.assertTrue(home.isLoginVisible());
    }

    @Test(priority = 30)
    public void tc30_registerLinkVisibleAfterLogout() {
        registerNewUser();
        HomePage home = new HomePage(driver);
        home.clickLogout();
        Assert.assertTrue(home.isRegisterVisible());
    }

    // ===================== FULL END-TO-END =====================

    @Test(priority = 31)
    public void tc31_fullShoppingFlow() {
        HomePage home = new HomePage(driver);

        String email = registerNewUser();
        Assert.assertTrue(home.isLogoutVisible());

        home.clickLogout();
        home.clickLogin();
        new LoginPage(driver).login(email, PASSWORD);
        Assert.assertEquals(home.getAccountEmail(), email);

        home.search("laptop");
        Assert.assertTrue(new SearchPage(driver).getResultTitles().size() > 0);

        addLaptopToCart();
        CartPage cart = new CartPage(driver);
        cart.open();
        cart.acceptTerms();
        cart.clickCheckout();

        CheckoutPage checkout = new CheckoutPage(driver);
        checkout.completeCheckout("India", "Mumbai City", "Prabhadevi", "400025", "9999922121");
        Assert.assertEquals(checkout.getSuccessMessage(), "Your order has been successfully processed!");

        driver.get(BasePage.URL);
        home.clickLogout();
        Assert.assertTrue(home.isLoginVisible());
    }
}