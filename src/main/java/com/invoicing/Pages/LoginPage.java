package com.invoicing.Pages;

import com.invoicing.Drivers.GUIDriver;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class LoginPage {
    // Locators
    private final By loginEmailLocator = By.id("emailField");
    private final By loginPasswordLocator = By.id("passwordField");
    private final By SignInButtonLocator = By.id("submitButton");
    private final By InvalidLoginErrorMsg = By.cssSelector("p[class=\"text-danger text-sm font-normal\"]");
    private final By EmptyEmailErrorMessage = By.xpath("//p[contains(@class,'text-danger') and contains(text(),'this field is required')]");
    GUIDriver driver;

    public LoginPage(GUIDriver driver) {
        this.driver = driver;
    }

    @Step("User Entered Login Credentials {email} And {password}")
    public LoginPage Login(String email, String password) {
        driver.element().type(loginEmailLocator, email);
        driver.element().type(loginPasswordLocator, password);
        driver.element().click(SignInButtonLocator);
        return this;
    }

    @Step("Verify that User Logged In Successfully")
    public NavigatorMenuPage verifyLoggedIn(String expectedURL) {
        driver.browser().waitForURL(expectedURL);
        String actualUrl = driver.browser().getURL();
        driver.softAssertion().assertEqual(actualUrl, expectedURL, "User didn't logged in successfully");
        return new NavigatorMenuPage(driver);
    }


    @Step("Verify that User Didn't Logged In Successfully with wrong Credentials")
    public LoginPage verifyInvalidLoggedIn(String expectedMsg) {
        String actualMsg = driver.element().getText(InvalidLoginErrorMsg);
        driver.softAssertion().Equal(actualMsg, expectedMsg, "User Logged in successfully");
        return this;
    }

    @Step("Verify that User Didn't Logged In Successfully with wrong Credentials")
    public LoginPage verifyErrorMessageForEmptyEmail(String expectedMsg) {
        String actualMsg = driver.element().getText(EmptyEmailErrorMessage);
        driver.softAssertion().Equal(actualMsg, expectedMsg, "User Logged in successfully");
        return this;
    }

}
