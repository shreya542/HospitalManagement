package util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyUtil {
    public static String getPropertyString(String key) {
        Properties properties = new Properties();
        try (InputStream input = new FileInputStream("src/db.properties")) { // Adjust path as necessary
            properties.load(input);
            return properties.getProperty(key);
        } catch (IOException e) {
            e.printStackTrace();
            return null; // or throw a custom exception
        }
    }
}
