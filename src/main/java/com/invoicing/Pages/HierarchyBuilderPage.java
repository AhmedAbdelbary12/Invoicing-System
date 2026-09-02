package com.invoicing.Pages;

import com.invoicing.Drivers.GUIDriver;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class HierarchyBuilderPage {
    private final By addNewHierarchyButton = By.cssSelector("button[routerlink=\"add\"]");
    private final By NewHierarchyButton = By.xpath("//button[contains(text(),'New Hierarchy')]");
    private final By selectOrganizationName = By.cssSelector("[_ngcontent-ng-c1011832936] >[_ngcontent-ng-c2447438634]");
    private final By OrganizationName = By.xpath("//div[@_ngcontent-ng-c2447438634] //div[@_ngcontent-ng-c2447438634][2]//div[2]");
    private final By addLevelButton = By.xpath("//button[.='Add Level']");
    private final By addLevelName = By.xpath("(//*[@placeholder=\"Add Level\"])[2]");
    private final By addNote = By.xpath("//section[@_ngcontent-ng-c671773064] /div[2]/div[2]/div/div/input");
    private final By createButton = By.xpath("//div[@_ngcontent-ng-c1011832936]/button[2]");
    private final By selectOrganizationField = By.xpath("//span[contains(text(),'Select organization name')]");
    // Locators
    GUIDriver driver;

    public HierarchyBuilderPage(GUIDriver driver) {
        this.driver = driver;
    }

    // Actions
    @Step("User Click on Add New Hierarchy Button")
    public HierarchyBuilderPage clickOnAddNewHierarchyButton() {
        driver.element().click(addNewHierarchyButton);
        return this;
    }

    @Step("User Click on New Hierarchy Button")
    public HierarchyBuilderPage clickOnNewHierarchButton() {
        driver.element().click(NewHierarchyButton);
        return this;
    }

    @Step("User Selects Organization {org}")
    public HierarchyBuilderPage selectOrganization(String org) {
        driver.element().click(selectOrganizationField);
        driver.element().click(selectTheOrg(org));
        return this;
    }

    public By selectTheOrg(String orgName) {
        return By.xpath("//span[.=' " + orgName + " ']");
    }

    @Step("User Click On Add Level")
    public HierarchyBuilderPage clickOnAddLevel(String levelName) {
        driver.element().click(addLevelButton);
        driver.element().type(addLevelName, levelName);
        return this;
    }

}
