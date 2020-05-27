package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
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
    private static final int YOUR_CART_IS_EMPTY_INDEX = 0;


    public CartPage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver,this);
        wait = new WebDriverWait(driver,Constant.WAITING_CONTROL);
    }

    @FindBy(id = "cartItemCountSpan")
    private WebElement cartItemHeader;
    @FindBy(css = ".itemDescription.description > a")
    private WebElement product;
    @FindBy(xpath = "//a[text()='Empty Cart']")
    private WebElement btnEmptyCart;
    @FindBy(css = ".empty-cart__text > p")
    private List<WebElement> messages;

    public boolean verifyProductDisplayOnCart(String productName){
        driver.manage().timeouts().implicitlyWait(Constant.WAITING_WINDOW, TimeUnit.SECONDS);
        String productDescription = this.product.getText();
        int compareResult = productDescription.compareTo(productName);
        return compareResult==0;
    }

    public void emptyCart(){
        PageNavigationHandler.waitFor(driver,By.cssSelector(".cartItemsHeader.toolbar"),Constant.WAITING_CONTROL);
        this.btnEmptyCart.click();
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
        waitForRefreshCartPageShowEmptyMessage();
        String cartMessage = this.messages.get(YOUR_CART_IS_EMPTY_INDEX).getText();
        return cartMessage;
    }

    public void waitForRefreshCartPageShowEmptyMessage(){
        int milis = Constant.WAITING_CONTROL*100;
        String carts = this.cartItemHeader.getText();
        int initTime = Constant.WAITING_CONTROL;
        while(carts.compareTo("0")!=0 && initTime<milis){
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            initTime = initTime + (Constant.WAITING_CONTROL*10);
            carts = this.cartItemHeader.getText();
        }
    }

    public boolean verifyCartMessage(String exptected){
        return getEmptyCartMessage().compareTo(exptected)==0;
    }
}