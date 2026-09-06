package com.invoicingTests;

import com.invoicing.APIs.userManagementAPI;
import com.invoicing.DB.DBManager;
import com.invoicing.Drivers.GUIDriver;
import com.invoicing.Pages.LoginPage;
import com.invoicing.Utils.DataManagement.JSONReader;
import com.invoicing.Utils.Faker.FakerManager;
import com.invoicing.Utils.Logs.LogsManager;
import com.invoicing.Utils.Logs.TimeManager;
import com.invoicing.Validations.SoftAssertion;
import io.qameta.allure.Description;
import org.testng.annotations.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.invoicing.Utils.DataManagement.PropertyReader.getProperty;

@Test(groups = {"regression"})
public class HierarchyBuilderTest extends BaseTest {
    private final List<String> deleteOrgInfo = new ArrayList<>();


    @BeforeClass(alwaysRun = true)
    public void beforeClass() {
        jsonReader = new JSONReader("hierarchyBuilder-data");
        DBManager.connect();
    }

    @AfterClass(alwaysRun = true)
    public void AfterClass() {
        DBManager.closeConnection();
    }

    @AfterMethod(alwaysRun = true)
    public void teardown() {
        SoftAssertion.assertAll();
        for (String orgInfo : deleteOrgInfo) {
            DBManager.deleteOrgInfo(orgInfo);
        }
        deleteOrgInfo.clear();
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

        String randomSlug = FakerManager.getFaker().name().firstName().toLowerCase().substring(0, 3);
        LogsManager.debug("Slug Added: " + randomSlug);
        File logo = new File(jsonReader.getJsonData("OrgInfo.logoPath"));
        String TimeStamp = TimeManager.getSimpleTimeStamp();

        // API Level
        new userManagementAPI().
                login(jsonReader.getJsonData("loginData.email"), jsonReader.getJsonData("loginData.password"))
                .createOrg(jsonReader.getJsonData("OrgInfo.DisplayName") + TimeStamp,
                        randomSlug,
                        jsonReader.getJsonData("OrgInfo.mobileNumber"),
                        jsonReader.getJsonData("OrgInfo.landLineNumber"),
                        jsonReader.getJsonData("OrgInfo.contactEmail"),
                        jsonReader.getJsonData("OrgInfo.contactAddress"),
                        jsonReader.getJsonData("OrgInfo.firstDayOfWeek"),
                        jsonReader.getJsonData("OrgInfo.dateFormat"),
                        jsonReader.getJsonData("OrgInfo.paymentMethodType"),
                        logo)
                .validateOrgIsCreated();

        LogsManager.debug("Added OrgName: " + jsonReader.getJsonData("OrgInfo.DisplayName") + TimeStamp);

        // UI Level
        new LoginPage(driver)
                .Login(jsonReader.getJsonData("loginData.email"),
                        jsonReader.getJsonData("loginData.password"))
                .verifyLoggedIn(jsonReader.getJsonData("expectedURL"))
                .NavigateToHierarchyBuilderPage()
                .clickOnAddNewHierarchyButton()
                .clickOnNewHierarchButton()
                .selectOrganization(jsonReader.getJsonData("OrgInfo.DisplayName") + TimeStamp)
                .typeLevelName(jsonReader.getJsonData("HierarchyInfo.Level1Name"))
                .typeNode(jsonReader.getJsonData("HierarchyInfo.Node"));
        deleteOrgInfo.add(jsonReader.getJsonData("OrgInfo.DisplayName") + TimeStamp);
    }


}
