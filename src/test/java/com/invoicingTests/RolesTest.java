package com.invoicingTests;

import com.invoicing.APIs.userManagementAPI;
import com.invoicing.DB.DBManager;
import com.invoicing.Drivers.GUIDriver;
import com.invoicing.Pages.LoginPage;
import com.invoicing.Pages.RolesPage;
import com.invoicing.Utils.DataManagement.JSONReader;
import com.invoicing.Utils.Logs.LogsManager;
import com.invoicing.Utils.Logs.TimeManager;
import com.invoicing.Validations.SoftAssertion;
import io.qameta.allure.*;
import org.testng.annotations.*;

import java.util.ArrayList;
import java.util.List;

import static com.invoicing.Utils.DataManagement.PropertyReader.getProperty;

@Epic("Invoicing Application")
@Story("Roles Functionality")
@Feature("Roles Feature")
@Owner("Ahmed Mostafa")
public class RolesTest extends BaseTest {
    private final List<String> deleteRole = new ArrayList<>();
    //String TIME_STAMP = String.valueOf(TimeManager.getTimeStamp());
    String TIME_STAMP = TimeManager.getSimpleTimeStamp();

    private String getTimeStamp() {
        return TimeManager.getSimpleTimeStamp();
    }

    private String getUniqueSecondTimeStamp() {
        return TimeManager.getUniqueSimpleTimeStamp();
    }

    @BeforeClass
    public void beforeClass() {
        jsonReader = new JSONReader("roles-data");
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
        driver.quitDriver();
    }


    @BeforeMethod(alwaysRun = true)
    @Step("User Navigated to Login Page")
    public void setup() {
        driver = new GUIDriver();
        driver.browser().navigate(getProperty("baseURL"));
    }

    @Test(priority = 1)
    @Description("User Add Admin Role that have all permissions")
    public void addAdminRole() {
        String TIME_STAMP = TimeManager.getSimpleTimeStamp();
        new LoginPage(driver)
                .Login(
                        jsonReader.getJsonData("loginData.email"),
                        jsonReader.getJsonData("loginData.password")
                )
                .verifyLoggedIn(jsonReader.getJsonData("expectedURL"))
                .NavigateToRolesPagePage()
                .addAdminRole(jsonReader.getJsonData("roleName") + TIME_STAMP,
                        jsonReader.getJsonData("selectedOrganizationName"),
                        jsonReader.getJsonData("selectAll.HierarchyBuilder"),
                        jsonReader.getJsonData("selectAll.UserManagement"),
                        jsonReader.getJsonData("selectAll.RoleManagement"),
                        jsonReader.getJsonData("selectAll.InvoicingManagement"),
                        jsonReader.getJsonData("selectAll.ServiceCatalog"),
                        jsonReader.getJsonData("selectAll.BankInfo"))
                .verifyRoleIsAdded(jsonReader.getJsonData("expectedSuccessAddedRole"))
                .clickOnOkButton();
        LogsManager.debug("Added Admin Role : " + jsonReader.getJsonData("roleName") + TIME_STAMP);

        //Delete Role
        deleteRole.add(jsonReader.getJsonData("roleName") + TIME_STAMP);
    }

    @Test(priority = 2)
    @Description("User Try to add Role without selecting Any permissions")
    public void addingRoleWithoutSelectionRoles() {
        new LoginPage(driver)
                .Login(
                        jsonReader.getJsonData("loginData.email"),
                        jsonReader.getJsonData("loginData.password")
                )
                .verifyLoggedIn(jsonReader.getJsonData("expectedURL"))
                .NavigateToRolesPagePage()
                .IgnoringRolesSelection(
                        jsonReader.getJsonData("roleName") + getTimeStamp(),
                        jsonReader.getJsonData("selectedOrganizationName"))
                .verifyUserCanNotCreateRolesWithoutRoleSelection(jsonReader.getJsonData("ErrorMessages.ExpectedMessageForIgnoringSelectAnyRole"));

    }

