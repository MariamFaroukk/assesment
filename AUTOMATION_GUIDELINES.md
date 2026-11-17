# Automation Guidelines

Conventions to follow for this project. Keep this as the single reference —
do not create additional docs (setup guides, summaries, indexes, etc.) unless
explicitly requested. Only `README.md` (usage) and this file (conventions)
should exist.

## Stack
- Language: Java 11+
- Browser automation: Selenium WebDriver
- Test framework: TestNG
- Build tool: Maven
- Driver management: WebDriverManager (auto driver downloads)
- Element inspection: Playwright CLI (inspector only—for discovering selectors)

## Design Pattern
- **Page Object Model (POM)**. Every page/feature gets a class in
  `src/main/java/com/pages/`, extending `BasePage`.
- Locators are `private static final By` fields at the top of the page class.
- Page classes expose action methods (e.g. `enterEmail()`, `clickRegisterButton()`)
  and verification methods (e.g. `isDashboardDisplayed()`), not raw `By` locators,
  to test classes.
- Test classes only call page object methods — no direct `driver.findElement`
  calls in tests.

## Project Structure
```
src/main/java/com/
  base/        BasePage.java, DriverFactory.java   (shared infra, don't duplicate)
  pages/       one class per page/feature (POM)
  utils/       ConfigReader.java, other shared helpers

src/test/java/com/
  base/        BaseTest.java (setUp/tearDown via @BeforeMethod/@AfterMethod)
  tests/       one class per feature/test suite
  listeners/   AllureTestListener.java
```
New test cases go in `src/test/java/com/tests/`, reusing existing page objects
where possible; add new page object methods instead of duplicating locators.

## Waits & Assertions
- Explicit waits only (`WebDriverWait` via `BasePage` helpers). No `Thread.sleep`.
- Use TestNG `Assert` with a descriptive failure message on every assertion.

## Test Data
- Use unique data per run where uniqueness matters (e.g. timestamp-based emails).
- Keep test data local to the test method unless shared across many tests.

## Configuration
- All environment values (URL, browser, headless, wait timeout) live in
  `src/test/resources/config.properties`, read via `ConfigReader`. Don't hardcode.

## Finding Element Selectors with Playwright Inspector
When you need to discover selectors for new page elements:

1. **Open Playwright Inspector** (run in the working directory):
   ```bash
   playwright-cli open https://your-app-url
   ```

2. **Take a snapshot** to see all elements with refs:
   ```bash
   playwright-cli snapshot
   ```

3. **Find elements by text**:
   ```bash
   playwright-cli find "Register"              # find elements containing text
   playwright-cli find --regex "/sign (in|up)/i"  # regex search
   ```

4. **Inspect element attributes** (to get `id`, `data-testid`, classes, etc.):
   ```bash
   playwright-cli eval "el => el.id" e5                 # get element ID
   playwright-cli eval "el => el.getAttribute('data-testid')" e5  # get data-testid
   ```

5. **Convert to CSS selectors** for use in Selenium:
   - Refs in the snapshot correspond to stable selectors
   - Use role locators, test IDs, or CSS selectors in your POM

6. **Close the inspector** when done:
   ```bash
   playwright-cli close
   ```

**Never use Playwright for test execution**—use Playwright Inspector only to discover selectors; write and run tests with Selenium/TestNG.

## Running Tests
```bash
mvn clean test                       # default browser (chrome)
mvn clean test -Dbrowser=firefox     # firefox / edge
mvn clean test -Dheadless=true
mvn clean test -Dtest=RegistrationTest
```

## Selector Discovery Examples

### Registration Page Elements
Reference: [RegistrationPage.java](src/main/java/com/pages/RegistrationPage.java)

Discovered via Playwright Inspector (2026-11-15):

| Field | HTML ID | Selector | Method |
|-------|---------|----------|--------|
| First Name | `firstname` | `By.id("firstname")` | `enterFirstName(String)` |
| Last Name | `lastname` | `By.id("lastname")` | `enterLastName(String)` |
| Email | `email_address` | `By.id("email_address")` | `enterEmail(String)` |
| Password | `password` | `By.id("password")` | `enterPassword(String)` |
| Confirm Password | `confirmation` | `By.id("confirmation")` | `enterConfirmPassword(String)` |
| Register Button | N/A (XPath) | `By.xpath("//button[contains(text(), 'Register')]")` | `clickRegisterButton()` |

**Discovery Method:**
```bash
playwright-cli open https://ecommerce.tealiumdemo.com/
playwright-cli goto https://ecommerce.tealiumdemo.com/customer/account/create/
playwright-cli snapshot
playwright-cli eval "el => el.id" <ref>  # to get each element's ID
playwright-cli close
```

Always extract actual element IDs/attributes from the page before writing selectors,
as form field naming varies by application (e.g., `firstname` vs `registration_form_first_name`).

## Documentation Policy
- Don't generate extra markdown files (quickstart/setup/summary/deliverables/etc.)
  for routine changes or new test cases. Update `README.md` only if usage
  instructions actually change.
