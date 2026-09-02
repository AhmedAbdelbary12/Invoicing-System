package com.invoicing.Media;

import com.invoicing.Utils.Logs.LogsManager;
import com.invoicing.Utils.Logs.TimeManager;
import com.invoicing.Utils.Reports.AllureAttachmentManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;

import java.io.File;
import java.io.IOException;

public class ScreenShotManager {
    public static final String SCREENSHOT_PATH = "test-output/screenshots";

    public static void takeScreenShot(WebDriver driver, String screenShotName) throws IOException {
        LogsManager.info("===== takeScreenShot() called =====");
        LogsManager.info("Driver = " + driver);

        try {

            File screenshotSrc = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            LogsManager.info("Temporary Screenshot = " + screenshotSrc.getAbsolutePath());
            File screenshotFile = new File(SCREENSHOT_PATH + File.separator + screenShotName + "-" + TimeManager.getSimpleTimeStamp() + ".png");
            LogsManager.info("Final Path = " + screenshotFile.getAbsolutePath());
            FileUtils.copyFile(screenshotSrc, screenshotFile);
            LogsManager.info("Saved Screenshot = " + screenshotFile.getAbsolutePath());

            AllureAttachmentManager.attachScreenShot(screenShotName, screenshotFile.getAbsolutePath());
            LogsManager.info("Captured Screenshot successfully");
        } catch (Exception e) {
            LogsManager.error("Failed to Take Screenshot" + e.getMessage());
        }
    }

    // Take screenshot to element
    public static void takeScreenShotToElement(WebDriver driver, By by) throws IOException {

        try {
            WebElement element = driver.findElement(by);
            String ariaName = element.getAccessibleName();

            File screenshotSrc = element.getScreenshotAs(OutputType.FILE);
            File screenshotFile = new File(SCREENSHOT_PATH + File.separator + ariaName + "-" + TimeManager.getSimpleTimeStamp() + ".png");
            FileUtils.copyFile(screenshotSrc, screenshotFile);

            LogsManager.info("Captured Screenshot successfully to the Element : " + by + " ");
        } catch (Exception e) {
            LogsManager.error("Failed to Take Screenshot successfully to the Element : " + by + " " + e.getMessage());
        }
    }

}
