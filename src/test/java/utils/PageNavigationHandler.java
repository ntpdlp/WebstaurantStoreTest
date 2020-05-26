package utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PageNavigationHandler {

    public static void waitFor(WebDriver driver, By by, int timeOutInSeconds){
        WebDriverWait wait = new WebDriverWait(driver,timeOutInSeconds);
        wait.until(ExpectedConditions.presenceOfElementLocated(by));
    }
}
