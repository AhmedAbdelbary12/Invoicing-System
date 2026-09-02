package com.invoicing.Utils;

import com.invoicing.Utils.Logs.LogsManager;

import java.io.IOException;

public class TerminalUtils {
    public static void executeTerminalCommand(String... commandParts) {
        try {
            Process process = Runtime.getRuntime().exec(commandParts);
            int exitCode = process.waitFor();
            if (exitCode != 0) {
                LogsManager.error("Command Failed with exit code" + exitCode);
            }
        } catch (IOException | InterruptedException e) {
            LogsManager.error("Failed to execute command: " + String.join(" " + commandParts), e.getMessage());
        }
    }
}
