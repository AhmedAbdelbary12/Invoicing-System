package com.invoicing.Utils.Actions;

import com.invoicing.Utils.Logs.LogsManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.time.Duration;

public class ActionBots {
    WebDriver driver;
    WebDriverWait wait;
    WaitManager waitManager;

    public ActionBots(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        waitManager = new WaitManager(driver);
    }


    public void click(By by) {
        try {
            wait.until(ExpectedConditions.and(ExpectedConditions.visibilityOfElementLocated(by), ExpectedConditions.elementToBeClickable(by)));
            WebElement element = findElement(by);
            scrollToElementUsingJS(by);
            element.click();
            LogsManager.info("Click on Element: " + by + "Successfully");
        } catch (Exception e) {
            LogsManager.error("File To Click : " + e.getMessage());
            throw new RuntimeException(e);
        }

    }


    public void type(By by, String text) {
        try {
            wait.until(ExpectedConditions.and(ExpectedConditions.presenceOfElementLocated(by), ExpectedConditions.visibilityOfElementLocated(by)));
            WebElement element = findElement(by);
            scrollToElementUsingJS(by);
            element.clear();
            element.sendKeys(text);
            LogsManager.info("Entered data in Element: " + by + "Successfully");
        } catch (Exception e) {
            LogsManager.error("File To SendKey : " + e.getMessage());
            return;
        }

    }

    public String getText(By by) {
        try {
            wait.until(ExpectedConditions.and(ExpectedConditions.presenceOfElementLocated(by), ExpectedConditions.visibilityOfElementLocated(by)));
            WebElement element = findElement(by);
            scrollToElementUsingJS(by);
            String msg = element.getText();
            LogsManager.info("retrieved text from Element " + by + "Successfully" + "And the message :" + msg);
            return msg;


        } catch (Exception e) {
            LogsManager.error("File To getText : " + e.getMessage());
            return null;
        }

    }

    // upload File
    public void uploadFile(By by, String filePath) {
        wait.until(ExpectedConditions.and(ExpectedConditions.presenceOfElementLocated(by), ExpectedConditions.visibilityOfElementLocated(by)));
        WebElement element = findElement(by);
        scrollToElementUsingJS(by);
        String path = System.getProperty("user.dir") + File.separator + filePath;
        element.sendKeys(path);
        LogsManager.info("File is uploaded using Locator :  " + by + "Successfully");
    }

    // function to scroll to element using js
    public void scrollToElementUsingJS(By by) {
        ((org.openqa.selenium.JavascriptExecutor) driver)
                .executeScript("""
                        arguments[0].scrollIntoView({behaviour:"auto" , block:"center", inline:"center"});""", findElement(by));
    }

    public WebElement findElement(By by) {
        try {
            return driver.findElement(by);

        } catch (Exception e) {
            return null;
        }
    }
}
