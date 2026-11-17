package com.tests;

import com.base.BaseTest;
import com.pages.AccessoriesPage;
import com.pages.DashboardPage;
import com.pages.ProductDetailPage;
import com.pages.ShoppingCartPage;
import com.utils.JsonDataReader;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class AddProductToCartTest extends BaseTest {

    private DashboardPage dashboardPage;
    private AccessoriesPage accessoriesPage;
    private ProductDetailPage productDetailPage;
    private ShoppingCartPage shoppingCartPage;

    @BeforeMethod
    public void initializePages() {
        dashboardPage = new DashboardPage(getDriver());
        accessoriesPage = new AccessoriesPage(getDriver());
        productDetailPage = new ProductDetailPage(getDriver());
        shoppingCartPage = new ShoppingCartPage(getDriver());
    }

    @DataProvider(name = "addProductToCartData")
    public Object[][] addProductToCartDataProvider() {
        return JsonDataReader.getAddProductToCartData();
    }

    @Test(description = "End-to-End: Accessories > Shoes > Dorian Perforated Oxford, " +
                        "select Color Black and random Size, add to cart and verify cart details",
          dataProvider = "addProductToCartData")
    public void testAddProductToCart(String productName, String color, int quantity, double price) {
        // Step 1: Accessories > Shoes, then open the product
        dashboardPage.navigateToShoes();
        accessoriesPage.assertShoesCategoryLoaded();
        accessoriesPage.selectProductByName(productName);

        // Step 2: Verify product details
        productDetailPage.assertProductDetails(productName, price);

        // Step 3: Select Black, a random size, and add to cart
        String selectedSize = productDetailPage.addProductToCart(quantity);

        // Step 4: Verify the product was added to the shopping cart
        shoppingCartPage.assertProductAddedToShoppingCart(productName, color, selectedSize, price, quantity);
    }
}

