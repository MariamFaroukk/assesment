# Tealium Ecommerce Demo - Registration Test Automation

This project automates test cases using Selenium WebDriver, TestNG framework, and Allure Reports for comprehensive test reporting.

## How to Run Tests

```bash
# Run all tests
mvn clean test

# Run specific test
mvn clean test -Dtest=TestClassName

# Run in headless mode
mvn clean test -Dheadless=true

# Run with specific browser (chrome, firefox, edge)
mvn clean test -Dbrowser=firefox
```

## Allure Reports Setup & Usage

### Generate Allure Report After Running Tests

After running tests with `mvn clean test`, generate and view the Allure report:

```bash
# Generate the Allure report
mvn allure:report

# Serve the report (opens in browser automatically)
mvn allure:serve
```

### Allure Report Features

The Allure report includes:
- **Test Overview**: Summary of passed, failed, and skipped tests
- **Test Results**: Detailed results for each test case
- **Screenshots**: Automatic failure screenshots attached to failed tests
- **Duration**: Test execution time for each test
- **Statistics**: Pass/fail rates

### Report Location

- **Test Results**: `allure-results/` directory (JSON files generated during test execution)
- **Generated Report**: `allure-report/` directory (HTML report - created after running `mvn allure:report`)
- **View Report**: Open `allure-report/index.html` in a browser

## Project Structure

```
assesment/
├── src/
│   ├── main/
│   │   └── java/
│   │       └── com/
│   │           ├── base/
│   │           │   ├── BasePage.java          # Base page with common Selenium methods
│   │           │   └── DriverFactory.java      # WebDriver factory
│   │           ├── pages/
│   │           │   ├── AccessoriesPage.java    # Accessories page object
│   │           │   ├── DashboardPage.java      # Dashboard page object
│   │           │   ├── LoginPage.java          # Login page object
│   │           │   ├── ProductDetailPage.java  # Product detail page object
│   │           │   ├── ProductPage.java        # Product page object
│   │           │   ├── RegistrationPage.java   # Registration page object
│   │           │   └── ShoppingCartPage.java   # Shopping cart page object
│   │           └── utils/
│   │               ├── ConfigReader.java       # Configuration reader
│   │               ├── JsonDataReader.java     # JSON test data/messages reader
│   │               └── TestDataGenerator.java  # Random test data generator
│   └── test/
│       ├── java/
│       │   └── com/
│       │       ├── base/
│       │       │   └── BaseTest.java           # Base test class
│       │       ├── listeners/
│       │       │   └── AllureTestListener.java # Allure reporting & screenshots
│       │       └── tests/
│       │           ├── AddProductToCartTest.java # Add product to cart test case
│       │           ├── InvalidLoginTest.java   # Invalid login test case
│       │           ├── RegistrationTest.java   # Registration test case
│       │           └── WishlistTest.java       # Wishlist test case
│       └── resources/
│           ├── config.properties               # Configuration
│           ├── messages.json                   # Expected UI messages
│           └── testdata.json                   # Test data
├── testng.xml                                   # TestNG configuration with Allure listeners
├── pom.xml                                      # Maven configuration with Allure plugin
├── AUTOMATION_GUIDELINES.md                     # Conventions & patterns
└── README.md                                    # This file
```

## Prerequisites

- Java 11 or higher
- Maven 3.6+
- Chrome, Firefox, or Edge browser (drivers auto-downloaded by WebDriverManager)## Dependencies

- **Selenium WebDriver 4.15.0** - Browser automation
- **TestNG 7.7.1** - Test framework
- **Allure TestNG 2.21.0** - Test reporting
- **WebDriverManager 5.6.3** - Automatic driver management
- **JavaFaker 1.0.2** - Test data generation

## Configuration

Edit `src/test/resources/config.properties`:

```properties
base.url=https://ecommerce.tealiumdemo.com/
browser=chrome                    # chrome, firefox, or edge
headless=false                    # true for headless mode
explicit.wait.seconds=15          # Explicit wait timeout
```

## Page Object Model Usage

Use methods in page objects to interact with the application:



## Extending Tests

To add more test cases:

1. Create test methods in existing test class or new test class in `src/test/java/com/tests/`
2. Use existing page object methods for interactions
3. Use TestNG annotations: `@Test`, `@BeforeMethod`, `@AfterMethod`
4. Add the test class to `testng.xml`



5. Run tests and generate Allure report:
```bash
mvn clean test
mvn allure:report
mvn allure:serve
```

## Test Execution Flow

1. **Setup:** WebDriver initializes and navigates to base URL
2. **Test:** Execute test steps using page object methods
3. **Verify:** Assert expected results
4. **Report:** Allure captures test results and screenshots (on failure)
5. **Teardown:** WebDriver quit and resources cleaned up

## Allure Report Workflow

```
1. Run Tests
   ↓
   mvn clean test
   ↓
   Generates: allure-results/ (JSON files)
   
2. Generate Report
   ↓
   mvn allure:report
   ↓
   Generates: allure-report/ (HTML)
   
3. View Report
   ↓
   mvn allure:serve
   ↓
   Opens: http://localhost:4040 (in browser)
```

## For More Information

See `AUTOMATION_GUIDELINES.md` for project conventions, design patterns, and best practices.

