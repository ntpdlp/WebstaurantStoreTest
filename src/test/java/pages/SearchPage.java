package pages;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.Constant;
import utils.PageNavigationHandler;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class SearchPage {
    private WebDriver driver;
    private WebDriverWait wait;
    private JavascriptExecutor js;

    public SearchPage(WebDriver driver){
        this.driver = driver;
        this.js = (JavascriptExecutor)driver;
        wait = new WebDriverWait(driver,Constant.WAITING_CONTROL);
        PageFactory.initElements(driver,this);
    }

    public boolean checkSearchResult(String keyword){
        boolean isResult = true;
        PageNavigationHandler.waitFor(driver,By.id("product_listing"), Constant.WAITING_CONTROL);
        List<WebElement> products = driver.findElements(By.cssSelector("#product_listing > div"));
        int productPerPage = products.size();
        for(int i = 1; i<=productPerPage; i++){
            String productBoxId = "productBox" + i;
            WebElement product = driver.findElement(By.id(productBoxId));
            boolean isContainKeyword = product.getText().contains(keyword);
            if(!isContainKeyword){
                isResult = false;
//                System.out.println("FAIL ProductId: " + productBoxId + " does not contain \"" + keyword + "\" in its title" + "\n" + product.getText());
            }
        }
        return isResult;
    }

    public void inputQuantityLastFound(String quantity){
        PageNavigationHandler.waitFor(driver,By.className("add_qty"),Constant.WAITING_CONTROL);
        WebElement txtQuantityLastItem = driver.findElement(By.cssSelector("div:last-child .add_qty.quantity"));
        txtQuantityLastItem.clear();
        txtQuantityLastItem.sendKeys(quantity);
    }

    public String getProductLastFound(){
        PageNavigationHandler.waitFor(driver,By.className("description"),Constant.WAITING_CONTROL);
        WebElement productDescription = driver.findElement(By.cssSelector("div:last-child .description"));
        String temp = productDescription.getText();
        System.out.println(temp);
        return temp;
    }

    public void clickAddToCartLastFound(){
        WebElement btnAddToCartLastItem = driver.findElement(By.cssSelector("div:last-child .btn.btn-cart.btn-small"));
        js.executeScript("arguments[0].click();",btnAddToCartLastItem);
    }

    public void addToCartLastedFoundItem(String quantity){
        inputQuantityLastFound(quantity);
        clickAddToCartLastFound();
        driver.manage().timeouts().implicitlyWait(Constant.WAITING_WINDOW,TimeUnit.SECONDS);
        PageNavigationHandler.waitFor(driver,By.id("ag-sub-grid"),Constant.WAITING_CONTROL);
        boolean isPresentAccessories = driver.findElements(By.id("ag-sub-grid")).size() >= 1;
        if(isPresentAccessories){
            confirmProductAccessories();
        }
        clickViewCartButton();
    }


    public void confirmProductAccessories(){
        driver.manage().timeouts().implicitlyWait(Constant.WAITING_WINDOW,TimeUnit.SECONDS);
        PageNavigationHandler.waitFor(driver,By.id("myModalLabel"),Constant.WAITING_CONTROL);
//        String productName = driver.findElement(By.id("myModalLabel")).getText();
//        System.out.println("Product Accessories title: " + productName);
        WebElement btnAddToCart = driver.findElement(By.xpath("//button[text()='Add to Cart']"));
        btnAddToCart.click();
    }

    public void clickViewCartButton(){
        driver.manage().timeouts().implicitlyWait(Constant.WAITING_WINDOW,TimeUnit.SECONDS);
        PageNavigationHandler.waitFor(driver,By.xpath("//a[text()='View Cart']"),Constant.WAITING_CONTROL);
        List<WebElement> buttons = driver.findElements(By.cssSelector(".notify-body > a"));
        WebElement viewCartLink = buttons.get(0);
        viewCartLink.click();
    }

    public void moveToNextPage(){
        List<WebElement> nexts = driver.findElements(By.cssSelector("div>div>ul>li a[rel='next']"));
        nexts.get(0).click();

    }

}
