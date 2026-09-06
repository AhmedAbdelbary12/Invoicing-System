package com.invoicingTests;

import com.invoicing.APIs.userManagementAPI;
import com.invoicing.Drivers.GUIDriver;
import com.invoicing.Pages.LoginPage;
import com.invoicing.Utils.DataManagement.JSONReader;
import com.invoicing.Validations.SoftAssertion;
import io.qameta.allure.*;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.invoicing.Utils.DataManagement.PropertyReader.getProperty;

@Epic("Invoicing Application")
@Story("Login Functionality")
@Feature("Login Feature")
@Owner("Ahmed Mostafa")
@Test(groups = {"regression"})
public class LoginTest extends BaseTest {
    @BeforeClass(alwaysRun = true)
    public void beforeClass() {
        jsonReader = new JSONReader("register-data");
    }

    @AfterMethod(alwaysRun = true)
    public void teardown() {
        SoftAssertion.assertAll();
        driver.quitDriver();
    }

    @BeforeMethod(alwaysRun = true)
    @Step("User Navigated to Login Page")
    public void setup() {
        driver = new GUIDriver();
        driver.browser().navigate(getProperty("baseURL"));
    }


    @Test(priority = 1, groups = {"smoke"})
    @Description("Verify User Can Login Successfully")
    public void ValidLoginTC() {
        new LoginPage(driver)
                .Login(
                        jsonReader.getJsonData("loginData.email"),
                        jsonReader.getJsonData("loginData.password")
                )
                .verifyLoggedIn(jsonReader.getJsonData("expectedURL"));
    }

    @Test(priority = 2)
    @Description("Verify User Can not Login Successfully with Wrong Credentials")
    public void InvalidLoginWithInvalidCredentials() {
        new LoginPage(driver)
                .Login(
                        jsonReader.getJsonData("invalidLoginCredentials.email"),
                        jsonReader.getJsonData("invalidLoginCredentials.password")
                )
                .verifyInvalidLoggedIn(jsonReader.getJsonData("messages.invalidLoginErrorMessage"));
    }

    @Test(priority = 3)
    @Description("Verify User Can not Login Successfully with Empty Email")
    public void InvalidLoginWithEmptyEmail() {
        new LoginPage(driver)
                .Login(
                        jsonReader.getJsonData("invalidLoginCredentials.emptyData"),
                        jsonReader.getJsonData("loginData.password")
                )
                .verifyErrorMessageForEmptyEmail(jsonReader.getJsonData("messages.emptyEmailErrorMessage"));
    }

    @Test(priority = 4)
    @Description("Verify User Can not Login Successfully with Empty Password")
    public void InvalidLoginWithEmptyPassword() {
        new LoginPage(driver)
                .Login(
                        jsonReader.getJsonData("loginData.email"),
                        jsonReader.getJsonData("invalidLoginCredentials.emptyData")
                )
                .verifyErrorMessageForEmptyEmail(jsonReader.getJsonData("messages.emptyEmailErrorMessage"));
    }

    @Test(priority = 5)
    public void ValidLoginUsingAPIRequest() {
        new userManagementAPI().login(
                        jsonReader.getJsonData("loginData.email"),
                        jsonReader.getJsonData("loginData.password"))
                .validateUserIsLoggedIn();
    }
}
