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

    @Test(description = " Add An Accessory (Shoe) to Cart with Color Avilable and random Size and Assert on Aceesory Details",
          dataProvider = "addProductToCartData")
    public void testAddProductToCart(String productName, String color, int quantity, double price) {
        dashboardPage.navigateToShoes();
        accessoriesPage.assertShoesCategoryLoaded();
        accessoriesPage.selectProductByName(productName);
        productDetailPage.assertProductDetails(productName, price);
        String selectedSize = productDetailPage.addProductToCart(quantity);
        shoppingCartPage.assertProductAddedToShoppingCart(productName, color, selectedSize, price, quantity);
    }
}

