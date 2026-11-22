package com.invoicing.Pages;

import com.invoicing.DB.DBInvoiceManager;
import com.invoicing.Drivers.GUIDriver;
import com.invoicing.Utils.Logs.LogsManager;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WindowType;

public class InvoicePage {

    private final By invoiceLocator = By.xpath("//span[.=' Create Invoice ']");
    private final By createInvoiceLocator = By.xpath("//button[.=' Create New Invoice33 ']");
    private final By levelLocator = By.xpath("//span[.='Select level']/parent::div");
    private final By billingEntityLocator = By.xpath("//span[.='Enter billing entity']/parent::div");
    private final By departmentLevelLocator = By.xpath("//span[normalize-space(.) ='Departments']");
    private final By technologyEntityLocator = By.xpath("//span[.=' Tec ']");
    private final By dueDateCalendarLocator = By.xpath("//input[@placeholder=\"Select invoice due date\"]");
    private final By dayLocator = By.xpath("(//button[contains(@class,'text-[#263238]') and not(contains(@class,'cursor-not-allowed'))])[3]");
    private final By applyDueDateLocator = By.xpath("//button[.=' Apply ']");
    private final By organizationPurchaseOrder = By.xpath("//input[@formcontrolname=\"organizationInvoiceReference\"]");
    private final By paymentDetails = By.xpath("//textarea[@formcontrolname=\"paymentDetails\"]");
    private final By clientName = By.xpath("//input[@formcontrolname=\"clientName\"]");
    private final By mobileNumberLocator = By.xpath("//input[@formcontrolname=\"mobileNumber\"]");
    private final By billsServiceNameLocator = By.xpath("//span[.=' bills ']/parent::div");
    private final By serviceNameLocator = By.xpath("//span[.='Select service name']/parent::div");
    private final By paymentMethodLocator = By.xpath("//span[.='Select payment method']/parent::div");
    private final By cardPaymentLink = By.xpath("//span[.=' Card Payment Link ']/parent::div");
    private final By payByMomknRep = By.xpath("//span[.=' Pay by momkn rep ']/parent::div");
    private final By payByProvider = By.xpath("//span[.=' Pay by provider ']/parent::div");
    private final By referencePaymentLink = By.xpath("//span[.=' Reference Payment Link ']/parent::div");
    private final By createInvoiceButton = By.xpath("//button[.=' Create Invoice ']");
    private final By OnOkButton = By.xpath("//button[.=' Ok ']");
    private final By clientNameInViewPage = By.xpath("//tbody /tr[1]/td[6]");
    private final By paymentMethodInViewPage = By.xpath("//tbody /tr[1]/td[9]");
    private final By nationalIDLocator = By.xpath("//input[@formcontrolname=\"nationalId\"]");
    private final By invoiceOptionLocator = By.xpath("//tbody/tr[1]/td[14]/button");
    private final By viewButton = By.xpath("//tbody/tr[1]/td[14]/div//a/span[.='View']");
    private final By payButton = By.xpath("//button[normalize-space(.) ='Pay']");
    private final By paymentMethodFieldInInvoiceView = By.xpath("//span[.='Select payment method']/parent::div");
    private final By confirmButton = By.xpath("//button[normalize-space(.)='Confirm']");
    private final By okButtonAfterConfirmPayment = By.xpath("//app-action-dialog//ancestor::button[normalize-space(.)='Ok']");
    GUIDriver driver;

    public InvoicePage(GUIDriver driver) {
        this.driver = driver;
    }

    @Step("Merchant Clicked on Create Invoice in Side Menu")
    public InvoicePage clickOnCreateInvoice() {
        driver.element().click(invoiceLocator);
        return this;
    }

    @Step("Merchant Clicked on Create Invoice Button")
    public InvoicePage clickOnCreateNewInvoice() {
        driver.element().click(createInvoiceLocator);
        return this;
    }

    @Step("Merchant Clicked on Level and selected Department Level")
    public InvoicePage selectDepartmentLevel() {
        driver.element().click(levelLocator);
        driver.element().click(departmentLevelLocator);
        return this;
    }

    @Step("Merchant Selected Technology Entity")
    public InvoicePage selectTechnologyEntity() {
        driver.element().click(billingEntityLocator);
        driver.element().click(technologyEntityLocator);

        return this;
    }

    @Step("Merchant Clicked on Due Date Calendar")
    public InvoicePage clickOnDueDateCalendar() {
        driver.element().click(dueDateCalendarLocator);
        driver.element().click(dayLocator);
        driver.element().click(applyDueDateLocator);
        return this;
    }

    public InvoicePage typeOrganizationReferenceNumber(String organizationPurchaseOrders) {
        driver.element().type(organizationPurchaseOrder, organizationPurchaseOrders);
        return this;
    }

