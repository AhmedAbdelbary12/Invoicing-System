package com.invoicing.Utils.Actions;

import com.invoicing.Utils.Logs.LogsManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BrowserActions {
    private final WebDriver driver;
    WebDriverWait wait;
    WaitManager waitManager;

    public BrowserActions(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        waitManager = new WaitManager(driver);
    }

    public String getURL() {
        String url = driver.getCurrentUrl();
        LogsManager.info("Current URL : " + url);
        return url;
    }

    public void waitForURL(String expectedURL) {
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.urlToBe(expectedURL));
        LogsManager.info("Current URL : " + driver.getCurrentUrl());
    }


    public void maximize() {
        driver.manage().window().maximize();
    }

    public void navigate(String url) {
        driver.get(url);
        LogsManager.info("Navigated To URL : " + url);
    }


    public void close() {
        driver.close();
    }

    public void acceptAlert() {
        wait.until(ExpectedConditions.alertIsPresent());
        driver.switchTo().alert().accept();
    }

    public void typeInAlert(String text) {
        wait.until(ExpectedConditions.alertIsPresent());
        driver.switchTo().alert().sendKeys(text);
    }

    public void dismissAlert(String text) {
        wait.until(ExpectedConditions.alertIsPresent());
        driver.switchTo().alert().dismiss();
    }


    // Frames


}
