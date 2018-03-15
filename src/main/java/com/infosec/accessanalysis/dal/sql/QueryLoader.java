package com.infosec.accessanalysis.dal.sql;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class QueryLoader {

    private static QueryLoader instance = null;
    private Map<String, String> queries = new HashMap<>();

    private QueryLoader() {
    }

    public static QueryLoader getInstance() throws IOException {
        if (instance == null) {
            instance = new QueryLoader();
        }

        return instance;
    }

    public String getQuery(String dbProvider, String queryName) throws IOException {
        String result = null;
        final String key = dbProvider +":" + queryName;

        if (queries.containsKey(key)) {
            result = queries.get(key);
        } else {
            try (InputStream is = this.getClass().getClassLoader().getResourceAsStream(
                    String.format("sql/%s/%s.sql", dbProvider, queryName))) {
                int length = is.available();
                byte[] bytes = new byte[length];
                is.read(bytes);

                result = new String(bytes, "UTF-8");
                queries.put(key, result);
            }
        }
        return result;
    }
}
