package com.invoicingTests;

import com.invoicing.APIs.userManagementAPI;
import com.invoicing.DB.DBManager;
import com.invoicing.Drivers.GUIDriver;
import com.invoicing.Pages.LoginPage;
import com.invoicing.Utils.DataManagement.JSONReader;
import com.invoicing.Utils.Logs.TimeManager;
import com.invoicing.Validations.SoftAssertion;
import io.qameta.allure.Description;
import org.testng.annotations.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.invoicing.Utils.DataManagement.PropertyReader.getProperty;

public class HierarchyBuilderTest extends BaseTest {
    private final List<String> deleteRole = new ArrayList<>();

    @BeforeClass
    public void beforeClass() {
        jsonReader = new JSONReader("hierarchyBuilder-data");
        DBManager.connect();
    }

    @AfterClass
    public void AfterClass() {
        DBManager.closeConnection();
    }


    @AfterMethod(alwaysRun = true)
    public void teardown() {
        SoftAssertion.assertAll();
        for (String roleName : deleteRole) {
            DBManager.deleteRole(roleName);
        }
        deleteRole.clear();
        //driver.quitDriver();
    }


    @BeforeMethod(alwaysRun = true)
    @Description("User Navigated to Login Page")
    public void setup() {
        driver = new GUIDriver();
        driver.browser().navigate(getProperty("baseURL"));
    }

    @Test
    @Description("User Add New Valid Single Hierarchy")
    public void addNewHierarchy() {
        File logo = new File(jsonReader.getJsonData("OrgInfo.logoPath"));
        String TimeStamp = TimeManager.getSimpleTimeStamp();

        new userManagementAPI().login(jsonReader.getJsonData("loginData.email"),
                        jsonReader.getJsonData("loginData.password")).
                createOrg(jsonReader.getJsonData("OrgInfo.DisplayName") + TimeStamp,
                        jsonReader.getJsonData("OrgInfo.Slug"),
                        jsonReader.getJsonData("OrgInfo.mobileNumber"),
                        jsonReader.getJsonData("OrgInfo.landLineNumber"),
                        jsonReader.getJsonData("OrgInfo.contactEmail"),
                        jsonReader.getJsonData("OrgInfo.contactAddress"),
                        jsonReader.getJsonData("OrgInfo.firstDayOfWeek"),
                        jsonReader.getJsonData("OrgInfo.dateFormat"),
                        jsonReader.getJsonData("OrgInfo.paymentMethodType"),
                        logo);

        new LoginPage(driver)
                .Login(jsonReader.getJsonData("loginData.email"),
                        jsonReader.getJsonData("loginData.password"))
                .verifyLoggedIn(jsonReader.getJsonData("expectedURL"))
                .NavigateToHierarchyBuilderPage()
                .clickOnAddNewHierarchyButton()
                .clickOnNewHierarchButton()
                .selectOrganization(jsonReader.getJsonData("OrgInfo.DisplayName") + TimeStamp)
                .clickOnAddLevel("Subject");
    }
    // Add new Feature


}