    @Test(priority = 3)
    @Description("User Try to add Role without Entering Role Name")
    public void addingRoleWithoutAddingRoleName() {
        new LoginPage(driver)
                .Login(
                        jsonReader.getJsonData("loginData.email"),
                        jsonReader.getJsonData("loginData.password")
                )
                .verifyLoggedIn(jsonReader.getJsonData("expectedURL"))
                .NavigateToRolesPagePage()
                .IgnoringRoleName(
                        jsonReader.getJsonData("selectedOrganizationName"),
                        jsonReader.getJsonData("selectAll.HierarchyBuilder"),
                        jsonReader.getJsonData("selectAll.UserManagement"),
                        jsonReader.getJsonData("selectAll.RoleManagement"),
                        jsonReader.getJsonData("selectAll.InvoicingManagement"),
                        jsonReader.getJsonData("selectAll.ServiceCatalog"))
                .verifyUserCanNotCreateRolesWithoutRoleName(
                        jsonReader.getJsonData("ErrorMessages.ExpectedMessageForIgnoringRoleName")
                );

    }

    @Test(priority = 4)
    @Description("User Try to add Role without Entering Organization Name")
    public void addingRoleWithoutAddingOrganization() {
        new LoginPage(driver)
                .Login(
                        jsonReader.getJsonData("loginData.email"),
                        jsonReader.getJsonData("loginData.password")
                )
                .verifyLoggedIn(jsonReader.getJsonData("expectedURL"))
                .NavigateToRolesPagePage()
                .IgnoringRoleNOrganization(jsonReader.getJsonData("roleName") + getTimeStamp(),
                        jsonReader.getJsonData("selectAll.HierarchyBuilder"),
                        jsonReader.getJsonData("selectAll.UserManagement"),
                        jsonReader.getJsonData("selectAll.RoleManagement"),
                        jsonReader.getJsonData("selectAll.InvoicingManagement"),
                        jsonReader.getJsonData("selectAll.ServiceCatalog"))
                .verifyUserCanNotCreateRolesWithoutOrganization(
                        jsonReader.getJsonData("ErrorMessages.ExpectedMessageForIgnoringOrganization")
                );
    }

    @Test(priority = 5)
    @Description("User Try to add Role and check that Role is Viewed successfully")
    public void verifyThatRoleIsViewedSuccessfully() {
        String TIME_STAMP = TimeManager.getSimpleTimeStamp();
        new LoginPage(driver)
                .Login(
                        jsonReader.getJsonData("loginData.email"),
                        jsonReader.getJsonData("loginData.password")
                )
                .verifyLoggedIn(jsonReader.getJsonData("expectedURL"))
                .NavigateToRolesPagePage()
                .addAdminRole(jsonReader.getJsonData("roleName") + TIME_STAMP,
                        jsonReader.getJsonData("selectedOrganizationName"),
                        jsonReader.getJsonData("selectAll.HierarchyBuilder"),
                        jsonReader.getJsonData("selectAll.UserManagement"),
                        jsonReader.getJsonData("selectAll.RoleManagement"),
                        jsonReader.getJsonData("selectAll.InvoicingManagement"),
                        jsonReader.getJsonData("selectAll.ServiceCatalog"),
                        jsonReader.getJsonData("selectAll.BankInfo"))
                .verifyRoleIsAdded(jsonReader.getJsonData("expectedSuccessAddedRole"))
                .clickOnOkButton()
                .viewRole(jsonReader.getJsonData("roleName") + TIME_STAMP,
                        jsonReader.getJsonData("RoleStatus"));
        deleteRole.add(jsonReader.getJsonData("roleName") + TIME_STAMP);


    }

