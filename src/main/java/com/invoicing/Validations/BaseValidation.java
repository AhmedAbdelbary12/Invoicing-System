package com.invoicing.Validations;

import com.invoicing.Utils.Actions.ActionBots;
import com.invoicing.Utils.Actions.WaitManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public abstract class BaseValidation {
    protected WebDriver driver;
    protected ActionBots actionBots;
    protected WaitManager waitManager;
    WebDriverWait wait;

    public BaseValidation(WebDriver driver) {
        this.driver = driver;
        this.actionBots = new ActionBots(driver);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        this.waitManager = new WaitManager(driver);
    }

    public BaseValidation() {

    }

    protected abstract void assertTrue(boolean condition, String message);

    protected abstract void assertFalse(boolean condition, String message);

    protected abstract void assertEqual(String actual, String expected, String message);

    protected abstract void assertNotEqual(String actual, String expected, String message);

    public void Equal(String actual, String expected, String message) {
        assertEqual(actual, expected, message);
    }

    public void NotEqual(String actual, String expected, String message) {
        assertNotEqual(actual, expected, message);
    }

    public void isElementVisible(By by) {

        waitManager.fluentWait().until(d -> {
            try {
                WebElement element = actionBots.findElement(by);
                element.isDisplayed();
                return true;
            } catch (Exception e) {
                return false;
            }

        });
        assertTrue(actionBots.findElement(by).isDisplayed(), "Element is not visible :" + by + " ");
    }

    public void checkUrl(String expectedURL) {
        String actualURL = driver.getCurrentUrl();
        assertEqual(actualURL, expectedURL, "The Expected URl you Entered is Invalid : " + expectedURL + " ");
    }

    public void verifyPageTitle(String expectedTitle) {
        String actualTitle = driver.getTitle();
        assertEqual(actualTitle, expectedTitle, "The Expected Title you Entered is Invalid : " + expectedTitle + " ");
    }

    public void AssertContainText(String actual, String expectedText) {
        assertTrue(actual.contains(expectedText), "The Expected Text you Entered is Invalid : " + expectedText + " " + "The Actual Is : " + actual);
    }

    public void Fail(String message) {
        assertTrue(false, message);
    }
}
