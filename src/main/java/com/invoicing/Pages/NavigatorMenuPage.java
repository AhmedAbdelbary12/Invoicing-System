package com.invoicing.Pages;

import com.invoicing.Drivers.GUIDriver;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class NavigatorMenuPage {
    //Locators

    protected final GUIDriver driver;
    private final By homeLocator = By.cssSelector("li[routerlink=\"/home\"]");
    private final By hierarchyBuilderLocator = By.cssSelector("li[routerlink=\"/hierarchy-builder\"]");
    private final By RolesLocator = By.cssSelector("li[routerlink=\"/roles\"]");
    private final By UserLocator = By.cssSelector("li[routerlink=\"/users\"]");
    private final By serviceCatalogLocator = By.cssSelector("li[routerlink=\"/service-catalog\"]");

    public NavigatorMenuPage(GUIDriver driver) {
        this.driver = driver;
    }

    public HomePage NavigateToHomePage() {
        driver.element().click(homeLocator);
        return new HomePage(driver);
    }

    @Step("User Navigated to Hierarchy Builder Page")
    public HierarchyBuilderPage NavigateToHierarchyBuilderPage() {
        driver.element().click(hierarchyBuilderLocator);
        return new HierarchyBuilderPage(driver);
    }

    public RolesPage NavigateToRolesPagePage() {
        driver.element().click(RolesLocator);
        return new RolesPage(driver);
    }

    public UsersPage NavigateToUsersPage() {
        driver.element().click(UserLocator);
        return new UsersPage(driver);
    }

    public ServiceCatalogPage NavigateToServiceCatalogPage() {
        driver.element().click(serviceCatalogLocator);
        return new ServiceCatalogPage(driver);
    }
}
