package com.invoicing;

import com.invoicing.Utils.DataManagement.PropertyReader;
import com.invoicing.Utils.Logs.LogsManager;

import java.io.File;

import static org.apache.commons.io.FileUtils.copyFile;

public class FileUtils {
    private static final String USER_DIR = PropertyReader.getProperty("user.dir") + File.separator;

    // Renaming
    public static void renameDirectory(String oldName, String newName) {
        try {
            var targetFile = new File(oldName);
            String targetDirectory = targetFile.getParentFile().getAbsolutePath();
            File newFile = new File(targetDirectory + File.separator + newName);
            if (!targetFile.getPath().equals(newFile.getPath())) {
                copyFile(targetFile, newFile);
                org.apache.commons.io.FileUtils.deleteQuietly(targetFile);
                LogsManager.info("Target File Path " + oldName + " was renamed to " + newName + " ");
            } else {
                LogsManager.info("Target File Path " + oldName + " already has desired name " + newName + " ");
            }
        } catch (Exception e) {
            LogsManager.error(e.getMessage());
        }
    }

    public static void main(String[] args) {
        System.out.println(USER_DIR);
        String oldFileName = "NEWtest.txt";
        String newFileName = "NEWtests.txt";
        renameDirectory(oldFileName, newFileName);
    }

    // Create Directory

    public static void createDirectory(String path) {
        try {

            File file = new File(USER_DIR + path);
            if (!file.exists()) {
                file.mkdir();
                LogsManager.info("Directory is Created Successfully");
            }
        } catch (Exception e) {
            LogsManager.error("Failed to Create Directory: " + e.getMessage());
        }
    }

    // Clear Directory
    public static void cleanDirectory(File file) {
        try {
            org.apache.commons.io.FileUtils.deleteQuietly(file);
        } catch (Exception e) {
            LogsManager.error("Error while deleting file: " + file.getAbsolutePath());
        }

    }
}
