package com.pages;

import com.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class LoginPage extends BasePage {

    // Locators - using IDs and XPath selectors as per Playwright discovery
    private static final By LOGIN_HEADING = By.xpath("//h1[contains(text(), 'Login') or contains(text(), 'LOGIN') or contains(text(), 'ACCOUNT')]");
    private static final By EMAIL_INPUT = By.id("email");
    private static final By PASSWORD_INPUT = By.id("pass");
    private static final By LOGIN_BUTTON = By.id("send2");
    private static final By CREATE_ACCOUNT_LINK = By.xpath("//a[contains(., 'Create an Account')]");
    
    private static final By ERROR_MESSAGE = By.cssSelector(".error-msg");
    
    private WebDriver driver;

    public LoginPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }

    public boolean isLoginPageDisplayed() {
        return isDisplayed(LOGIN_HEADING);
    }

    public boolean isCreateAccountLinkDisplayed() {
        return isDisplayed(CREATE_ACCOUNT_LINK);
    }

    public void enterEmail(String email) {
        type(EMAIL_INPUT, email);
    }

    public void enterPassword(String password) {
        type(PASSWORD_INPUT, password);
    }

    public void clickLoginButton() {
        click(LOGIN_BUTTON);
    }

    public String getLoginPageUrl() {
        return driver.getCurrentUrl();
    }

    public void waitForLoginPageToLoad() {
        waitForVisible(LOGIN_HEADING);
    }

    public void loginWithCredentials(String email, String password) {
        enterEmail(email);
        enterPassword(password);
        clickLoginButton();
    }

    public void assertFailedLogin(String expectedMessage) {
        waitForVisible(ERROR_MESSAGE);
        String actualMessage = getText(ERROR_MESSAGE);
        assertEquals(actualMessage, expectedMessage,
                "Error message displayed for failed login does not match the expected message");
    }

    public void assertNavigationLoginPage() {
        waitForLoginPageToLoad();
        
        assertTrue(isLoginPageDisplayed(),
                "User should be redirected to the Login page when attempting to add item to wishlist as a guest");

        assertTrue(isCreateAccountLinkDisplayed(),
                "The 'Create an Account' link should be displayed on the login page");

        String currentUrl = getLoginPageUrl();
        assertTrue(currentUrl.contains("/customer/account/login"),
                "Page URL should contain '/customer/account/login'. Actual URL: " + currentUrl);
    }
}
