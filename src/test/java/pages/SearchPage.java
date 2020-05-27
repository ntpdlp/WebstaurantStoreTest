package pages;

import com.sun.tracing.dtrace.FunctionName;
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
    private static final int VIEW_CART_BUTTON_INDEX = 0;
    private static final int NEXT_BUTTON_INDEX = 0;

    @FindBy(css = "#paging>div > ul > li")
    private List<WebElement> pages;
    @FindBy(id = "product_listing")
    private WebElement productPane;
    @FindBy(css = "#product_listing > div")
    private List<WebElement> products;
    @FindBy(css = "div:last-child .add_qty.quantity")
    private WebElement txtQuantityFieldLastFoundItem;
    @FindBy(css = "div:last-child .btn.btn-cart.btn-small")
    private WebElement btnAddToCartLastFoundItem;
    @FindBy(id = "ag-sub-grid")
    private WebElement productAccessories;
    @FindBy (xpath = "//button[text()='Add to Cart']")
    private WebElement btnAddToCartOnProductAccessories;
    @FindBy(css = ".notify-body > a")
    private List<WebElement> buttons;
    @FindBy(css = "div>div>ul>li a[rel='next']")
    private List<WebElement> nexts;

    public SearchPage(WebDriver driver){
        this.driver = driver;
        this.js = (JavascriptExecutor)driver;
        wait = new WebDriverWait(driver,Constant.WAITING_CONTROL);
        PageFactory.initElements(driver,this);
    }

    public boolean verifyEveryProductsContainTitle(String keyword, int reportType){
        boolean isResult = true;
        String lastpageDisplay = pages.get(pages.size() - 2).getText();
        Integer lastpageNum = Integer.parseInt(lastpageDisplay);
        System.out.println("Search Result is displayed totally in " + lastpageNum.intValue() + " pages. ");
        for(int i=1; i<=lastpageNum.intValue(); i++){
            int tempIndex = i;
            System.out.println("Verifying on page: " + tempIndex );
            boolean isCurrentPage = checkSearchResultPage(keyword,reportType);
            isResult = isResult && isCurrentPage;
            if(tempIndex < lastpageNum.intValue()){
                moveToNextPage();
            }
        }
        return isResult;
    }

    public boolean checkSearchResultPage(String keyword, int reportType){
        boolean isResult = true;
        wait.until(ExpectedConditions.visibilityOf(productPane));
        int totalProductPerPage = products.size();
        for(int i = 1; i<=totalProductPerPage; i++){
            String productBoxId = "productBox" + i;
            WebElement product = driver.findElement(By.id(productBoxId));
            boolean isContainKeyword = product.getText().contains(keyword);
            if(!isContainKeyword){
                isResult = false;
                printProductTitleDontQualify(productBoxId,keyword,product,reportType);
            }
        }
        return isResult;
    }

    public void printProductTitleDontQualify(String productId, String keyword, WebElement webElement, int reportType){
        if(reportType == Constant.DETAIL_TESTCASE_REPORT){
            String productInfo = webElement.getText();
            String str = "FAIL : " + productId + " does not contain \"" + keyword + "\" in its title" + "\n" + productInfo;
            System.out.println(str);
        }
    }

    public void inputQuantity(String quantity, WebElement webElement){
//        PageNavigationHandler.waitFor(driver,By.className("add_qty"),Constant.WAITING_CONTROL);
        wait.until(ExpectedConditions.visibilityOf(webElement));
        webElement.clear();
        webElement.sendKeys(quantity);
    }

    public String getProductLastFound(){
        PageNavigationHandler.waitFor(driver,By.className("description"),Constant.WAITING_CONTROL);
        WebElement productDescription = driver.findElement(By.cssSelector("div:last-child .description"));
        System.out.println("Last of Found Product: " + productDescription.getText());
        return productDescription.getText();
    }

    public void clickAddToCartLastFoundItem(WebElement webElement){
        js.executeScript("arguments[0].click();",webElement);

    }

    public void addToCartLastedFoundItem(String quantity){
        inputQuantity(quantity,this.txtQuantityFieldLastFoundItem);
        clickAddToCartLastFoundItem(this.btnAddToCartLastFoundItem);
        driver.manage().timeouts().implicitlyWait(Constant.WAITING_WINDOW,TimeUnit.SECONDS);
        System.out.println("need to check > or >=");
        boolean isPresentAccessories = driver.findElements(By.id("ag-sub-grid")).size() >= 1;
        if(isPresentAccessories){
            confirmProductAccessories();
        }
        clickViewCartButton();
    }


    public void confirmProductAccessories(){
        driver.manage().timeouts().implicitlyWait(Constant.WAITING_WINDOW,TimeUnit.SECONDS);
        this.btnAddToCartOnProductAccessories.click();
//        PageNavigationHandler.waitFor(driver,By.id("myModalLabel"),Constant.WAITING_CONTROL);
//        String productName = driver.findElement(By.id("myModalLabel")).getText();
//        System.out.println("Product Accessories title: " + productName);
    }

    public void clickViewCartButton(){
        driver.manage().timeouts().implicitlyWait(Constant.WAITING_WINDOW,TimeUnit.SECONDS);
//        PageNavigationHandler.waitFor(driver,By.xpath("//a[text()='View Cart']"),Constant.WAITING_CONTROL);
        WebElement viewCartLink = this.buttons.get(VIEW_CART_BUTTON_INDEX);
        viewCartLink.click();
    }

    public void moveToNextPage(){
        this.nexts.get(NEXT_BUTTON_INDEX).click();
    }

}
