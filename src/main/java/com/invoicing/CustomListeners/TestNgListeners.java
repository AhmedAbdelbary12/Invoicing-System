package com.invoicing.CustomListeners;

import com.invoicing.Drivers.WebDriverProvider;
import com.invoicing.FileUtils;
import com.invoicing.Media.ScreenShotManager;
import com.invoicing.Utils.DataManagement.PropertyReader;
import com.invoicing.Utils.Logs.LogsManager;
import com.invoicing.Utils.Reports.AllureAttachmentManager;
import com.invoicing.Utils.Reports.AllureConstants;
import com.invoicing.Utils.Reports.AllureEnvironmentManager;
import com.invoicing.Utils.Reports.AllureReportGenerator;
import com.invoicing.Validations.SoftAssertion;
import org.openqa.selenium.WebDriver;
import org.testng.*;

import java.io.File;
import java.io.IOException;

public class TestNgListeners implements IInvokedMethodListener, ITestListener, IExecutionListener, IHookable {
    @Override
    public void run(IHookCallBack callBack, ITestResult testResult) {

        callBack.runTestMethod(testResult);
        SoftAssertion.assertAll();
    }

    public void onExecutionStart() {
        LogsManager.info("Test Execution Started");
        cleanTestOutputDirectory();
        LogsManager.info("Directories are cleaned");
        createTestOutputDirectory();
        LogsManager.info("Test Execution are created");
        PropertyReader.loadProperties();
        LogsManager.info("Properties files are loaded");
        AllureEnvironmentManager.setEnvironment();
        LogsManager.info("Allure Environment is set");


    }

    public void onExecutionFinish() {
        AllureReportGenerator.generateAllureReport(false);
        AllureReportGenerator.copyHistory();
        AllureReportGenerator.generateAllureReport(true);
        AllureReportGenerator.openReport(AllureReportGenerator.renameReport());
        LogsManager.info("Allure Report is generated");


    }

    public void onTestStart(ITestResult result) {


    }

    public void onTestSuccess(ITestResult result) {
        LogsManager.info("Test successful : " + result.getName() + " Passed ");
    }

    public void onTestFailure(ITestResult result) {
        LogsManager.info("Test Case : " + result.getName() + " Is Failed");

    }

    public void onTestSkipped(ITestResult result) {
        LogsManager.info("Test Case : " + result.getName() + " Is Skipped");

    }


    public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
    }

    public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
        WebDriver driver = null;
        if (method.isTestMethod()) {

            if (testResult.getInstance() instanceof WebDriverProvider provider)
                driver = provider.getWebDriver();
            switch (testResult.getStatus()) {
                case ITestResult.SUCCESS -> {
                    try {
                        ScreenShotManager.takeScreenShot(driver, "Passed-" + testResult.getName());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                case ITestResult.FAILURE -> {
                    try {
                        ScreenShotManager.takeScreenShot(driver, "Failed-" + testResult.getName());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                case ITestResult.SKIP -> {
                    try {
                        ScreenShotManager.takeScreenShot(driver, "Skip-" + testResult.getName());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                case ITestResult.CREATED -> {
                    try {
                        ScreenShotManager.takeScreenShot(driver, "Created-" + testResult.getName());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            AllureAttachmentManager.attachLogs();
        }

    }

    // Clear Logs , screenshots , allure results
    private void cleanTestOutputDirectory() {
        FileUtils.cleanDirectory(AllureConstants.RESULTS_FOLDER.toFile());
        FileUtils.cleanDirectory(new File(ScreenShotManager.SCREENSHOT_PATH));
        FileUtils.cleanDirectory(new File("test-output/Logs"));
    }

    // Create Screenshot
    private void createTestOutputDirectory() {
        FileUtils.createDirectory(ScreenShotManager.SCREENSHOT_PATH);
    }


}