    @Test(priority = 8)
    @Description("User Try to add Role and check that Role is Viewed successfully Through API Calls")
    public void verifyThatRoleIsViewedSuccessfullyThroughAPI() {
        String TIME_STAMP_UNIQUE = TimeManager.getSimpleTimeStamp();
        new userManagementAPI().login(
                jsonReader.getJsonData("loginData.email"),
                jsonReader.getJsonData("loginData.password")
        );
        new userManagementAPI().createRoleAPI(jsonReader.getJsonData("roleName") + TIME_STAMP_UNIQUE)
                .validateRoleIsCreated();

        new LoginPage(driver)
                .Login(
                        jsonReader.getJsonData("loginData.email"),
                        jsonReader.getJsonData("loginData.password")
                )
                .verifyLoggedIn(jsonReader.getJsonData("expectedURL"))
                .NavigateToRolesPagePage()
                .viewRole(jsonReader.getJsonData("roleName") + TIME_STAMP_UNIQUE,
                        jsonReader.getJsonData("RoleStatus"));
        deleteRole.add(jsonReader.getJsonData("roleName") + TIME_STAMP_UNIQUE);
    }

    @Test(priority = 6)
    @Description("User Try to add Role Successfully and add the same Role again and check system will refuse that Scenario")
    public void addSameRoleTwice() {
        new LoginPage(driver)
                .Login(
                        jsonReader.getJsonData("loginData.email"),
                        jsonReader.getJsonData("loginData.password")
                )
                .verifyLoggedIn(jsonReader.getJsonData("expectedURL"))
                .NavigateToRolesPagePage()
                .addAdminRole(jsonReader.getJsonData("roleName") + TIME_STAMP,
                        jsonReader.getJsonData("selectedOrganizationName"),
                        jsonReader.getJsonData("selectAll.HierarchyBuilder"),
                        jsonReader.getJsonData("selectAll.UserManagement"),
                        jsonReader.getJsonData("selectAll.RoleManagement"),
                        jsonReader.getJsonData("selectAll.InvoicingManagement"),
                        jsonReader.getJsonData("selectAll.ServiceCatalog"),
                        jsonReader.getJsonData("selectAll.BankInfo"))
                .verifyRoleIsAdded(jsonReader.getJsonData("expectedSuccessAddedRole"))
                .clickOnOkButton();
        new RolesPage(driver)
                .addAdminRole(jsonReader.getJsonData("roleName") + TIME_STAMP,
                        jsonReader.getJsonData("selectedOrganizationName"),
                        jsonReader.getJsonData("selectAll.HierarchyBuilder"),
                        jsonReader.getJsonData("selectAll.UserManagement"),
                        jsonReader.getJsonData("selectAll.RoleManagement"),
                        jsonReader.getJsonData("selectAll.InvoicingManagement"),
                        jsonReader.getJsonData("selectAll.ServiceCatalog"),
                        jsonReader.getJsonData("selectAll.BankInfo"))
                .duplicatedRoleMessage(jsonReader.getJsonData("ErrorMessages.duplicatedRoleMessage"));
        deleteRole.add(jsonReader.getJsonData("roleName") + TIME_STAMP);
    }

    @Test(priority = 7)
    @Description("User Try to Edit Role Successfully By unselecting User Management Permissions")
    public void editRoleByUnSelectPermissions() {
        String TIME_STAMP = TimeManager.getSimpleTimeStamp();
        new LoginPage(driver)
                .Login(
                        jsonReader.getJsonData("loginData.email"),
                        jsonReader.getJsonData("loginData.password")
                )
                .verifyLoggedIn(jsonReader.getJsonData("expectedURL"))
                .NavigateToRolesPagePage()
                .addAdminRole(jsonReader.getJsonData("roleName") + TIME_STAMP,
                        jsonReader.getJsonData("selectedOrganizationName"),
                        jsonReader.getJsonData("selectAll.HierarchyBuilder"),
                        jsonReader.getJsonData("selectAll.UserManagement"),
                        jsonReader.getJsonData("selectAll.RoleManagement"),
                        jsonReader.getJsonData("selectAll.InvoicingManagement"),
                        jsonReader.getJsonData("selectAll.ServiceCatalog"),
                        jsonReader.getJsonData("selectAll.BankInfo"))
                .verifyRoleIsAdded(jsonReader.getJsonData("expectedSuccessAddedRole"))
                .clickOnOkButton()
                .clickOnEditButton(jsonReader.getJsonData("roleName") + TIME_STAMP)
                .removeSelectAll(jsonReader.getJsonData("selectAll.UserManagement"))
                .clickOnUpdateButton()
                .clickOnApplyButton()
                .verifyRoleIsUpdated(jsonReader.getJsonData("expectedSuccessUpdatedRole"));
        deleteRole.add(jsonReader.getJsonData("roleName") + TIME_STAMP);

    }

