package com.pages;

import com.base.BasePage;
import com.utils.JsonDataReader;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static org.testng.Assert.assertTrue;

public class DashboardPage extends BasePage {

    // Locators
    private static final By PRIVACY_POPUP = By.className("privacy_prompt_content");
    private static final By PRIVACY_OPTIN_RADIO = By.id("privacy_pref_optin");
    private static final By CONSENT_SUBMIT_BUTTON = By.id("consent_prompt_submit");
    private static final By MY_DASHBOARD_HEADING = By.xpath("//h1[contains(text(), 'My Dashboard')] | //h2[contains(text(), 'My Dashboard')]");
    private static final By CONFIRMATION_MESSAGE = By.xpath("//*[contains(text(), 'Thank you for registering')]");
    private static final By ACCOUNT_MENU = By.cssSelector("[data-target-element='#header-account']");
    private static final String ACCOUNT_OPTION_XPATH = "//div[@id='header-account']//a[normalize-space()='%s']";
    private static final By ACCESSORIES_MENU = By.xpath("//nav[@id='nav']//a[contains(@class,'level0') and contains(@href,'/accessories.html')]");
    private static final By SHOES_SUBMENU = By.xpath("//nav[@id='nav']//a[contains(@href,'/accessories/shoes.html')]");

    private static final String PRODUCT_ITEM_XPATH = "(//li[@class='item last'])[%d]";

    public static final String LOGIN = "Log In";
    public static final String REGISTER = "Register";

    public DashboardPage(WebDriver driver) {
        super(driver);
    }

    public void navigateTo(String accountOption) {
        click(ACCOUNT_MENU);
        click(By.xpath(String.format(ACCOUNT_OPTION_XPATH, accountOption)));
    }

    public void navigateToShoes() {
        hoverOver(ACCESSORIES_MENU);
        click(SHOES_SUBMENU);
        wait.until(ExpectedConditions.urlContains("/accessories/shoes"));
    }

    public void closePrivacyPopup() {
        if (isDisplayed(PRIVACY_POPUP)) {
            click(PRIVACY_OPTIN_RADIO);
            click(CONSENT_SUBMIT_BUTTON);
            wait.until(ExpectedConditions.invisibilityOfElementLocated(PRIVACY_POPUP));
        }
    }

    public boolean isMyDashboardDisplayed() {
        return isDisplayed(MY_DASHBOARD_HEADING);
    }

    public boolean isConfirmationMessageDisplayed() {
        return isDisplayed(CONFIRMATION_MESSAGE);
    }

    public String getConfirmationMessage() {
        return getText(CONFIRMATION_MESSAGE);
    }

    public void assertRegistrationSuccess() {
        String expectedSuccessMessage = JsonDataReader.getSuccessMessage("registrationSuccess");

        assertTrue(isMyDashboardDisplayed(),
                "User should be redirected to My Dashboard page after successful registration");

        assertTrue(isConfirmationMessageDisplayed(),
                "Confirmation message should be displayed");

        String actualMessage = getConfirmationMessage();
        assertTrue(actualMessage.contains(expectedSuccessMessage),
                "Confirmation message should contain '" + expectedSuccessMessage + "'. Actual message: " + actualMessage);
    }

    public void assertNavigationToDashboard() {
        assertTrue(isMyDashboardDisplayed(),
                "User should be on the Dashboard page");
    }

    public void addRandomProduct() {
        int index = (int) (Math.random() * 5) + 1;
        By product = By.xpath(String.format(PRODUCT_ITEM_XPATH, index));
        scrollIntoView(product);
        click(product);
    }
}
