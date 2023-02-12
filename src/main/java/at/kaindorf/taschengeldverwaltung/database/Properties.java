package at.kaindorf.taschengeldverwaltung.database;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Properties {

    private static final java.util.Properties PROPS = new java.util.Properties();

    static{
        Path propertyFilePath = Paths.get("src/main/resources/database.properties");
        try {
            FileInputStream is = new FileInputStream(propertyFilePath.toFile());
            PROPS.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getProperty(String key){
        return PROPS.getProperty(key);
    }
}
