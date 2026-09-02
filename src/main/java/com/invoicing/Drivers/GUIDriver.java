package com.invoicing.Drivers;

import com.invoicing.Utils.Actions.ActionBots;
import com.invoicing.Utils.Actions.ActionBotsFluentWait;
import com.invoicing.Utils.Actions.BrowserActions;
import com.invoicing.Utils.DataManagement.PropertyReader;
import com.invoicing.Utils.Logs.LogsManager;
import com.invoicing.Validations.HardAssertion;
import com.invoicing.Validations.SoftAssertion;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ThreadGuard;

public class GUIDriver {

    //public final String browser = "EDGE";
    public final String browser = PropertyReader.getProperty("RunningBrowser");
    public ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();

    public GUIDriver() {

        Browser browserType = Browser.valueOf(browser.toUpperCase());
        LogsManager.info("Starting driver for Browser" + browserType);// Browser.Edge
        AbstractFactory abstractFactory = browserType.getDriverFactory(); // New EdgeFactory EDGE.
        WebDriver driver = ThreadGuard.protect(abstractFactory.createDriver()); // New EdgeDriver
        driverThreadLocal.set(driver);
    }

    public ActionBotsFluentWait element() {
        return new ActionBotsFluentWait(get());
    }

    public ActionBots elementActionBots() {
        return new ActionBots(get());
    }

    public ActionBots actions() {
        return new ActionBots(get());
    }

    public BrowserActions browser() {
        return new BrowserActions(get());
    }

    public HardAssertion hardAssertion() {
        return new HardAssertion(get());
    }

    public SoftAssertion softAssertion() {
        return new SoftAssertion(get());
    }


    public WebDriver get() {
        return driverThreadLocal.get();
    }

    public void quitDriver() {
        get().quit();
    }


}
