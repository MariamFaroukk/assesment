package com.pages;

import com.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class ProductDetailPage extends BasePage {

    private static final By PRODUCT_TITLE = By.className("h1");
    private static final By PRODUCT_PRICE = By.className("price");
    // The page has two Add to Cart buttons; only the one submitting the product form is visible
    private static final By ADD_TO_CART_BUTTON = By.xpath("//button[@title='Add to Cart' and contains(@onclick,'productAddToCartForm')]");
    private static final By QUANTITY_INPUT = By.xpath("//input[@title='Qty']");
    private static final By COLOR_OPTION = By.xpath("//li[@id='option20']/a");
    // Size swatch element id -> shoe size
    private static final Map<String, String> SIZE_OPTIONS = new LinkedHashMap<>();

    static {
        SIZE_OPTIONS.put("option100", "8");
        SIZE_OPTIONS.put("option99", "9");
        SIZE_OPTIONS.put("option98", "10");
        SIZE_OPTIONS.put("option97", "11");
        SIZE_OPTIONS.put("option96", "12");
    }

    private final Random random = new Random();

    public ProductDetailPage(WebDriver driver) {
        super(driver);
    }

    public String getProductTitle() {
        return getText(PRODUCT_TITLE);
    }

    public String getProductPrice() {
        return getText(PRODUCT_PRICE);
    }

    public void selectColor() {
        click(COLOR_OPTION);
    }

    public String selectRandomSize() {
        List<String> optionIds = new ArrayList<>(SIZE_OPTIONS.keySet());
        String optionId = optionIds.get(random.nextInt(optionIds.size()));
        click(By.xpath("//li[@id='" + optionId + "']/a"));
        return SIZE_OPTIONS.get(optionId);
    }

    public void setQuantity(int quantity) {
        type(QUANTITY_INPUT, String.valueOf(quantity));
    }

    public void clickAddToCart() {
        scrollIntoView(ADD_TO_CART_BUTTON);
        click(ADD_TO_CART_BUTTON);
    }

    public String addProductToCart(int quantity) {
        selectColor();
        String selectedSize = selectRandomSize();
        setQuantity(quantity);
        clickAddToCart();
        wait.until(ExpectedConditions.urlContains("/checkout/cart"));
        return selectedSize;
    }

    public void assertProductDetails(String productName, double expectedPrice) {
        assertTrue(isDisplayed(PRODUCT_TITLE), "Product title (class=h1) should be displayed");
        assertTrue(isDisplayed(ADD_TO_CART_BUTTON), "Add to Cart button (title='Add to Cart') should be displayed");

        assertTrue(getProductTitle().equalsIgnoreCase(productName),
                "Product detail page title should be the product name: " + productName);
        String detailPrice = getProductPrice();
        assertEquals(ShoppingCartPage.parsePrice(detailPrice), expectedPrice,
                "Product price on detail page should be " + expectedPrice + " but was: " + detailPrice);
    }
}
