package com.infosec.accessanalysis.api.rest;

import java.io.FileInputStream;
import java.util.Properties;

public class Configuration {
    private static String dburl;

    static {
        Properties props = new Properties();
        try {
            try (FileInputStream is = new FileInputStream("application.properties")) {
                props.load(is);
            }
        } catch (Exception ignored) {
        }
        dburl = props.getProperty("accessanalysis.dburl");
    }

    public static String getDbUrl() {
        return dburl;
    }
}
