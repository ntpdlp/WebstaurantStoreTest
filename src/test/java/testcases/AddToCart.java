package testcases;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.CartPage;
import pages.HomePage;
import pages.SearchPage;
import utils.Constant;
import utils.PageNavigationHandler;

import java.sql.Driver;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;


public class AddToCart {
    private WebDriver driver;
    private HomePage homepage;
    private SearchPage searchPage;
    private CartPage cartPage;
    private JavascriptExecutor js;
    private int reportType;

    public AddToCart(){
        System.setProperty("webdriver.chrome.driver",Constant.DRIVE_PATH);
        driver = new ChromeDriver();
        homepage = new HomePage(driver);
        searchPage = new SearchPage(driver);
        cartPage = new CartPage(driver);
        js = (JavascriptExecutor)driver;
        reportType = Constant.SIMPLE_TESTCASE_REPORT; // DETAIL_TESTCASE_REPORT
    }

    @Before
    public void setup(){
        driver.get(Constant.URL);
        driver.manage().window().maximize();
    }

    @After
    public void teardown(){
        System.out.println("Test Done! ");
        driver.quit();
    }

    @Test
    public void TC_001_Cart_EmptyCartHavingProducts(){
        homepage.SearchByKeyword("stainless work table");
        boolean isTitle = searchPage.verifyEveryProductsContainTitle("Table",reportType);
        searchPage.addToCartLastedFoundItem("5");
        boolean isProductAdded = cartPage.verifyProductDisplayOnCart(searchPage.getProductLastFound());
        cartPage.emptyCart();
        boolean isEmptyMessage = cartPage.verifyCartMessage("Your cart is empty.");

        System.out.println("Verify every product contains 'Table': " + isTitle);
        System.out.println("Verify product is added into cart successfully: " + isProductAdded);
        System.out.println("Verify after empty cart 'Your cart is empty': " + isEmptyMessage);
        assertArrayEquals(new boolean[]{isTitle,isProductAdded,isEmptyMessage},new boolean[]{false,true,true});
    }





}
