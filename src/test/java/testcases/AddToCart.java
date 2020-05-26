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
    private int testReportMode;

    public AddToCart(){
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-notification");
        driver = new ChromeDriver(options);
        homepage = new HomePage(driver);
        searchPage = new SearchPage(driver);
        cartPage = new CartPage(driver);
        js = (JavascriptExecutor)driver;
        testReportMode = Constant.SIMPLE_TESTCASE_REPORT; // DETAIL_TESTCASE_REPORT
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
        boolean isVerifyKeyword = allProductsContainKeyword("Table");
        searchPage.addToCartLastedFoundItem("5");
        boolean isVerifyAddedProductToCart = cartPage.verifyProductDisplayOnCart(searchPage.getProductLastFound());
        cartPage.emptyCart();
        boolean isVerifyCartMessage = cartPage.getEmptyCartMessage().contains("Your cart is empty.");

        System.out.println("Verify every product contains 'Table': " + isVerifyKeyword);
        System.out.println("Verify product is added into cart successfully: " + isVerifyAddedProductToCart);
        System.out.println("Verify after empty cart 'Your cart is empty': " + isVerifyCartMessage);

        assertArrayEquals(new boolean[]{isVerifyKeyword,isVerifyAddedProductToCart,isVerifyCartMessage},new boolean[]{false,true,true});
    }


    public boolean allProductsContainKeyword(String keyword){
        //how many pages returned?
        boolean isResult = true;
        List<WebElement> pages = driver.findElements(By.cssSelector("#paging>div > ul > li"));
        //check: every product contain 'Table'
        String lastpageDisplay = pages.get(pages.size() - 2).getText();
        Integer lastpageNum = Integer.parseInt(lastpageDisplay);
        System.out.println("Search Result is displayed in " + lastpageNum.intValue() + " pages. ");
        for(int i=1; i<=lastpageNum.intValue(); i++){
            int tempIndex = i;
            System.out.println("Verifying on page: " + tempIndex );
            boolean isCurrentPage = searchPage.checkSearchResult("Table",this.testReportMode);
            isResult = isResult && isCurrentPage;
            if(tempIndex < lastpageNum.intValue()){
                searchPage.moveToNextPage();
            }
        }
        return isResult;
    }


}