    @Test(priority = 9)
    @Description("User Try to Add Role but he add special Chars in Role Name")
    public void addRoleNameWithSpecialCharacters() {
        new LoginPage(driver)
                .Login(
                        jsonReader.getJsonData("loginData.email"),
                        jsonReader.getJsonData("loginData.password")
                )
                .verifyLoggedIn(jsonReader.getJsonData("expectedURL"))
                .NavigateToRolesPagePage()
                .addAdminRole(jsonReader.getJsonData("roleNameWithSpecialCharacters") + TIME_STAMP,
                        jsonReader.getJsonData("selectedOrganizationName"),
                        jsonReader.getJsonData("selectAll.HierarchyBuilder"),
                        jsonReader.getJsonData("selectAll.UserManagement"),
                        jsonReader.getJsonData("selectAll.RoleManagement"),
                        jsonReader.getJsonData("selectAll.InvoicingManagement"),
                        jsonReader.getJsonData("selectAll.ServiceCatalog"),
                        jsonReader.getJsonData("selectAll.BankInfo"))
                .verifySpecialCharsAreNotAllowedMsg(jsonReader.getJsonData("ErrorMessages.specialCharactersNotAllowed"));
    }

    @Test(priority = 10)
    @Description("User Try to Add Role but he add spaces in Role Name")
    public void addRoleNameWithSpaces() {
        new LoginPage(driver)
                .Login(
                        jsonReader.getJsonData("loginData.email"),
                        jsonReader.getJsonData("loginData.password")
                )
                .verifyLoggedIn(jsonReader.getJsonData("expectedURL"))
                .NavigateToRolesPagePage()
                .addAdminRole(jsonReader.getJsonData("roleNameWithSpaces") + TIME_STAMP,
                        jsonReader.getJsonData("selectedOrganizationName"),
                        jsonReader.getJsonData("selectAll.HierarchyBuilder"),
                        jsonReader.getJsonData("selectAll.UserManagement"),
                        jsonReader.getJsonData("selectAll.RoleManagement"),
                        jsonReader.getJsonData("selectAll.InvoicingManagement"),
                        jsonReader.getJsonData("selectAll.ServiceCatalog"),
                        jsonReader.getJsonData("selectAll.BankInfo"))
                .verifySpacesAreNotAllowedMsg(jsonReader.getJsonData("ErrorMessages.spacesNotAllowed"));
    }

    @Test(priority = 11, groups = {"BugBefore"})
    @Description("User Try to Add Role but he add only Numbers in Role Name")
    public void enterOnlyNumbersInRoleNameInput() {
        new LoginPage(driver)
                .Login(
                        jsonReader.getJsonData("loginData.email"),
                        jsonReader.getJsonData("loginData.password")
                )
                .verifyLoggedIn(jsonReader.getJsonData("expectedURL"))
                .NavigateToRolesPagePage()
                .addAdminRole(jsonReader.getJsonData("roleNameWithNumbers"),
                        jsonReader.getJsonData("selectedOrganizationName"),
                        jsonReader.getJsonData("selectAll.HierarchyBuilder"),
                        jsonReader.getJsonData("selectAll.UserManagement"),
                        jsonReader.getJsonData("selectAll.RoleManagement"),
                        jsonReader.getJsonData("selectAll.InvoicingManagement"),
                        jsonReader.getJsonData("selectAll.ServiceCatalog"),
                        jsonReader.getJsonData("selectAll.BankInfo"))
                .verifyEnterAtLeastOneLetterMsg(jsonReader.getJsonData("ErrorMessages.enterAtLeastOneLetter"));
    }

