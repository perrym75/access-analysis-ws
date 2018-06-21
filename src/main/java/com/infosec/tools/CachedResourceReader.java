package com.infosec.tools;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CachedResourceReader {

    private final static Map<String, String> queries = new HashMap<>();

    private CachedResourceReader() {
    }

    public static String readString(String resourceName) throws IOException {
        String result = queries.get(resourceName);
        if (result == null) {
            result = ResourceReader.readString(resourceName, "UTF-8");
            queries.put(resourceName, result);
        }

        return result;
    }
}
