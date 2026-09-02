package com.invoicingTests;

import com.invoicing.Drivers.GUIDriver;
import com.invoicing.Drivers.WebDriverProvider;
import com.invoicing.Utils.DataManagement.JSONReader;
import com.invoicing.Validations.SoftAssertion;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import static com.invoicing.Utils.DataManagement.PropertyReader.getProperty;

public class BaseTest implements WebDriverProvider {

    protected GUIDriver driver;
    protected JSONReader jsonReader;


    @BeforeMethod
    public void setup() {
        driver = new GUIDriver();
        driver.browser().navigate(getProperty("baseURL"));
    }


    @AfterMethod
    public void teardown() {
        SoftAssertion.assertAll();
        // driver.quitDriver();
    }


    @Override
    public WebDriver getWebDriver() {
        return driver.get();
    }
}
