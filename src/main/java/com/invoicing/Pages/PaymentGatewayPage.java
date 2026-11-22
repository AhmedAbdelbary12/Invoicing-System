package com.invoicing.Pages;

import com.invoicing.Drivers.GUIDriver;
import org.openqa.selenium.By;

public class PaymentGatewayPage {
    private final By PaymentGatewayEmailField = By.xpath("//input[@formcontrolname=\"customerEmail\"]");
    GUIDriver driver;

    public PaymentGatewayPage(GUIDriver driver) {
        this.driver = driver;
    }

    public PaymentGatewayPage EnterEmail() {
        driver.element().type(PaymentGatewayEmailField, "asd@sda.com");
        return this;
    }
}
