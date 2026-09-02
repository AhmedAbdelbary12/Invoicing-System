package com.invoicing.Utils;

import com.invoicing.Utils.DataManagement.PropertyReader;

public class OSUtils {
    public static OS getCurrentOS() {
        String os = PropertyReader.getProperty("os.name").toLowerCase();
        if (os.contains("win")) return OS.WINDOWS;
        if (os.contains("mac")) return OS.MAC;
        if (os.contains("nix") || os.contains("nux")) return OS.LINUX;
        return OS.OTHERS;
    }

    public enum OS {WINDOWS, LINUX, MAC, OTHERS}

}
