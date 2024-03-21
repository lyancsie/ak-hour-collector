package org.lyancsie;

import org.lyancsie.exception.PropertyLoadFailureException;
import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class PropertiesLoader {

    private static final String APPLICATION_YML = "application.yml";
    private static Properties INSTANCE;

    private PropertiesLoader() {

    }

    public static Properties getProperties() throws PropertyLoadFailureException {
        if (INSTANCE == null) {
            INSTANCE = load();
        }
        return INSTANCE;
    }

    private static Properties load() throws PropertyLoadFailureException {
        Properties configuration = new Properties();
        try (InputStream inputStream = PropertiesLoader.class
            .getClassLoader()
            .getResourceAsStream(APPLICATION_YML)) {
            configuration.load(inputStream);
        } catch (IOException e) {
            throw new PropertyLoadFailureException(e);
        }
        return configuration;
    }

    public static List<String> getUrlsFromYaml(String filePath) {
        try (FileInputStream fis = new FileInputStream(filePath)) {
            Yaml yaml = new Yaml();
            Map<String, List<String>> yamlData = yaml.load(fis);
            return yamlData.get("urls");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
