package com.invoicing.Utils.DataManagement;

import com.invoicing.Utils.Logs.LogsManager;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.Collection;
import java.util.Properties;

public class PropertyReader {
    public static String getProperty(String key) {

        try {
            return System.getProperty(key);
        } catch (Exception e) {
            LogsManager.error("error to get property File for key" + key + e.getMessage());
            return "";
        }
    }

    public static Properties loadProperties() {

        try {
            Properties properties = new Properties();
            Collection<File> collection;
            collection = FileUtils.listFiles(new File("src/main/resources"), new String[]{"properties"}, true);
            collection.forEach(file -> {
                try {
                    properties.load(FileUtils.openInputStream(file));
                } catch (Exception e) {
                    LogsManager.error("error to load property File" + file.getName() + e.getMessage());
                }
                properties.putAll(System.getProperties());
                System.getProperties().putAll(properties);
            });
            return properties;
        } catch (Exception e) {
            LogsManager.error("error to load property File" + e.getMessage());
            return null;
        }
    }
}
