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
        // Step 1: Navigate to a random product as a guest user
        System.out.println("Navigating to a random product...");
        dashboardPage.addRandomProduct();

        // Step 2: Verify we are on the product page
        productPage.assertNavigationToProductPage();

        // Step 3: Click the 'Add to Wishlist' link
        System.out.println("Clicking 'Add to Wishlist' link...");
        productPage.clickAddToWishlist();

        // Step 4: Verify redirect to Login/Create Account page
        loginPage.assertNavigationLoginPage();
    }
}

