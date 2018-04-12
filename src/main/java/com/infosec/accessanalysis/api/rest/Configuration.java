package com.infosec.accessanalysis.api.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.util.Properties;
import java.net.URI;

public class Configuration {
    private final static Logger logger = LoggerFactory.getLogger(Configuration.class);

    private final static String CONFIGURATION_FILE = "application.properties";
    private final static String DATABASE_URL_PROPERTY = "accessanalysis.dburl";

    private static String dburl = null;
    private static String sqlQueryResourcePrefix = null;

    static {
        try (FileInputStream is = new FileInputStream(CONFIGURATION_FILE)) {
            Properties props = new Properties();
            props.load(is);
            dburl = props.getProperty(DATABASE_URL_PROPERTY);

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
            logger.error("Could mot load configuration from " + CONFIGURATION_FILE + ".");
            System.exit(1);
        }
    }

    public static String getDbUrl() {
        return dburl;
    }

    public static String getSqlQueryResourcePrefix() {
        return sqlQueryResourcePrefix;
    }
}
