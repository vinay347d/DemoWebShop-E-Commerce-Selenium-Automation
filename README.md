# Demo Web Shop E-Commerce Automation Framework

## Project Overview

An end-to-end web automation testing framework developed for the Tricentis Demo Web Shop application using Java, Selenium WebDriver, TestNG, Maven and Page Object Model (POM).

The framework automates important e-commerce workflows, including user registration, login, product search, shopping cart operations, product comparison and checkout.

## Application Under Test

**Application:** Tricentis Demo Web Shop

**URL:** https://demowebshop.tricentis.com/

## Technologies & Tools

| Technology | Purpose |
|---|---|
| Java 17 | Programming language |
| Selenium WebDriver 4.43.0 | Browser automation |
| TestNG 7.10.2 | Test execution and assertions |
| Maven | Dependency and build management |
| Extent Reports 5.1.2 | HTML test reporting |
| ChromeDriver | Chrome browser automation |
| IntelliJ IDEA | Development environment |

## Framework Architecture

The framework follows the Page Object Model design pattern.

- **BasePage:** Common Selenium methods, explicit waits and browser interactions.
- **Page Classes:** Separate classes for Login, Registration, Home, Product, Cart, Checkout, Search and Categories.
- **MainTest:** Test cases and test execution.
- **TestListener:** TestNG execution events and failure screenshot capture.
- **ExtentManager:** Extent Reports configuration and initialization.
- **DataProvider:** Data-driven testing using TestNG.

## Test Scenarios Covered

| Module | Scenarios |
|---|---|
| Registration | Valid registration, duplicate email, mandatory fields, password validation |
| Login | Valid login, invalid credentials, empty credentials |
| Search | Valid search, invalid search, short search |
| Categories | Books, Computers, Electronics, Apparel & Shoes |
| Product Details | Product name, price, description, availability |
| Shopping Cart | Add product, update quantity, remove product, empty cart |
| Compare | Compare multiple products |
| Checkout | Place order, terms validation, guest checkout |
| Logout | Successful logout and registration link |
| End-to-End | Complete shopping workflow |

**31 test methods and approximately 40 test executions** when all DataProvider combinations are executed.

## Reporting & Screenshots

Extent Reports generates an HTML execution report containing:

- Passed test cases
- Failed test cases
- Skipped test cases
- Failure details
- Screenshots of failed tests

**Report location:** `reports/ExtentReport.html`

**Screenshot location:** `screenshots/`

TestNG also generates its execution reports inside `test-output/`.

## Project Execution

### Prerequisites

- JDK 17 or later
- IntelliJ IDEA
- Maven
- Google Chrome

### Run the project

1. Clone the repository.
2. Open the project in IntelliJ IDEA.
3. Load Maven dependencies.
4. Configure JDK 17.
5. Execute `testng.xml`.

Alternatively, execute from the terminal:

```bash
mvn clean test
```

## Repository Structure

```text
DemoWebShop/
├── src/
│   └── test/
│       └── java/
│           ├── pages/
│           ├── Tests/
│           └── Utils/
├── screenshots/
├── reports/
├── test-output/
├── testng.xml
├── pom.xml
├── .gitignore
└── README.md
```

## Author

**Vinay Dudyala**

Automation Testing | Java | Selenium | TestNG | Maven | SQL