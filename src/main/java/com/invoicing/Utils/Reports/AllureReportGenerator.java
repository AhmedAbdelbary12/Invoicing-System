package com.invoicing.Utils.Reports;

import com.invoicing.Utils.Logs.LogsManager;
import com.invoicing.Utils.Logs.TimeManager;
import com.invoicing.Utils.OSUtils;
import com.invoicing.Utils.TerminalUtils;
import org.apache.commons.io.FileUtils;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static com.invoicing.Utils.DataManagement.PropertyReader.getProperty;
import static com.invoicing.Utils.Reports.AllureConstants.HISTORY_FOLDER;
import static com.invoicing.Utils.Reports.AllureConstants.RESULT_HISTORY_FOLDER;

public class AllureReportGenerator {

    //Generate Allure Report
    public static void generateAllureReport(boolean isSingleFile) {
        Path outputFolder = isSingleFile ? AllureConstants.REPORT_PATH : AllureConstants.FULL_REPORT_PATH;
        List<String> command = new ArrayList<String>(List.of(
                AllureBinaryManager.getExecutable().toString(),
                "generate",
                AllureConstants.RESULTS_FOLDER.toString(),
                "-o", outputFolder.toString(),
                "--clean"
        ));
        if (isSingleFile) command.add("--single-file");
        TerminalUtils.executeTerminalCommand(command.toArray(new String[0]));

    }

    // Open Report
    public static void openReport(String reportFileName) {
        if (!getProperty("OpenAllureReportAfterExecution").equalsIgnoreCase("true")) return;

        Path reportPath = AllureConstants.REPORT_PATH.resolve(reportFileName);
        switch (OSUtils.getCurrentOS()) {
            case WINDOWS -> TerminalUtils.executeTerminalCommand("cmd.exe", "/c", "start", reportPath.toString());
            case MAC, LINUX -> TerminalUtils.executeTerminalCommand("open", reportPath.toString());
            default -> LogsManager.error("open Allure Report is not supported in this OS");
        }
    }

    // Copy History
    public static void copyHistory() {
        try {
            FileUtils.copyDirectory(HISTORY_FOLDER.toFile(), RESULT_HISTORY_FOLDER.toFile());

        } catch (Exception e) {
            LogsManager.error("copy Allure History Failed");
        }
    }

    public static String renameReport() {
        String newFileName = AllureConstants.REPORT_PREFIX + TimeManager.getSimpleTimeStamp() + AllureConstants.REPORT_EXTENSION;
        com.invoicing.FileUtils.renameDirectory(AllureConstants.REPORT_PATH.resolve(AllureConstants.INDEX_HTML).toString(), newFileName);
        return newFileName;
    }
}
