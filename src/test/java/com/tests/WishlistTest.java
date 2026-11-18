package com.tests;

import com.base.BaseTest;
import com.pages.DashboardPage;
import com.pages.ProductPage;
import com.pages.LoginPage;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class WishlistTest extends BaseTest {

    private DashboardPage dashboardPage;
    private ProductPage productPage;
    private LoginPage loginPage;

    @BeforeMethod
    public void initializePages() {
        dashboardPage = new DashboardPage(getDriver());
        productPage = new ProductPage(getDriver());
        loginPage = new LoginPage(getDriver());
    }

    @Test(description = "Validate that an unauthenticated guest user attempting to add a random item to the Wishlist is redirected to the Customer Login / Create an Account page")
    public void testGuestUserAddToWishlistRedirectsToLogin() {
        dashboardPage.addRandomProduct();
        productPage.assertNavigationToProductPage();
        productPage.clickAddToWishlist();
        loginPage.assertNavigationLoginPage();
    }
}