    //TODO: Fix this Test Case because it is not working as expected (Need to Make Sure From Team)
    @Test(priority = 12)
    @Description("User Add Role but he add 50 Characters in Role Name")
    public void enterMaximumCharactersInRoleNameInput() {
        new LoginPage(driver)
                .Login(
                        jsonReader.getJsonData("loginData.email"),
                        jsonReader.getJsonData("loginData.password")
                )
                .verifyLoggedIn(jsonReader.getJsonData("expectedURL"))
                .NavigateToRolesPagePage()
                .addAdminRole(jsonReader.getJsonData("roleNameWithMaximumCharacters"),
                        jsonReader.getJsonData("selectedOrganizationName"),
                        jsonReader.getJsonData("selectAll.HierarchyBuilder"),
                        jsonReader.getJsonData("selectAll.UserManagement"),
                        jsonReader.getJsonData("selectAll.RoleManagement"),
                        jsonReader.getJsonData("selectAll.InvoicingManagement"),
                        jsonReader.getJsonData("selectAll.ServiceCatalog"),
                        jsonReader.getJsonData("selectAll.BankInfo"))
                .verifyMaximumCharactersAllowedMessage(jsonReader.getJsonData("ErrorMessages.maximumCharactersAllowed"));
        deleteRole.add(jsonReader.getJsonData("roleNameWithMaximumCharacters"));

    }

    @Test(priority = 13)
    @Description("User Add Admin Role and Enter Just 3 Chars in Role Name Input")
    public void addAdminRoleWithRoleNameThreeChars() {
        new LoginPage(driver)
                .Login(
                        jsonReader.getJsonData("loginData.email"),
                        jsonReader.getJsonData("loginData.password")
                )
                .verifyLoggedIn(jsonReader.getJsonData("expectedURL"))
                .NavigateToRolesPagePage()
                .addAdminRole(jsonReader.getJsonData("roleNameWithThreeChars"),
                        jsonReader.getJsonData("selectedOrganizationName"),
                        jsonReader.getJsonData("selectAll.HierarchyBuilder"),
                        jsonReader.getJsonData("selectAll.UserManagement"),
                        jsonReader.getJsonData("selectAll.RoleManagement"),
                        jsonReader.getJsonData("selectAll.InvoicingManagement"),
                        jsonReader.getJsonData("selectAll.ServiceCatalog"),
                        jsonReader.getJsonData("selectAll.BankInfo"))
                .verifyRoleIsAdded(jsonReader.getJsonData("expectedSuccessAddedRole"))
                .clickOnOkButton();
        deleteRole.add(jsonReader.getJsonData("roleNameWithThreeChars"));
    }

    @Test(priority = 14)
    @Description("User Add Admin Role and Enter Less Than 3 Chars in Role Name Input")
    public void addAdminRoleWithRoleNameLessThreeChars() {
        new LoginPage(driver)
                .Login(
                        jsonReader.getJsonData("loginData.email"),
                        jsonReader.getJsonData("loginData.password")
                )
                .verifyLoggedIn(jsonReader.getJsonData("expectedURL"))
                .NavigateToRolesPagePage()
                .addAdminRole(jsonReader.getJsonData("roleNameWithLessThreeChars") + getUniqueSecondTimeStamp(),
                        jsonReader.getJsonData("selectedOrganizationName"),
                        jsonReader.getJsonData("selectAll.HierarchyBuilder"),
                        jsonReader.getJsonData("selectAll.UserManagement"),
                        jsonReader.getJsonData("selectAll.RoleManagement"),
                        jsonReader.getJsonData("selectAll.InvoicingManagement"),
                        jsonReader.getJsonData("selectAll.ServiceCatalog"),
                        jsonReader.getJsonData("selectAll.BankInfo"))
                .verifyMinimumAllowerCharsInRoleName(jsonReader.getJsonData("ErrorMessages.minimumCharactersAllowed"));
    }

