package com.pages;

import com.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static org.testng.Assert.assertTrue;

public class AccessoriesPage extends BasePage {

    public AccessoriesPage(WebDriver driver) {
        super(driver);
    }
    // Dynamic Locators
    private By productLink(String productName) {
    return By.xpath(
        "//h2[contains(@class,'product-name')]/a[normalize-space()='" + productName + "']"
    );
}
  
    public void selectProductByName(String productName) {
         By product = productLink(productName);
        scrollIntoView(product);
        click(product);
    }

    public void assertShoesCategoryLoaded() {
        assertTrue(driver.getCurrentUrl().contains("/accessories/shoes"),
                "Should be on the Accessories > Shoes category page but was: " + driver.getCurrentUrl());
    }
}
