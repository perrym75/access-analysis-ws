package com.infosec.accessanalysis.api.rest;

import java.io.FileInputStream;
import java.util.Properties;
import java.net.URI;

public class Configuration {
    private final static String dburl;
    private static String sqlQueryResourcePrefix;

    static {
        Properties props = new Properties();
        try {
            try (FileInputStream is = new FileInputStream("application.properties")) {
                props.load(is);
            }
        } catch (Exception ignored) {
        }
        dburl = props.getProperty("accessanalysis.dburl");

        try {
            String scheme = new URI(new URI(dburl).getSchemeSpecificPart()).getScheme();
            switch (scheme) {
                case "postgresql":
                    sqlQueryResourcePrefix = "sql/postgresql/";
                    break;
                case "sqlserver":
                    sqlQueryResourcePrefix = "sql/mssql/";
                    break;
                default:
                    sqlQueryResourcePrefix = "sql/mssql/";
                    break;
            }
        } catch(Exception ignored) {
            sqlQueryResourcePrefix = "sql/mssql/";
        }
    }

    public static String getDbUrl() {
        return dburl;
    }

    public static String getSqlQueryResourcePrefix() {
        return sqlQueryResourcePrefix;
    }
}
