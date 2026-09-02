package com.invoicingTests;

import com.invoicing.DB.DBManager;
import com.invoicing.Drivers.GUIDriver;
import com.invoicing.Pages.LoginPage;
import com.invoicing.Utils.DataManagement.JSONReader;
import com.invoicing.Validations.SoftAssertion;
import io.qameta.allure.Step;
import org.testng.annotations.*;

import java.util.ArrayList;
import java.util.List;

import static com.invoicing.Utils.DataManagement.PropertyReader.getProperty;

public class UsersTests extends BaseTest {
    private final List<String> deleteRole = new ArrayList<>();

    @AfterMethod(alwaysRun = true)
    public void teardown() {
        SoftAssertion.assertAll();
        for (String roleName : deleteRole) {
            DBManager.deleteRole(roleName);
        }
        deleteRole.clear();
        driver.quitDriver();
    }


    @BeforeMethod(alwaysRun = true)
    @Step("User Navigated to Login Page")
    public void setup() {
        driver = new GUIDriver();
        driver.browser().navigate(getProperty("baseURL"));
    }

    @BeforeClass
    public void beforeClass() {
        jsonReader = new JSONReader("Users-data");
        DBManager.connect();
    }

    @AfterClass
    public void AfterClass() {
        DBManager.closeConnection();
    }

    @Test
    public void test() {
        new LoginPage(driver)
                .Login(jsonReader.getJsonData("loginData.email"),
                        jsonReader.getJsonData("loginData.password"))
                .verifyLoggedIn(jsonReader.getJsonData("loginData.expectedURL"))
                .NavigateToUsersPage()
                .clickOnAddNewUserButton();
    }
}
