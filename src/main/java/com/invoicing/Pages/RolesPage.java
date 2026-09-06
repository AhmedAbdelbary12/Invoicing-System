package com.invoicing.Pages;

import com.invoicing.Drivers.GUIDriver;
import com.invoicing.Utils.Logs.LogsManager;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class RolesPage {
    private final By addRoleButtonLocator = By.cssSelector("[routerlink=\"add\"]");
    private final By roleNameLocator = By.cssSelector("[formcontrolname=\"roleName\"]");
    private final By organisationNameSelection = By.xpath("//*[@formcontrolname=\"organizationName\"]");
    private final By submitButton = By.cssSelector("button[type=\"submit\"]");
    private final By addedRoleSuccessMsg = By.cssSelector("h2[_ngcontent-ng-c3919432026]");
    private final By updatedRoleSuccessMsg = By.cssSelector("h2[_ngcontent-ng-c3919432026]");
    private final By okButton = By.cssSelector("button[_ngcontent-ng-c3919432026]");
    private final By errorMessageForIgnoringRoleSelection = By.xpath("//div[.=' At least one permission must be selected in one module ']");
    private final By errorMessageForIgnoringRoleName = By.xpath("//span[.='Role Name is required']");
    private final By errorMessageForIgnoringOrganization = By.xpath("//span[.='Organization is required']");
    private final By roleNameInViewPage = By.xpath("//tbody/tr[1]/td[2]");
    private final By ActiveRoleStatusInViewPage = By.xpath("//tbody/tr[1]/td/span[.=' Active ']");
    private final By InactiveRoleStatusInViewPage = By.xpath("//tbody/tr[1]/td[4]/span[.=' Inactive ']");
    private final By duplicatedRoleMessage = By.xpath("//h2[.='Duplicate role']");
    private final By editButtonLocator = By.xpath("//tbody/tr[1]/td[5]/a");
    private final By cancelButtonLocator = By.xpath("//button[.=' Cancel ']");
    private final By cancelCreationMessage = By.xpath("//h2[.='Cancel creation?' and @_ngcontent-ng-c3919432026]");
    private final By yesButtonLocator = By.xpath("//button[@_ngcontent-ng-c3919432026 and .=' Yes ']");
    private final By updateButtonLocator = By.cssSelector("button[type=\"submit\"]");
    private final By applyButtonLocator = By.xpath("//button[.=' Apply ']");
    private final By specialCharacterIsNotAllowedMsh = By.xpath("//span[.='Special characters are not allowed.']");
    private final By userNameCannotStartWithSpace = By.xpath("//span[.='User Name cannot start with a space.']");
    private final By enterAtLeastOneLetterMessageLocator = By.xpath("//span[.='Please enter at least 1 letter.']");
    private final By maximumCharactersAllowedMessageLocator = By.xpath("//span[.='Maximum 50 characters allowed.']");
    private final By minimumCharactersAllowedMessageLocator = By.xpath("//span[.='Minimum 3 characters allowed.']");
    private final By filterToggle = By.cssSelector(".chevron[_ngcontent-ng-c1977091539]");
    private final By filterRoleName = By.id("globalSearchInput");
    private final By filterApplyButton = By.xpath("//button[@_ngcontent-ng-c1977091539 and .=' Apply ']");
    private final By numberOfElementsInViewPage = By.xpath("//tbody/tr");
    private final By activationToggle = By.cssSelector(".toggle-input[_ngcontent-ng-c509278831]:checked + .toggle-label[_ngcontent-ng-c509278831] ");
    private final By deactivateButton = By.xpath("//button[@_ngcontent-ng-c3919432026 and .=' Deactivate ']");
    public NavigatorMenuPage navigatorMenuPage;
    GUIDriver driver;

    public RolesPage(GUIDriver driver) {
        this.driver = driver;
        this.navigatorMenuPage = new NavigatorMenuPage(driver);
    }

    // Actions
    public By selectOrganizationName(String organizationName) {
        return By.xpath("//span[.=' " + organizationName + " ']");
    }

    @Step("User Add Admin Role That Have All Permissions")
    public RolesPage addAdminRole(String roleName, String organizationName, String HierarchyBuilderSelectAll, String userManagementSelectAll, String roleManagementSelectAll, String invoicingManagementSelectAll, String serviceCatalogSelectAll, String bankInfonSelectAll) {
        driver.element().click(addRoleButtonLocator);
        driver.element().type(roleNameLocator, roleName);
        driver.element().click(organisationNameSelection);
        driver.element().click(selectOrganizationName(organizationName));
        driver.element().click(selectAll(HierarchyBuilderSelectAll));
        driver.element().click(selectAll(userManagementSelectAll));
        driver.element().click(selectAll(roleManagementSelectAll));
        driver.element().click(selectAll(invoicingManagementSelectAll));
        driver.element().click(selectAll(serviceCatalogSelectAll));
        driver.element().click(selectAll(bankInfonSelectAll));
        driver.element().click(submitButton);
        LogsManager.debug("Admin role added successfully : " + roleName);
        return this;
    }

    @Step("User Add Admin Role Data That Have All Permissions")
    public RolesPage addRoleValidData(String roleName, String organisationName, String HierarchyBuilderSelectAll, String userManagementSelectAll, String roleManagementSelectAll, String invoicingManagementSelectAll, String serviceCatalogSelectAll, String bankInfonSelectAll) {
        driver.element().click(addRoleButtonLocator);
        driver.element().type(roleNameLocator, roleName);
        driver.element().click(organisationNameSelection);
        driver.element().click(selectOrganizationName(organisationName));
        driver.element().click(selectAll(HierarchyBuilderSelectAll));
        driver.element().click(selectAll(userManagementSelectAll));
        driver.element().click(selectAll(roleManagementSelectAll));
        driver.element().click(selectAll(invoicingManagementSelectAll));
        driver.element().click(selectAll(serviceCatalogSelectAll));
        driver.element().click(selectAll(bankInfonSelectAll));
        return this;
    }

    public RolesPage clickOnCancelButton() {
        driver.element().click(cancelButtonLocator);
        return this;
    }

    public By selectAll(String section) {
        return By.xpath("(//div[@_ngcontent-ng-c3576967370]/h5[.='" + section + "']//following::input)[1]");
    }

    @Step("Verify User added Role Successfully")
    public RolesPage verifyRoleIsAdded(String expectedMessage) {
        String ActualMessage = driver.element().getText(addedRoleSuccessMsg);
        driver.softAssertion().Equal(ActualMessage, expectedMessage, "Role did not added successfully");
        return this;
    }

    @Step("User Pressed OK Button After Adding Role Successfully")
    public RolesPage clickOnOkButton() {
        WebDriverWait wait = new WebDriverWait(driver.get(), Duration.ofSeconds(10));
        wait.until(ExpectedConditions.and(ExpectedConditions.visibilityOfElementLocated(okButton), ExpectedConditions.elementToBeClickable(okButton), ExpectedConditions.presenceOfElementLocated(okButton)));
        driver.element().click(okButton);
        return this;
    }

    @Step("User didn't select Any Roles")
    public RolesPage IgnoringRolesSelection(String roleName, String organizationName) {
        driver.element().click(addRoleButtonLocator);
        driver.element().type(roleNameLocator, roleName);
        driver.element().click(organisationNameSelection);
        driver.element().click(selectOrganizationName(organizationName));
        driver.element().click(submitButton);
        return this;
    }

    @Step("Verify that User could not create Role without selecting any roles")
    public RolesPage verifyUserCanNotCreateRolesWithoutRoleSelection(String ExpectedMessage) {
        String ActualMsg = driver.element().getText(errorMessageForIgnoringRoleSelection);
        driver.softAssertion().Equal(ActualMsg, ExpectedMessage, "User Could Create Role without selecting any Roles");
        return this;
    }

    @Step("User didn't Enter Role Name and selected the rest of data successfully")
    public RolesPage IgnoringRoleName(String organizationName, String HierarchyBuilderSelectAll, String userManagementSelectAll, String roleManagementSelectAll, String invoicingManagementSelectAll, String serviceCatalogSelectAll) {
        driver.element().click(addRoleButtonLocator);
        driver.element().click(organisationNameSelection);
        driver.element().click(selectOrganizationName(organizationName));
        driver.element().click(selectAll(HierarchyBuilderSelectAll));
        driver.element().click(selectAll(userManagementSelectAll));
        driver.element().click(selectAll(roleManagementSelectAll));
        driver.element().click(selectAll(invoicingManagementSelectAll));
        driver.element().click(selectAll(serviceCatalogSelectAll));
        driver.element().click(submitButton);
        return this;
    }

    @Step("User didn't Enter Organization Name and selected the rest of data successfully")
    public RolesPage IgnoringRoleNOrganization(String roleName, String HierarchyBuilderSelectAll, String userManagementSelectAll, String roleManagementSelectAll, String invoicingManagementSelectAll, String serviceCatalogSelectAll) {
        driver.element().click(addRoleButtonLocator);
        driver.element().type(roleNameLocator, roleName);
        driver.element().click(selectAll(HierarchyBuilderSelectAll));
        driver.element().click(selectAll(userManagementSelectAll));
        driver.element().click(selectAll(roleManagementSelectAll));
        driver.element().click(selectAll(invoicingManagementSelectAll));
        driver.element().click(selectAll(serviceCatalogSelectAll));
        driver.element().click(submitButton);
        return this;
    }

    @Step("Verify that User could not create Role without selecting any roles")
    public RolesPage verifyUserCanNotCreateRolesWithoutOrganization(String ExpectedMessage) {
        String ActualMsg = driver.element().getText(errorMessageForIgnoringOrganization);
        driver.softAssertion().Equal(ActualMsg, ExpectedMessage, "User Could Create Role without Entering Organization Name");
        return this;
    }


    @Step("Verify that User could not create Role without selecting any roles")
    public void verifyUserCanNotCreateRolesWithoutRoleName(String ExpectedMessage) {
        String ActualMsg = driver.element().getText(errorMessageForIgnoringRoleName);
        driver.softAssertion().Equal(ActualMsg, ExpectedMessage, "User Could Create Role without Entering Role Name");
    }

    @Step("Verify Roles is Viewed successfully")
    public void viewRole(String ExpectedRoleName, String ExpectedRoleStatus) {

        String ActualRoleName = driver.element().getText(roleNameInViewPage);
        String ActualRoleStatus = driver.element().getText(ActiveRoleStatusInViewPage);
        driver.softAssertion().Equal(ActualRoleName, ExpectedRoleName, "Role Name Added is not the Role Name Viewed");
        driver.softAssertion().Equal(ActualRoleStatus, ExpectedRoleStatus, "Role Status Added is not the Role Name Viewed");
        System.out.println(" ActualRoleName " + ActualRoleName);
        System.out.println(" ExpectedRoleName " + ExpectedRoleName);

    }

    @Step("Verify duplicated role message")
    public void duplicatedRoleMessage(String ExpectedMessage) {
        String ActualMessage = driver.element().getText(duplicatedRoleMessage);
        driver.softAssertion().Equal(ActualMessage, ExpectedMessage, "There is No Duplication");
    }

    @Step("User click on Edit Button")
    public RolesPage clickOnEditButton(String roleName) {
        WebDriverWait wait = new WebDriverWait(driver.get(), Duration.ofSeconds(10));
        wait.until(ExpectedConditions.and(ExpectedConditions.presenceOfElementLocated(getEditButtonLocator(roleName)), ExpectedConditions.elementToBeClickable(getEditButtonLocator(roleName)), ExpectedConditions.visibilityOfElementLocated(getEditButtonLocator(roleName))));
        driver.element().click(getEditButtonLocator(roleName));
        return this;
    }

    public By getEditButtonLocator(String roleNameLocator) {
        return By.xpath("//td[.='" + roleNameLocator + "']//following-sibling::td[3]/a");
    }

    @Step("User click on Un Select All For Section : {section}")
    public RolesPage removeSelectAll(String section) {
        driver.element().click(selectAll(section));
        return this;
    }

    @Step("User click on Update Button")
    public RolesPage clickOnUpdateButton() {
        WebDriverWait wait = new WebDriverWait(driver.get(), Duration.ofSeconds(10));
        wait.until(ExpectedConditions.and(ExpectedConditions.presenceOfElementLocated(updateButtonLocator), ExpectedConditions.elementToBeClickable(updateButtonLocator), ExpectedConditions.visibilityOfElementLocated(updateButtonLocator)));
        driver.element().click(updateButtonLocator);
        return this;
    }

    @Step("VClick on Apply Button Successfully")
    public RolesPage clickOnApplyButton() {
        WebDriverWait wait = new WebDriverWait(driver.get(), Duration.ofSeconds(10));
        wait.until(ExpectedConditions.and(ExpectedConditions.presenceOfElementLocated(applyButtonLocator), ExpectedConditions.elementToBeClickable(applyButtonLocator), ExpectedConditions.visibilityOfElementLocated(applyButtonLocator)));
        driver.element().click(applyButtonLocator);
        return this;
    }

    @Step("Verify User updated Role Successfully")
    public void verifyRoleIsUpdated(String expectedMessage) {
        new WebDriverWait(driver.get(), Duration.ofSeconds(10)).until(ExpectedConditions.textToBe(updatedRoleSuccessMsg, expectedMessage));
        String ActualMessage = driver.element().getText(updatedRoleSuccessMsg);
        driver.softAssertion().Equal(ActualMessage, expectedMessage, "Role did not update successfully");
    }

    @Step("Verify Special Chars are Not Allowed in Role Name")
    public void verifySpecialCharsAreNotAllowedMsg(String ExpectedMessage) {
        String ActualMessage = driver.element().getText(specialCharacterIsNotAllowedMsh);
        driver.softAssertion().Equal(ActualMessage, ExpectedMessage, "The special characters are not allowed");
    }

    @Step("Verify Spaces are Not Allowed in Role Name")
    public void verifySpacesAreNotAllowedMsg(String ExpectedMessage) {
        String ActualMessage = driver.element().getText(userNameCannotStartWithSpace);
        driver.softAssertion().Equal(ActualMessage, ExpectedMessage, "The role name cannot start with a space");
    }

    @Step("Verify Spaces are Not Allowed in Role Name")
    public void verifyEnterAtLeastOneLetterMsg(String ExpectedMessage) {
        String ActualMessage = driver.element().getText(enterAtLeastOneLetterMessageLocator);
        driver.softAssertion().Equal(ActualMessage, ExpectedMessage, "Enter At Least 1 Letter Only");
    }

    @Step("Verify Maximum Characters Allowed in Role Name")
    public void verifyMaximumCharactersAllowedMessage(String ExpectedMessage) {
        String ActualMessage = driver.element().getText(maximumCharactersAllowedMessageLocator);
        driver.softAssertion().Equal(ActualMessage, ExpectedMessage, "Maximum 50 characters allowed.");
    }

    @Step("Verify that Minimum allowed Characters for Role Name is 3 Chars")
    public void verifyMinimumAllowerCharsInRoleName(String ExpectedMessage) {
        String ActualMessage = driver.element().getText(minimumCharactersAllowedMessageLocator);
        driver.softAssertion().Equal(ActualMessage, ExpectedMessage, "Minimum 3 characters allowed.");
    }

    @Step("Popup appears to ask the user if he want actually to cancel the Role")
    public RolesPage verifyMessageToCancelRole(String ExpectedMessage) {
        String ActualMessage = driver.element().getText(cancelCreationMessage);
        driver.softAssertion().Equal(ActualMessage, ExpectedMessage, "Cancel creation message is not displayed correctly.");
        return this;
    }

    @Step("User click on Yes Button to cancel the Role")
    public void clickOnYesButton() {
        driver.element().click(yesButtonLocator);
    }

    @Step("User click on Filter Toggle to expand the filter section")
    public RolesPage clickOnFilterToggle() {
        LogsManager.debug("Current URL before filter toggle: " + driver.get().getCurrentUrl());
        driver.element().click(filterToggle);
        return this;
    }

    @Step("User enter role name in the filter input")
    public RolesPage enterRoleNameInFilter(String RoleName) {
        driver.element().type(filterRoleName, RoleName);
        return this;
    }

    @Step("User click on Apply Button")
    public RolesPage clickOnFilterApplyButton() {
        driver.element().click(filterApplyButton);
        return this;
    }

    @Step("Verify that Number of records appear after Filtration is 1 and role is filtered successfully")
    public RolesPage verifyRoleIsFiltered(String ExpectedRoleName) {
        WebDriverWait wait = new WebDriverWait(driver.get(), Duration.ofSeconds(10));
        wait.until(d -> {
            List<WebElement> roles = d.findElements(numberOfElementsInViewPage);
            LogsManager.debug("Waiting... Current count: " + roles.size());
            return roles.size() == 1;
        });

        List<WebElement> NumberOfRoles = driver.get().findElements(numberOfElementsInViewPage);
        int numberOfRoles = NumberOfRoles.size();
        LogsManager.debug("Number of Roles after filter : " + numberOfRoles);
        if (numberOfRoles == 1) {
            String ActualRoleName = driver.element().getText(roleNameInViewPage);
            driver.softAssertion().Equal(ActualRoleName, ExpectedRoleName, "Role Name Added is not the Role Name Filtered");
            LogsManager.debug("Role Name Filtered : " + ExpectedRoleName);
        } else {
            driver.softAssertion().Fail("More than one role is filtered");
            LogsManager.error("More than one role is filtered");

        }
        return this;
    }

    @Step("User Click on Activation Toggle")
    public RolesPage clickOnActivationToggle() {
        // WebDriverWait wait = new WebDriverWait(driver.get(), Duration.ofSeconds(10));
        //wait.until(ExpectedConditions.and(ExpectedConditions.presenceOfElementLocated(activationToggle), ExpectedConditions.elementToBeClickable(activationToggle), ExpectedConditions.visibilityOfElementLocated(activationToggle)));
        driver.element().click(activationToggle);
        return this;
    }

    @Step("User Click on Deactivate Button")
    public RolesPage clickOnDeactivateButton() {
        // WebDriverWait wait = new WebDriverWait(driver.get(), Duration.ofSeconds(10));
        //wait.until(ExpectedConditions.and(ExpectedConditions.presenceOfElementLocated(deactivateButton),
        //      ExpectedConditions.elementToBeClickable(deactivateButton),
        //    ExpectedConditions.visibilityOfElementLocated(deactivateButton)));
        driver.element().click(deactivateButton);
        return this;
    }

    @Step("Verify that Role Became InActive")
    public void verifyRoleIsInActive(String roleName, String ExpectedRole) {
        // WebDriverWait wait = new WebDriverWait(driver.get(), Duration.ofSeconds(15));
        By statusLocator = By.xpath("//td[normalize-space(.)='" + roleName + "']//following-sibling::td//span[contains(normalize-space(.),'Inactive')]");
        //wait.until(ExpectedConditions.visibilityOfElementLocated(statusLocator));

        String ActualRole = driver.element().getText(statusLocator);
        driver.softAssertion().Equal(ActualRole, ExpectedRole, "Role Status is not inactive.");
    }


}
