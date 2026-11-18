package com.pages;

import com.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class ShoppingCartPage extends BasePage {

    private static final By CART_HEADING = By.xpath("//h1[contains(text(), 'Shopping Cart')]");
    private static final By SUCCESS_MESSAGE = By.xpath("//li[contains(@class,'success-msg')]//span");
    private static final By CART_ITEM_NAME = By.xpath("//h2[contains(@class,'product-name')]/a");
    private static final By CART_ITEM_PRICE = By.xpath("//td[contains(@class,'product-cart-price')]//span[@class='price']");
    private static final By CART_QUANTITY_INPUT = By.xpath("//input[@title='Qty']");
    private static final By CART_ITEM_TOTAL = By.xpath("//td[contains(@class,'product-cart-total')]//span[@class='cart-price']");
    private static final By CART_ITEM_COLOR = By.xpath("//dl[contains(@class,'item-options')]/dt[normalize-space()='Color']/following-sibling::dd[1]");
    private static final By CART_ITEM_SIZE = By.xpath("//dl[contains(@class,'item-options')]/dt[normalize-space()='Shoe size']/following-sibling::dd[1]");

    public ShoppingCartPage(WebDriver driver) {
        super(driver);
    }

    public void assertShoppingCartPageDisplayed() {
        waitForVisible(CART_HEADING);
        assertTrue(driver.getCurrentUrl().contains("/checkout/cart"),
                "Should be on the shopping cart page but was: " + driver.getCurrentUrl());
    }

    public String getSuccessMessage() {
        return getText(SUCCESS_MESSAGE);
    }

    public String getProductName() {
        return getText(CART_ITEM_NAME);
    }

    public String getProductPrice() {
        return getText(CART_ITEM_PRICE);
    }

    public int getProductQuantity() {
        return Integer.parseInt(getAttribute(CART_QUANTITY_INPUT, "value").trim());
    }

    public String getCartTotal() {
        return getText(CART_ITEM_TOTAL);
    }

    private String getItemOption(By locator) {
        return wait.until(ExpectedConditions.presenceOfElementLocated(locator)).getAttribute("textContent").trim();
    }

    public String getProductColor() {
        return getItemOption(CART_ITEM_COLOR);
    }

    public String getProductSize() {
        return getItemOption(CART_ITEM_SIZE);
    }

    public static double parsePrice(String price) {
        return Double.parseDouble(price.replaceAll("[^0-9.]", ""));
    }

    public void assertProductAddedToShoppingCart(String productName, String color, String size,
                                                 double price, int quantity) {
        assertShoppingCartPageDisplayed();
        assertEquals(getSuccessMessage(), productName + " was added to your shopping cart.",
                "Success message should confirm the product was added");

        assertTrue(getProductName().equalsIgnoreCase(productName),
                "Cart should contain the added product: " + productName);
        assertEquals(getProductColor(), color, "Cart product color should match the selected color");
        assertEquals(getProductSize(), size, "Cart shoe size should match the randomly selected size");

        double cartPrice = parsePrice(getProductPrice());
        assertEquals(cartPrice, price, "Cart unit price should be " + price);
        assertEquals(getProductQuantity(), quantity, "Cart quantity (Qty input value) should match the added quantity");

        double cartTotal = parsePrice(getCartTotal());
        assertEquals(cartTotal, quantity * cartPrice, "Cart total (cart-price) should equal Qty * price");
    }
}
