package com.invoicing.Utils.Reports;

import com.google.common.collect.ImmutableMap;
import com.invoicing.Utils.Logs.LogsManager;

import java.io.File;

import static com.github.automatedowl.tools.AllureEnvironmentWriter.allureEnvironmentWriter;
import static com.invoicing.Utils.DataManagement.PropertyReader.getProperty;

public class AllureEnvironmentManager {
    public static void setEnvironment() {

        allureEnvironmentWriter(
                ImmutableMap.<String, String>builder()
                        .put("OS", getProperty("os.name"))
                        .put("Java Version", getProperty("java.runtime.version"))
                        .put("Browser", getProperty("RunningBrowser"))
                        .put("Execution Type", getProperty("ExecutionType"))
                        .put("Base URL", getProperty("baseURL"))
                        .put("Author", "Ahmed AbdelBary")
                        .put("Team Leader", "Smyrna Fayek")
                        .build(), String.valueOf(AllureConstants.RESULTS_FOLDER) + File.separator
        );
        LogsManager.info("Allure Environment Manager has been set");
        AllureBinaryManager.downloadAndExtract();
    }
}
