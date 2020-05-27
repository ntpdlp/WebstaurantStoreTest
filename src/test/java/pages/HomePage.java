package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.Constant;

public class HomePage {
    private WebDriver driver;
    private WebDriverWait wait;

    @FindBy(id="searchval")
    private WebElement txtSearchVal;
    @FindBy(css = ".btn.btn-info.banner-search-btn")
    private WebElement btnSearch;
    @FindBy(id="paging")
    private WebElement pagination;

    public HomePage(WebDriver driver){
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Constant.WAITING_CONTROL);
        PageFactory.initElements(driver,this);
    }

    public void SearchByKeyword(String keyword){
        this.txtSearchVal.sendKeys(keyword);
        this.btnSearch.click();
        wait.until(ExpectedConditions.visibilityOf(this.pagination));
    }

}
