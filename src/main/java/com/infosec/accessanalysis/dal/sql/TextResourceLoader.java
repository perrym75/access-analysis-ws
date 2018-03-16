package com.infosec.accessanalysis.dal.sql;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class TextResourceLoader {

    private static TextResourceLoader instance = null;
    private static Map<String, String> queries = new HashMap<>();

    private TextResourceLoader() {
    }

    public static String loadResource(String resourceName) throws IOException {
        String result;
        if (queries.containsKey(resourceName)) {
            result = queries.get(resourceName);
        } else {
            try (InputStream is = TextResourceLoader.class.getClassLoader().getResourceAsStream(resourceName)) {
                int length = is.available();
                byte[] bytes = new byte[length];
                is.read(bytes);

                result = new String(bytes, "UTF-8");
                queries.put(resourceName, result);
            }
        }

        return result;
    }
}
