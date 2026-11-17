package com.pages;

import com.base.BasePage;
import com.utils.JsonDataReader;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class RegistrationPage extends BasePage {

    // Locators
    private static final By FIRST_NAME_INPUT = By.id("firstname");
    private static final By LAST_NAME_INPUT = By.id("lastname");
    private static final By EMAIL_INPUT = By.id("email_address");
    private static final By PASSWORD_INPUT = By.id("password");
    private static final By CONFIRM_PASSWORD_INPUT = By.id("confirmation");
    private static final By REGISTER_BUTTON = By.xpath("//button[@title='Register']");
    private static final By CONFIRMATION_MESSAGE = By.xpath("//*[contains(text(), 'Thank you for registering')]");
    private static final By MY_DASHBOARD_HEADING = By.xpath("//h1[contains(text(), 'My Dashboard')] | //h2[contains(text(), 'My Dashboard')]");

    public RegistrationPage(WebDriver driver) {
        super(driver);
    }

    public void enterFirstName(String firstName) {
        type(FIRST_NAME_INPUT, firstName);
    }

    public void enterLastName(String lastName) {
        type(LAST_NAME_INPUT, lastName);
    }

    public void enterEmail(String email) {
        type(EMAIL_INPUT, email);
    }

    public void enterPassword(String password) {
        type(PASSWORD_INPUT, password);
    }

    public void enterConfirmPassword(String confirmPassword) {
        type(CONFIRM_PASSWORD_INPUT, confirmPassword);
    }

    public void clickRegisterButton() {
        click(REGISTER_BUTTON);
    }

    public boolean isConfirmationMessageDisplayed() {
        return isDisplayed(CONFIRMATION_MESSAGE);
    }

    public String getConfirmationMessage() {
        return getText(CONFIRMATION_MESSAGE);
    }


    public void registerNewUser(String firstName, String lastName, String email, String password, String confirmPassword) {
        enterFirstName(firstName);
        enterLastName(lastName);
        enterEmail(email);
        enterPassword(password);
        enterConfirmPassword(confirmPassword);
        scrollIntoView(REGISTER_BUTTON);
        clickRegisterButton();
    }

    public boolean isOnRegistrationPage() {
        return driver.getCurrentUrl().contains("/customer/account/create");
    }

    public void assertRegistrationFailed(String invalidEmail) {
        String currentUrl = driver.getCurrentUrl();
        assertTrue(isOnRegistrationPage(),
                "User should remain on the registration page when registration fails. Actual URL: " + currentUrl);


        String expectedValidationMessage = JsonDataReader.getValidationMessage("invalidEmail")
                .replace("{email}", invalidEmail);
        String actualValidationMessage = getValidationMessage(EMAIL_INPUT);
        assertEquals(actualValidationMessage, expectedValidationMessage,
                "Email field validation message should match the expected browser validation message");
    }
}
