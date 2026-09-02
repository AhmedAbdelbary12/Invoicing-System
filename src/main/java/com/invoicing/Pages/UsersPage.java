package com.invoicing.Pages;

import com.invoicing.Drivers.GUIDriver;
import org.openqa.selenium.By;

public class UsersPage {
    private final By addNewUserButton = By.xpath("//button[.=' Add New User ']");
    private final By userNameInput = By.cssSelector("input[formcontrolname=\"userName\"]");
    private final By emailInput = By.cssSelector("input[formcontrolname=\"email\"]");
    private final By mobileNumberInput = By.cssSelector("input[formcontrolname=\"mobileNumber\"]");
    private final By organisationNameInput = By.cssSelector("[formcontrolname=\"tenantSlug\"] > div >div");
    private final By roleNameInput = By.cssSelector("[formcontrolname=\"roleId\"] > div >div");
    private final By credentialSentByInput = By.cssSelector("[formcontrolname=\"credentialSendBy\"] > div >div");
    GUIDriver driver;

    public UsersPage(GUIDriver driver) {
        this.driver = driver;
    }

    // Locators

    public By selectOrganisationName(String organisationName) {
        return By.xpath("//div/span[.=' " + organisationName + " ']");
    }

    public By selectRoleName(String roleName) {
        return By.xpath("//div/span[.=' " + roleName + " ']");
    }

    // Validations

    //Actions
    public UsersPage clickOnAddNewUserButton() {
        driver.element().click(addNewUserButton);
        return this;
    }

    public UsersPage enterUserName(String userName) {
        driver.element().type(userNameInput, userName);
        return this;
    }

    public UsersPage enterEmail(String email) {
        driver.element().type(emailInput, email);
        return this;
    }

    public UsersPage enterMobileNumber(String mobileNumber) {
        driver.element().type(mobileNumberInput, mobileNumber);
        return this;
    }

    public UsersPage clickOnOrganizationName() {
        driver.element().click(organisationNameInput);
        return this;
    }

    public UsersPage clickOnRoleName() {
        driver.element().click(roleNameInput);
        return this;
    }

    public UsersPage clickOnCredentialsSentBy() {
        driver.element().click(credentialSentByInput);
        return this;
    }

}
