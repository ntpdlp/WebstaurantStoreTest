package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage {
    protected WebDriver driver;

    public HomePage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver,this);
    }

    @FindBy(id="searchval")
    private WebElement txtSearchVal;
    @FindBy(css = ".btn.btn-info.banner-search-btn")
    private WebElement btnSearch;

    public void SearchByKeyword(String keyword){
        this.txtSearchVal.sendKeys(keyword);
        this.btnSearch.click();
        WebDriverWait wait = new WebDriverWait(driver,120);
        WebElement pagination = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#paging")));
    }

}