    @Step("Merchant Typed Payment Details")
    public InvoicePage typePaymentDetails(String paymentDetailsText) {
        driver.element().type(paymentDetails, paymentDetailsText);
        return this;
    }

    @Step("Merchant Typed Client Name : {clientNameText}")
    public InvoicePage typeClientName(String clientNameText) {
        driver.element().type(clientName, clientNameText);
        return this;
    }

    @Step("Merchant Typed Mobile Number : {mobileNumber}")
    public InvoicePage typeMobileNumber(String mobileNumber) {
        driver.element().type(mobileNumberLocator, mobileNumber);
        return this;
    }

    @Step("Merchant Selected Service Name")
    public InvoicePage selectServiceName() {
        driver.element().click(serviceNameLocator);
        driver.element().click(billsServiceNameLocator);
        return this;
    }

    @Step("Merchant Selected Payment Method")
    public InvoicePage selectPaymentMethod(String paymentMethod) {
        driver.element().click(paymentMethodFieldInInvoiceView);
        driver.element().click(getPaymentMethodLocator(paymentMethod));
        driver.element().click(confirmButton);
        driver.element().click(okButtonAfterConfirmPayment);
        return this;
    }

    @Step("Merchant Clicked on Create Invoice Button")
    public InvoicePage clickOnCreateInvoiceButton() {
        driver.element().click(createInvoiceButton);
        return this;
    }

    @Step("Merchant Clicked on Ok Button")
    public InvoicePage clickOnOkButton() {
        driver.element().click(OnOkButton);
        return this;
    }

    @Step("Check if the Invoice is created successfully with Expected Name : {ExpectedName} and Expected Payment Method : {ExpectedPaymentMethod}")
    public InvoicePage checkIfInvoiceCreatedSuccessfully(String ExpectedName, String ExpectedPaymentMethod) {
        String ActualName = driver.element().getText(clientNameInViewPage);
        String ActualPaymentMethod = driver.element().getText(paymentMethodInViewPage);
        driver.softAssertion().Equal(ActualName, ExpectedName, "Invoice Didn't Created Successfully");
        driver.softAssertion().Equal(ActualPaymentMethod, ExpectedPaymentMethod, "Invoice Didn't Created Successfully");
        LogsManager.debug("Invoice Created Successfully with ActualName Client Name: " + ActualName);
        LogsManager.debug("Invoice Created Successfully with ExpectedName Client Name: " + ExpectedName);
        LogsManager.debug("Invoice Created Successfully with ActualPaymentMethod Before Select Method : " + ActualPaymentMethod);
        LogsManager.debug("Invoice Created Successfully with ExpectedPaymentMethod Before Select Method: " + ExpectedPaymentMethod);
        return this;
    }

    public By getPaymentMethodLocator(String paymentMethod) {
        return By.xpath("//span[normalize-space(.) ='" + paymentMethod + "']/parent::div");
    }

    public InvoicePage enterNationalID(String nationalID) {
        driver.element().type(nationalIDLocator, nationalID);
        return this;
    }

    public InvoicePage clickOnViewButton() {
        driver.element().click(invoiceOptionLocator);
        driver.element().click(viewButton);
        return this;
    }

    public InvoicePage clickOnPayButton() {
        driver.element().click(payButton);
        return this;
    }

    public InvoicePage checkIfInvoiceCreatedSuccessfullyAfterAddingPaymentMethod(String ExpectedPaymentMethod) {
        String ActualPaymentMethod = driver.element().getText(paymentMethodInViewPage);
        driver.softAssertion().Equal(ActualPaymentMethod, ExpectedPaymentMethod, "Invoice Didn't Created Successfully");
        LogsManager.debug("Invoice Created Successfully with ActualPaymentMethod After Select Method: " + ActualPaymentMethod);
        LogsManager.debug("Invoice Created Successfully with ExpectedPaymentMethod After Select Method: " + ExpectedPaymentMethod);
        return this;
    }

    public PaymentGatewayPage getInvoiceLink(String clientNamee) {
        String parentWindow;
        String paymentWindow;

        LogsManager.debug("Payment Link:" + DBInvoiceManager.getPaymentLink(clientNamee));
        String paymentLink = DBInvoiceManager.getPaymentLink(clientNamee);
        LogsManager.debug("paymentLink : " + paymentLink);

        parentWindow = driver.get().getWindowHandle();
        System.out.println("Parent ::: " + parentWindow);

        driver.get().switchTo().newWindow(WindowType.TAB);
        paymentWindow = driver.get().getWindowHandle();
        LogsManager.debug("Parent: " + parentWindow + " | Payment: " + paymentWindow);


        driver.browser().navigate(paymentLink);


        return new PaymentGatewayPage(driver);


    }


}

