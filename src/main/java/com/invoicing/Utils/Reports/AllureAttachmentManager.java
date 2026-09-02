package com.invoicing.Utils.Reports;

import com.invoicing.Utils.Logs.LogsManager;
import io.qameta.allure.Allure;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LoggerContext;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

public class AllureAttachmentManager {

    public static void attachScreenShot(String name, String path) {
        try {
            Path screenShot = Path.of(path);
            if (Files.exists(screenShot)) {
                Allure.addAttachment(name, Files.newInputStream(screenShot));
            } else {
                LogsManager.error("screenshot not found");
            }
        } catch (Exception e) {
            LogsManager.error("Could not add attachment");
        }
    }

    public static void attachLogs() {
        try {
            LogManager.shutdown();
            File logFile = new File(LogsManager.LOGS_PATH + File.separator + "logs.log");
            ((LoggerContext) LogManager.getContext(false)).reconfigure();

            if (logFile.exists()) {
                Allure.attachment("logs.log", Files.readString(logFile.toPath()));
            }
        } catch (Exception e) {
            LogsManager.error("Error Attaching logs");
        }
    }

}
