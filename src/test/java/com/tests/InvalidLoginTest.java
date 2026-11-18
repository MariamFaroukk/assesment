package com.tests;

import com.base.BaseTest;
import com.pages.DashboardPage;
import com.pages.LoginPage;
import com.utils.JsonDataReader;
import com.utils.TestDataGenerator;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class InvalidLoginTest extends BaseTest {

    private DashboardPage dashboardPage;
    private LoginPage loginPage;

    @BeforeMethod
    public void initializePages() {
        dashboardPage = new DashboardPage(getDriver());
        loginPage = new LoginPage(getDriver());
    }

    @DataProvider(name = "validUserData")
    public Object[][] validUserDataProvider() {
        TestDataGenerator.UserData userData = TestDataGenerator.generateUserData();
        return new Object[][] {
            {userData.email, userData.password}
        };
    }

    @Test(description = "Negative - Validate that an error message is shown when attempting to log in with valid credentials not registered in the system",
          dataProvider = "validUserData")
    public void testLoginWithInvalidCredentials(String email, String password) {
        dashboardPage.navigateTo(DashboardPage.LOGIN);
        loginPage.assertNavigationLoginPage();
        loginPage.loginWithCredentials(email, password);
        loginPage.assertFailedLogin(JsonDataReader.getErrorMessage("invalidLogin"));
    }
}
