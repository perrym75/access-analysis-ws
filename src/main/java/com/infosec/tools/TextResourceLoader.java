package com.infosec.tools;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class TextResourceLoader {

    private final static Map<String, String> queries = new HashMap<>();

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

                result = new String(bytes, 0, is.read(bytes), "UTF-8");
                queries.put(resourceName, result);
            }
        }

        return result;
    }
}
