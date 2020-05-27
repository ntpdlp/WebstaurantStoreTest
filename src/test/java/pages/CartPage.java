package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.Constant;
import utils.PageNavigationHandler;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class CartPage {
    private WebDriver driver;
    private WebDriverWait wait;


    public CartPage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver,this);
        wait = new WebDriverWait(driver,Constant.WAITING_CONTROL);
    }

    public boolean verifyProductDisplayOnCart(String productName){
        driver.manage().timeouts().implicitlyWait(Constant.WAITING_WINDOW, TimeUnit.SECONDS);
        WebElement product = driver.findElement(By.cssSelector(".itemDescription.description > a"));
        String productDescription = product.getText();
        int compareResult = productDescription.compareTo(productName);
        return compareResult==0;
    }

    public void emptyCart(){
        PageNavigationHandler.waitFor(driver,By.cssSelector(".cartItemsHeader.toolbar"),Constant.WAITING_CONTROL);
        List<WebElement> emptyCarts = driver.findElements(By.cssSelector(".cartItemsHeader > div > a"));
        emptyCarts.get(0).click();
        confirmEmptyCartDialog();
    }

    public void confirmEmptyCartDialog(){
        driver.manage().timeouts().implicitlyWait(Constant.WAITING_WINDOW, TimeUnit.SECONDS);
        PageNavigationHandler.waitFor(driver,By.className("modal-footer"),Constant.WAITING_CONTROL);
        List<WebElement> buttons = driver.findElements(By.cssSelector(".modal-footer > button"));
        WebElement btnEmptyCart = buttons.get(3);
        btnEmptyCart.click();
    }

    public String getEmptyCartMessage(){
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        driver.manage().timeouts().implicitlyWait(Constant.WAITING_WINDOW, TimeUnit.SECONDS);
        PageNavigationHandler.waitFor(driver,By.className("empty-cart__text"),Constant.WAITING_CONTROL);
        List<WebElement> messages = driver.findElements(By.cssSelector(".empty-cart__text > p"));
        String cartMessage = messages.get(0).getText();
        return cartMessage;
    }

    public boolean verifyCartMessage(String exptected){
        return getEmptyCartMessage().compareTo(exptected)==0;
    }
}