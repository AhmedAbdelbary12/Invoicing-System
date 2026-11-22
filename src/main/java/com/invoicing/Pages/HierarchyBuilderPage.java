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
    private final By addSecondLevelName = By.xpath("(//*[@placeholder=\"Add Level\"])[3]");
    private final By typeInNode = By.xpath("(//*[@placeholder=\"Add Node\"])");
    private final By addNode = By.xpath("//span[.='Add Node']");
    private final By addNodeLevelTwo = By.xpath("(//span[.='Add Node'])[2]");
    private final By createButton = By.xpath("//div[@_ngcontent-ng-c1011832936]/button[2]");
    private final By selectOrganizationField = By.xpath("//span[contains(text(),'Select organization name')]");
    private final By levelOneNodeOne = By.xpath("(//div[@_ngcontent-ng-c671773064]/following::input[@_ngcontent-ng-c671773064])[3]");
    private final By levelOneNodeTwo = By.xpath("(//div[@_ngcontent-ng-c671773064]/following::input[@_ngcontent-ng-c671773064])[4]");
    private final By levelTOneNodeThree = By.xpath("(//div[@_ngcontent-ng-c671773064]/following::input[@_ngcontent-ng-c671773064])[5]");
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
    public HierarchyBuilderPage typeLevelName(String levelName) {
        driver.element().click(addLevelButton);
        driver.element().type(addLevelName, levelName);
        return this;
    }

    @Step("User Click On Add Level")
    public HierarchyBuilderPage typeSecondLevelName(String levelName) {
        driver.element().type(addSecondLevelName, levelName);
        return this;
    }

    @Step("User Click On Add Level")
    public HierarchyBuilderPage typeInFirstNode(String node) {
        driver.element().typeAndPressENTER(typeInNode, node);
        return this;
    }

    public HierarchyBuilderPage clickOnSecondNode() {
        driver.element().click(addNode);
        return this;
    }

    public HierarchyBuilderPage clickOnThirdNode() {
        driver.element().click(addNode);
        return this;
    }

    public HierarchyBuilderPage typeInSecondNode(String node) {
        driver.element().typeAndPressENTER(typeInNode, node);
        return this;
    }

    public HierarchyBuilderPage typeInThirdNode(String node) {
        driver.element().typeAndPressENTER(typeInNode, node);
        return this;
    }

    public HierarchyBuilderPage clickOnAddLevel() {
        driver.element().click(addLevelButton);
        return this;
    }

    public HierarchyBuilderPage clickOnFirstNodeLevel1() {
        driver.element().click(levelOneNodeOne);
        return this;
    }

    public HierarchyBuilderPage clickOnSecondeNodeLevel1() {
        driver.element().click(levelOneNodeTwo);
        return this;
    }

    public HierarchyBuilderPage clickOnThirdNodeLevel1() {
        driver.element().click(levelTOneNodeThree);
        return this;
    }

    public HierarchyBuilderPage clickOnAddNodeLevelTwo() {
        driver.element().click(addNodeLevelTwo);
        return this;
    }

}
