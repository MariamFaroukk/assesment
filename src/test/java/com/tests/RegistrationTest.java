package com.tests;

import com.base.BaseTest;
import com.pages.DashboardPage;
import com.pages.DashboardPage.AccountOption;
import com.pages.RegistrationPage;
import com.utils.TestDataGenerator;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class RegistrationTest extends BaseTest {

    private DashboardPage dashboardPage;
    private RegistrationPage registrationPage;

    @BeforeMethod
    public void initializePages() {
        dashboardPage = new DashboardPage(getDriver());
        registrationPage = new RegistrationPage(getDriver());
    }

    @DataProvider(name = "validUserData")
    public Object[][] validUserDataProvider() {
        TestDataGenerator.UserData userData = TestDataGenerator.generateUserData();
        return new Object[][] {
            {userData.firstName, userData.lastName, userData.email, userData.password}
        };
    }

    @Test(description = "Positive - Validate that a new user can successfully register an account with generated fake data",
          dataProvider = "validUserData")
    public void testUserRegistrationWithValidDetails(String firstName, String lastName, String email, String password) {
        // Perform registration with generated data
        dashboardPage.navigateTo(AccountOption.REGISTER);
        registrationPage.registerNewUser(firstName, lastName, email, password, password);

        // Assert registration success
        dashboardPage.assertRegistrationSuccess();
    }

    @DataProvider(name = "invalidEmailUserData")
    public Object[][] invalidEmailUserDataProvider() {
        TestDataGenerator.UserData userData = TestDataGenerator.generateUserData();
        String invalidEmail = userData.email.replace("@", "");
        return new Object[][] {
            {userData.firstName, userData.lastName, invalidEmail, userData.password}
        };
    }

    @Test(description = "Negative - Validate that registration fails when an invalid email format is provided",
          dataProvider = "invalidEmailUserData")
    public void testUserRegistrationWithInvalidEmail(String firstName, String lastName, String invalidEmail, String password) {
        // Attempt registration with an invalid email format
        dashboardPage.navigateTo(AccountOption.REGISTER);
        registrationPage.registerNewUser(firstName, lastName, invalidEmail, password, password);

        // Assert registration failed
        registrationPage.assertRegistrationFailed(invalidEmail);
    }
}







