package com.invoicingTests;

import com.invoicing.DB.DBCreateInvoiceManager;
import com.invoicing.DB.DBManager;
import com.invoicing.Drivers.GUIDriver;
import com.invoicing.Pages.LoginPage;
import com.invoicing.Utils.DataManagement.JSONReader;
import com.invoicing.Utils.Logs.LogsManager;
import com.invoicing.Utils.Logs.TimeManager;
import com.invoicing.Validations.SoftAssertion;
import io.qameta.allure.Description;
import org.testng.annotations.*;

import java.util.ArrayList;
import java.util.List;

import static com.invoicing.Utils.DataManagement.PropertyReader.getProperty;

public class InvoiceTest extends BaseTest {
    private final List<String> deleteInvoice = new ArrayList<>();

    @BeforeClass(alwaysRun = true)
    public void beforeClass() {
        jsonReader = new JSONReader("invoice-data");
        DBManager.connect();
    }

    @AfterClass(alwaysRun = true)
    public void AfterClass() {
        DBManager.closeConnection();
    }

    @AfterMethod(alwaysRun = true)
    public void teardown() {
        try {
            SoftAssertion.assertAll();
        } finally {
            try {
                for (String invoiceName : deleteInvoice) {
                    try {
                        DBCreateInvoiceManager.deleteInvoice(invoiceName);
                    } catch (Exception e) {
                        LogsManager.error("Failed to delete invoice: " + invoiceName + " -> " + e.getMessage());
                    }
                }
            } finally {
                deleteInvoice.clear();
                //driver.quitDriver();
            }
        }
    }

    @BeforeMethod(alwaysRun = true)
    @Description("User Navigated to Login Page")
    public void setup() {
        driver = new GUIDriver();
        driver.browser().navigate(getProperty("MerchantBaseURL"));
    }

    @Test
    @Description("Merchant Create Invoice Successfully with Payment Method : Card Payment Link")
    public void createInvoice_CardPaymentLink() {
        String TimeStamp = TimeManager.getSimpleTimeStamp();
        new LoginPage(driver)
                .Login(jsonReader.getJsonData("loginData.MerchantAlahlyMomknEmail"),
                        jsonReader.getJsonData("loginData.MerchantAlahlyMomknPassword"))
                .verifyLoggedIn(jsonReader.getJsonData("expectedURL"))
                .NavigateToInvoicePage()
                .clickOnCreateInvoice()
                .clickOnCreateNewInvoice()
                .selectDepartmentLevel()
                .selectTechnologyEntity()
                .clickOnDueDateCalendar()
                //TODO Comment Under Line
                .typeOrganizationReferenceNumber(jsonReader.getJsonData("invoiceData.organizationReferenceNumber"))
                .typePaymentDetails(jsonReader.getJsonData("invoiceData.paymentDetails"))
                .typeClientName(jsonReader.getJsonData("invoiceData.clientName") + TimeStamp)
                .typeMobileNumber(jsonReader.getJsonData("invoiceData.mobileNumber"))
                .enterNationalID(jsonReader.getJsonData("invoiceData.nationalID"))
                .selectServiceName()
                .clickOnCreateInvoiceButton()
                .clickOnOkButton()
                .checkIfInvoiceCreatedSuccessfully(jsonReader.getJsonData("invoiceData.clientName") + TimeStamp,
                        jsonReader.getJsonData("paymentMethod.cardPaymentLinkBeforeSelectPaymentMethod"))
                .clickOnViewButton()
                .clickOnPayButton()
                .selectPaymentMethod(jsonReader.getJsonData("paymentMethod.cardPaymentLink"))
                .checkIfInvoiceCreatedSuccessfullyAfterAddingPaymentMethod(jsonReader.getJsonData("paymentMethod.cardPaymentLinkAfterSelectPaymentMethod"))
                .getInvoiceLink(jsonReader.getJsonData("invoiceData.clientName") + TimeStamp)
                .EnterEmail();


        //deleteInvoice.add(jsonReader.getJsonData("invoiceData.clientName") + TimeStamp);
    }

    @Test
    @Description("Merchant Create Invoice Successfully with Payment Method : Reference Payment Link")
    public void createInvoice_ReferencePaymentLink() {
        String TimeStamp = TimeManager.getSimpleTimeStamp();
        new LoginPage(driver)
                .Login(jsonReader.getJsonData("loginData.MerchantAlahlyMomknEmail"),
                        jsonReader.getJsonData("loginData.MerchantAlahlyMomknPassword"))
                .verifyLoggedIn(jsonReader.getJsonData("expectedURL"))
                .NavigateToInvoicePage()
                .clickOnCreateInvoice()
                .clickOnCreateNewInvoice()
                .selectDepartmentLevel()
                .selectTechnologyEntity()
                .clickOnDueDateCalendar()
                .typePaymentDetails(jsonReader.getJsonData("invoiceData.paymentDetails"))
                .typeClientName(jsonReader.getJsonData("invoiceData.clientName") + TimeStamp)
                .typeMobileNumber(jsonReader.getJsonData("invoiceData.mobileNumber"))
                .enterNationalID(jsonReader.getJsonData("invoiceData.nationalID"))
                .selectServiceName()
                .clickOnCreateInvoiceButton()
                .clickOnOkButton()
                .checkIfInvoiceCreatedSuccessfully(jsonReader.getJsonData("invoiceData.clientName") + TimeStamp,
                        jsonReader.getJsonData("paymentMethod.cardPaymentLinkBeforeSelectPaymentMethod"))
                .clickOnViewButton()
                .clickOnPayButton()
                .selectPaymentMethod(jsonReader.getJsonData("paymentMethod.referencePaymentLink"))
                .checkIfInvoiceCreatedSuccessfullyAfterAddingPaymentMethod(jsonReader.getJsonData("paymentMethod.referencePaymentLinkAfterSelectPaymentMethod"));
        //deleteInvoice.add(jsonReader.getJsonData("invoiceData.clientName") + TimeStamp);
    }


}
