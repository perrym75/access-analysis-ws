package com.infosec.tools;

import java.io.IOException;
import java.io.InputStream;

public class ResourceReader {

    private ResourceReader() {
    }

    public static String readString(String resourceName, String charset) throws IOException {
          return new String(readBinary(resourceName), charset);
    }

    public static byte[] readBinary(String resourceName) throws IOException {
        try (InputStream is = ResourceReader.class.getClassLoader().getResourceAsStream(resourceName)) {
            int length = is.available();
            byte[] bytes = new byte[length];

            is.read(bytes);
            return bytes;
        }
    }
}
