package com.invoicing.Utils.Reports;

import com.invoicing.Utils.Logs.LogsManager;
import com.invoicing.Utils.OSUtils;
import com.invoicing.Utils.TerminalUtils;
import org.jsoup.Jsoup;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class AllureBinaryManager {
    public static void downloadAndExtract() {
        try {
            String version = LazyHolder.Version;
            LogsManager.warn("Downloading Allure Report from " + version);
            Path extractionDir = Paths.get(AllureConstants.EXTENSION_DIR.toString(), "allure-" + version);
            if (Files.exists(extractionDir)) {
                LogsManager.info("Allure Binary Already exists");
                return;
            }

            if (!OSUtils.getCurrentOS().equals(OSUtils.OS.WINDOWS)) {
                TerminalUtils.executeTerminalCommand("chmod", "u+x", AllureConstants.USER_DIR.toString());
            }


            Path zipPath = downloadZIP(version);
            extractZIP(zipPath);
            LogsManager.info("Allure Binary Extraction Successful");


            if (!OSUtils.getCurrentOS().equals(OSUtils.OS.WINDOWS)) {
                TerminalUtils.executeTerminalCommand("chmod", "u+x", getExecutable().toString());
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // Download zip file to Allure
    private static Path downloadZIP(String version) {
        try {
            String url = AllureConstants.ALLURE_ZIP_BASE_URL + version + "/allure-commandline-" + version + ".zip";
            Path zipFile = Paths.get(AllureConstants.EXTENSION_DIR.toString(), "allure-" + version + ".zip");
            if (!Files.exists(zipFile)) {
                Files.createDirectories(AllureConstants.EXTENSION_DIR);
                try (BufferedInputStream in = new BufferedInputStream(new URI(url).toURL().openStream());
                     OutputStream out = Files.newOutputStream(zipFile)) {
                    in.transferTo(out);

                } catch (Exception e) {
                    LogsManager.error("Invalid URL for Allure Zip File");
                }
            }
            return zipFile;

        } catch (Exception e) {
            LogsManager.error("Invalid URL to download ZIP file " + e.getMessage());
            return Paths.get("");
        }
    }

    // Extract Allure zip file
    private static void extractZIP(Path zipPath) throws IOException {
        try (ZipInputStream zipInputStream = new ZipInputStream(Files.newInputStream(zipPath))) {
            ZipEntry entry;
            while ((entry = zipInputStream.getNextEntry()) != null) {
                Path filePath = Paths.get(AllureConstants.EXTENSION_DIR.toString(), File.separator, entry.getName());
                if (entry.isDirectory()) {
                    Files.createDirectories(filePath);
                } else {
                    Files.createDirectories(filePath.getParent());
                    Files.copy(zipInputStream, filePath);
                }
            }
        } catch (Exception e) {
            LogsManager.error("Error Extracting ZIP File");
        }
    }

    public static Path getExecutable() {
        String version = LazyHolder.Version;
        //C:\Users\Ahmed.Abdelbary\.m2\repository\allure\allure-2.44.0\bin
        Path binaryPath = Paths.get(AllureConstants.EXTENSION_DIR.toString(), "allure-" + version, "bin", "allure");
        return OSUtils.getCurrentOS() == OSUtils.OS.WINDOWS
                ? binaryPath.resolveSibling(binaryPath.getFileName() + ".bat")
                : binaryPath;
    }

    // Get Allure version
    private static class LazyHolder {
        static final String Version = resolveVersion();
        //static final String Version = "2.34.1";

        private static String resolveVersion() {

            try {
                String url = Jsoup.connect("https://github.com/allure-framework/allure2/releases/latest")
                        .followRedirects(true)
                        .timeout(5000)
                        .execute()
                        .url()
                        .toString();

                return url.substring(url.lastIndexOf('/') + 1);

            } catch (Exception e) {
                LogsManager.warn("Couldn't resolve latest Allure version. Using default 2.34.1");
                return "2.34.1";
            }

            /*
            try {
                String url = Jsoup.connect("https://github.com/allure-framework/allure2/releases/latest")
                        .followRedirects(true).execute().url().toString();
                return url.split("/tag/")[1];
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            */

        }


    }
}