    @Test(priority = 15)
    @Description("User Add Admin Role with all valid data and cancel it")
    public void cancelRoleCreation() {
        new LoginPage(driver)
                .Login(
                        jsonReader.getJsonData("loginData.email"),
                        jsonReader.getJsonData("loginData.password")
                )
                .verifyLoggedIn(jsonReader.getJsonData("expectedURL"))
                .NavigateToRolesPagePage()
                .addRoleValidData(jsonReader.getJsonData("roleName") + getTimeStamp(),
                        jsonReader.getJsonData("selectedOrganizationName"),
                        jsonReader.getJsonData("selectAll.HierarchyBuilder"),
                        jsonReader.getJsonData("selectAll.UserManagement"),
                        jsonReader.getJsonData("selectAll.RoleManagement"),
                        jsonReader.getJsonData("selectAll.InvoicingManagement"),
                        jsonReader.getJsonData("selectAll.ServiceCatalog"),
                        jsonReader.getJsonData("selectAll.BankInfo"))
                .clickOnCancelButton()
                .verifyMessageToCancelRole(jsonReader.getJsonData("cancelMessage"))
                .clickOnYesButton();
    }

    @Test(priority = 16)
    @Description("User add role and filter it by role name")
    public void RolesFilterByRoleName() {
        String TIME_STAMP = TimeManager.getSimpleTimeStamp();
        new LoginPage(driver)
                .Login(
                        jsonReader.getJsonData("loginData.email"),
                        jsonReader.getJsonData("loginData.password")
                )
                .verifyLoggedIn(jsonReader.getJsonData("expectedURL"))
                .NavigateToRolesPagePage()
                .addAdminRole(jsonReader.getJsonData("roleName") + TIME_STAMP,
                        jsonReader.getJsonData("selectedOrganizationName"),
                        jsonReader.getJsonData("selectAll.HierarchyBuilder"),
                        jsonReader.getJsonData("selectAll.UserManagement"),
                        jsonReader.getJsonData("selectAll.RoleManagement"),
                        jsonReader.getJsonData("selectAll.InvoicingManagement"),
                        jsonReader.getJsonData("selectAll.ServiceCatalog"),
                        jsonReader.getJsonData("selectAll.BankInfo"))
                .clickOnOkButton()
                .clickOnFilterToggle()
                .enterRoleNameInFilter(jsonReader.getJsonData("roleName") + TIME_STAMP)
                .clickOnFilterApplyButton()
                .verifyRoleIsFiltered(jsonReader.getJsonData("roleName") + TIME_STAMP);

        deleteRole.add(jsonReader.getJsonData("roleName") + TIME_STAMP);

    }

    @Test(priority = 17)
    @Description("User DeActivate Role and check that have been deactivated")
    public void deActivateRole() {
        String TIME_STAMP = TimeManager.getSimpleTimeStamp();
        new LoginPage(driver)
                .Login(
                        jsonReader.getJsonData("loginData.email"),
                        jsonReader.getJsonData("loginData.password")
                )
                .verifyLoggedIn(jsonReader.getJsonData("expectedURL"))
                .NavigateToRolesPagePage()
                .addAdminRole(jsonReader.getJsonData("roleName") + TIME_STAMP,
                        jsonReader.getJsonData("selectedOrganizationName"),
                        jsonReader.getJsonData("selectAll.HierarchyBuilder"),
                        jsonReader.getJsonData("selectAll.UserManagement"),
                        jsonReader.getJsonData("selectAll.RoleManagement"),
                        jsonReader.getJsonData("selectAll.InvoicingManagement"),
                        jsonReader.getJsonData("selectAll.ServiceCatalog"),
                        jsonReader.getJsonData("selectAll.BankInfo"))
                .verifyRoleIsAdded(jsonReader.getJsonData("expectedSuccessAddedRole"))
                .clickOnOkButton()
                .clickOnEditButton(jsonReader.getJsonData("roleName") + TIME_STAMP)
                .clickOnActivationToggle()
                .clickOnDeactivateButton()
                .clickOnUpdateButton()
                .clickOnApplyButton()
                .clickOnOkButton()
                .verifyRoleIsInActive(
                        jsonReader.getJsonData("roleName") + TIME_STAMP
                        , jsonReader.getJsonData("InActiveStatus"));
        LogsManager.debug("Added Admin Role : " + jsonReader.getJsonData("roleName") + TIME_STAMP);
        deleteRole.add(jsonReader.getJsonData("roleName") + TIME_STAMP);
    }

}
