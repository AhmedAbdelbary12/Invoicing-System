package com.invoicing.Drivers;

import com.invoicing.Utils.DataManagement.PropertyReader;
import com.invoicing.Utils.Logs.LogsManager;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URI;

public class ChromeFactory extends AbstractFactory {
    public ChromeOptions getChromeOptions() {
        ChromeOptions options = new ChromeOptions();


        if (PropertyReader.getProperty("ExecutionType").equalsIgnoreCase("local")) {
            options.addArguments("--disable-gpu");
            options.addArguments("--disable-notifications");
            options.addArguments("--disable-popup-blocking");
            options.addArguments("--no-sandbox");
            options.addArguments("start-maximized");
            options.setPageLoadStrategy(PageLoadStrategy.EAGER);
        }
        if (PropertyReader.getProperty("ExecutionType").equalsIgnoreCase("localHeadless") ||
                PropertyReader.getProperty("ExecutionType").equalsIgnoreCase("Remote")) {
            options.addArguments("--headless");
            options.addArguments("--window-position=-2400,-2400");
            options.addArguments("--disable-gpu");
            options.addArguments("--disable-notifications");
            options.addArguments("--disable-popup-blocking");
            options.addArguments("--no-sandbox");
            options.setPageLoadStrategy(PageLoadStrategy.EAGER);
        }
        return options;

    }


    @Override
    public WebDriver createDriver() {
        if (PropertyReader.getProperty("ExecutionType").equalsIgnoreCase("localHeadless") ||
                PropertyReader.getProperty("ExecutionType").equalsIgnoreCase("local")) {
            return new ChromeDriver(getChromeOptions());
        } else if (PropertyReader.getProperty("ExecutionType").equalsIgnoreCase("Remote")) {
            try {
                return new RemoteWebDriver(
                        new URI("http://" + PropertyReader.getProperty("remotHost") + ":" + PropertyReader.getProperty("remotPort") + "/wd/hub").toURL(), getChromeOptions()
                );
            } catch (Exception e) {
                LogsManager.error("Failed To Create RemoteWebDriver");
                throw new RuntimeException("Failed To Create RemoteWebDriver");
            }
        } else {
            LogsManager.error("Invalid Execution Type" + PropertyReader.getProperty("ExecutionType"));
            throw new IllegalArgumentException("invalid ExecutionType" + PropertyReader.getProperty("ExecutionType"));
        }

    }


}
