package com.infosec.accessanalysis.dal.sql;

import java.io.IOException;
import java.io.InputStream;

public class QueryLoader {

    private static QueryLoader instance = null;
    private String queryGetAllDepartments = null;

    private QueryLoader() throws IOException {
        try (InputStream is = this.getClass().getClassLoader().getResourceAsStream("sql/mssql/selectAllDepartments.sql")) {
            int length = is.available();
            byte[] bytes = new byte[length];
            is.read(bytes);
            queryGetAllDepartments = new String(bytes, "UTF-8");
        }
    }

    public static QueryLoader getInstance() throws IOException {
        if (instance == null) {
            instance = new QueryLoader();
        }

        return instance;
    }

    public String getQueryGetAllDepartments() {
        return queryGetAllDepartments;
    }
}
