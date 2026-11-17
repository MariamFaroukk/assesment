package com.pages;

import com.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static org.testng.Assert.assertTrue;

public class ProductPage extends BasePage {

        private static final By ADD_TO_WISHLIST_LINK = By.className("link-wishlist");
    
    private WebDriver driver;

    public ProductPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }

    public boolean isProductPageDisplayed() {
        return isDisplayed(ADD_TO_WISHLIST_LINK);
    }

    public void clickAddToWishlist() {
        scrollIntoView(ADD_TO_WISHLIST_LINK);
        click(ADD_TO_WISHLIST_LINK);
    }

    public void assertNavigationToProductPage() {
        assertTrue(isProductPageDisplayed(),
                "Product page should be displayed with 'Add to Wishlist' link");
    }
}
