package com.invoicing.Validations;

import com.invoicing.Utils.Logs.LogsManager;
import org.openqa.selenium.WebDriver;
import org.testng.asserts.SoftAssert;

public class SoftAssertion extends BaseValidation {
    private static SoftAssert softAssert = new SoftAssert();
    private static boolean used = false;

    public SoftAssertion(WebDriver driver) {
        super(driver);
    }

    public SoftAssertion() {

    }

    public static void assertAll() {
        if (!used) return;

        try {
            softAssert.assertAll();
        } catch (AssertionError e) {
            LogsManager.error("Assertion Failed :", e.getMessage());
            throw e;
        } finally {
            softAssert = new SoftAssert();
            used = false;
        }
    }

    @Override
    public void assertTrue(boolean condition, String message) {
        used = true;
        softAssert.assertTrue(condition, message);
    }

    @Override
    public void assertFalse(boolean condition, String message) {
        used = true;
        softAssert.assertFalse(condition, message);

    }

    @Override
    public void assertEqual(String actual, String expected, String message) {
        used = true;
        softAssert.assertEquals(actual, expected, message);

    }

    @Override
    public void assertNotEqual(String actual, String expected, String message) {
        used = true;
        softAssert.assertNotEquals(actual, expected, message);
    }

    public void checkStatusCode(int statusCode, int i, String message) {
        used = true;
        softAssert.assertEquals(statusCode, i, message);
    }
}
