package com.invoicing.Validations;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;

public class HardAssertion extends BaseValidation {
    public HardAssertion(WebDriver driver) {
        super(driver);
    }

    @Override
    public void assertTrue(boolean condition, String message) {
        Assert.assertTrue(condition, message);
    }

    @Override
    public void assertFalse(boolean condition, String message) {
        Assert.assertFalse(condition, message);

    }

    @Override
    public void assertEqual(String actual, String expected, String message) {
        Assert.assertEquals(actual, expected, message);
    }

    @Override
    public void assertNotEqual(String actual, String expected, String message) {
        Assert.assertNotEquals(actual, expected, message);
    }
}
